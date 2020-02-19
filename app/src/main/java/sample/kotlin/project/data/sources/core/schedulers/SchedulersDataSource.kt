package sample.kotlin.project.data.sources.core.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import sample.kotlin.project.domain.sources.core.schedulers.SchedulersSource
import javax.inject.Inject

class SchedulersDataSource
@Inject constructor(
) : SchedulersSource {

    override val ioScheduler = Schedulers.io()

    override val uiScheduler: Scheduler = AndroidSchedulers.mainThread()
}
