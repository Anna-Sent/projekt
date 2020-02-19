package sample.kotlin.project.data.providers.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import sample.kotlin.project.domain.sources.core.schedulers.SchedulersProvider
import javax.inject.Inject

class SchedulersDataProvider
@Inject constructor(
) : SchedulersProvider {

    override val ioScheduler = Schedulers.io()

    override val uiScheduler: Scheduler = AndroidSchedulers.mainThread()
}
