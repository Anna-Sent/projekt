package sample.kotlin.project.domain.providers.schedulers

import io.reactivex.CompletableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer

interface SchedulersProvider {

    val ioScheduler: Scheduler

    val uiScheduler: Scheduler

    fun <U> applySchedulersObservable(): ObservableTransformer<U, U>

    fun <U> applySchedulersSingle(): SingleTransformer<U, U>

    fun <U> applySchedulersMaybe(): MaybeTransformer<U, U>

    fun applySchedulersCompletable(): CompletableTransformer

    fun <U> applySamplingObservable(milliseconds: Long): ObservableTransformer<U, U>

    fun <U> retryLinearDelayLimitedObservable(): ObservableTransformer<U, U>

    fun <U> retryLinearDelayLimitedSingle(): SingleTransformer<U, U>

    fun <U> retryConstantDelayInfiniteObservable(): ObservableTransformer<U, U>

    fun <U> retryConstantDelayInfiniteSingle(): SingleTransformer<U, U>
}
