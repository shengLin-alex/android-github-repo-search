package com.example.mvvmbeginner.utils

import androidx.lifecycle.LiveData

class AbsentLiveData<T> private constructor() : LiveData<T>() {

    init {
        this.postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}