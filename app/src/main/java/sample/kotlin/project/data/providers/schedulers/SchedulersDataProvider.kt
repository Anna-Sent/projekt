package sample.kotlin.project.data.providers.schedulers

import android.os.NetworkOnMainThreadException
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import org.slf4j.LoggerFactory
import sample.kotlin.project.domain.providers.connectivity.ConnectivityProvider
import sample.kotlin.project.domain.providers.connectivity.NoConnectionException
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.providers.schedulers.TryCountExceededException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SchedulersDataProvider
@Inject constructor(
    private val connectivityProvider: ConnectivityProvider
) : SchedulersProvider {

    companion object {
        private const val MAX_TRY_COUNT = 5
        private const val CONSTANT_DELAY_SECONDS = 5
    }

    private val logger = LoggerFactory.getLogger(toString())

    override val ioScheduler = Schedulers.io()

    override val uiScheduler: Scheduler = AndroidSchedulers.mainThread()

    override fun <U> applySchedulersObservable() =
        ObservableTransformer { upstream: Observable<U> ->
            upstream
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
        }

    override fun <U> applySchedulersSingle() =
        SingleTransformer { upstream: Single<U> ->
            upstream
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
        }

    override fun <U> applySchedulersMaybe() =
        MaybeTransformer { upstream: Maybe<U> ->
            upstream
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
        }

    override fun applySchedulersCompletable() =
        CompletableTransformer { upstream ->
            upstream
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
        }

    override fun <U> applySamplingObservable(milliseconds: Long) =
        ObservableTransformer { upstream: Observable<U> ->
            upstream.sample(milliseconds, TimeUnit.MILLISECONDS, ioScheduler)
        }

    override fun <U> retryLinearDelayLimitedObservable() =
        ObservableTransformer { upstream: Observable<U> ->
            wrapWithRetryLinearDelayLimited(upstream)
        }

    override fun <U> retryLinearDelayLimitedSingle() =
        SingleTransformer { upstream: Single<U> ->
            wrapWithRetryLinearDelayLimited(upstream.toObservable()).firstOrError()
        }

    override fun <U> retryConstantDelayInfiniteObservable() =
        ObservableTransformer { upstream: Observable<U> ->
            wrapWithRetryConstantDelayInfinite(upstream)
        }

    override fun <U> retryConstantDelayInfiniteSingle() =
        SingleTransformer { upstream: Single<U> ->
            wrapWithRetryConstantDelayInfinite(upstream.toObservable()).firstOrError()
        }

    private fun <U> wrapWithRetryLinearDelayLimited(observable: Observable<U>) =
        observable.retryWhen { timer(it, linearDelayLimited()) }

    private fun <U> wrapWithRetryConstantDelayInfinite(observable: Observable<U>) =
        observable.retryWhen { timer(it, constantDelayInfinite()) }

    private fun timer(
        errors: Observable<Throwable>,
        transformer: ObservableTransformer<Throwable, Int>
    ) =
        errors
            .doOnNext { logger.debug("try failed {}", it.javaClass.simpleName) }
            .compose(transformer)
            .doOnNext { logger.debug("next try will start in {} seconds", it) }
            .flatMap(::startTimer)

    private fun linearDelayLimited() =
        ObservableTransformer<Throwable, Int> { upstream ->
            upstream.zipWith(Observable.range(1, MAX_TRY_COUNT))
            { throwable, i ->
                if (
                    throwable is NoConnectionException
                    || throwable is NetworkOnMainThreadException
                ) throw throwable
                else if (i == MAX_TRY_COUNT) throw TryCountExceededException(throwable)
                else i
            }
                .doOnNext { logger.debug("try {} failed", it) }
                .map { (it % 5 + 1) * 3 / 2 }
        }

    private fun constantDelayInfinite() =
        ObservableTransformer<Throwable, Int> { upstream ->
            upstream.map { CONSTANT_DELAY_SECONDS }
        }

    private fun startTimer(seconds: Int) =
        Observable.timer(seconds.toLong(), TimeUnit.SECONDS, ioScheduler)
            .mergeWith(connectivityProvider.isNetworkConnectedSkipInitial()
                .filter { it }
                .map { 0L })
            .firstOrError()
            .toObservable()
}
