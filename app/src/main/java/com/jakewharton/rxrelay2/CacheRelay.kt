package com.jakewharton.rxrelay2

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.EmptyDisposable
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class CacheRelay<T> private constructor() : Relay<T>() {

    private val subscriber = AtomicReference<CacheDisposable<T>>()
    private val queue = ConcurrentLinkedQueue<T>()

    override fun subscribeActual(observer: Observer<in T>) {
        val s = subscriber.get()
        if (s != null) {
            EmptyDisposable.error(
                IllegalStateException("Only a single observer at a time allowed"),
                observer
            )
            return
        }
        val disposable = CacheDisposable(observer, this)
        observer.onSubscribe(disposable)
        add(disposable)
        if (disposable.isDisposed) {
            remove(disposable)
        } else {
            var element: T? = queue.peek()
            while (element != null && disposable.onNext(element)) {
                queue.remove(element)
                element = queue.peek()
            }
        }
    }

    private fun add(disposable: CacheDisposable<T>) {
        while (!subscriber.compareAndSet(null, disposable)) {
        }
    }

    internal fun remove(disposable: CacheDisposable<T>) {
        while (!subscriber.compareAndSet(disposable, null)) {
        }
    }

    override fun accept(value: T) {
        val s = subscriber.get()
        if (s == null || !s.onNext(value)) {
            queue.add(value)
        }
    }

    override fun hasObservers() = subscriber.get() != null

    internal class CacheDisposable<T>(
        private val downstream: Observer<in T>,
        private val parent: CacheRelay<T>
    ) : AtomicBoolean(), Disposable {

        fun onNext(value: T) =
            if (get()) {
                false
            } else {
                downstream.onNext(value)
                true
            }

        override fun dispose() {
            if (compareAndSet(false, true)) {
                parent.remove(this)
            }
        }

        override fun isDisposed() = get()
    }

    companion object {
        fun <T> create(): CacheRelay<T> {
            return CacheRelay()
        }
    }
}
