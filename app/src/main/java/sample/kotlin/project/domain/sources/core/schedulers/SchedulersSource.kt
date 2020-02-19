package sample.kotlin.project.domain.sources.core.schedulers

import io.reactivex.Scheduler

interface SchedulersSource {

    val ioScheduler: Scheduler

    val uiScheduler: Scheduler
}
