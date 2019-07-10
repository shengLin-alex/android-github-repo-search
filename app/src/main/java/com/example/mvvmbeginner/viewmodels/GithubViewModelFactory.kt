package com.example.mvvmbeginner.viewmodels

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class GithubViewModelFactory @Inject constructor(
            creatorsMap: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    private val mCreators = creatorsMap

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = this.mCreators[modelClass]

        if (creator == null) {
            for ((key, value) in this.mCreators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}