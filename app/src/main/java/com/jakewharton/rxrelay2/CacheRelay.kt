package com.jakewharton.rxrelay2

import io.reactivex.Observer
import io.reactivex.internal.disposables.EmptyDisposable
import java.util.concurrent.ConcurrentLinkedQueue

class CacheRelay<T> private constructor() : Relay<T>() {

    private val queue = ConcurrentLinkedQueue<T>()
    private val relay = PublishRelay.create<T>()

    override fun accept(value: T) {
        if (relay.hasObservers()) {
            relay.accept(value)
        } else {
            queue.add(value)
        }
    }

    override fun hasObservers(): Boolean {
        return relay.hasObservers()
    }

    override fun subscribeActual(observer: Observer<in T>) {
        if (hasObservers()) {
            EmptyDisposable.error(
                IllegalStateException("Only a single observer at a time allowed"),
                observer
            )
        } else {
            var element: T? = queue.poll()
            while (element != null) {
                observer.onNext(element)
                element = queue.poll()
            }
            relay.subscribeActual(observer)
        }
    }

    companion object {
        fun <T> create(): CacheRelay<T> {
            return CacheRelay()
        }
    }
}
