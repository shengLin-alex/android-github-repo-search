package com.example.mvvmbeginner.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    companion object {
        private const val TAG = "SingleLiveEvent"
    }

    private val _pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (this.hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
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