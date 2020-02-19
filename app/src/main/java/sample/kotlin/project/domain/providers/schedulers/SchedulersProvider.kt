package sample.kotlin.project.domain.providers.schedulers

import io.reactivex.Scheduler

interface SchedulersProvider {

    val ioScheduler: Scheduler

    val uiScheduler: Scheduler
}
