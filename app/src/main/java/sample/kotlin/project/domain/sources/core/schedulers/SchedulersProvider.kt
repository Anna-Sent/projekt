package sample.kotlin.project.domain.sources.core.schedulers

import io.reactivex.Scheduler

interface SchedulersProvider {

    val ioScheduler: Scheduler

    val uiScheduler: Scheduler
}
