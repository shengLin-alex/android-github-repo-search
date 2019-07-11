package com.example.mvvmbeginner.utils

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    companion object {
        private const val TAG = "SingleLiveEvent"
    }

    private val _pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (this.hasActiveObservers()) {
            Timber.tag(SingleLiveEvent.TAG).w("Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(owner, Observer<T> {
            if (this._pending.compareAndSet(true, false))
                observer.onChanged(it)
        })
    }

    @MainThread
    override fun setValue(@Nullable value: T?) {
        this._pending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        this.setValue(null)
    }
}