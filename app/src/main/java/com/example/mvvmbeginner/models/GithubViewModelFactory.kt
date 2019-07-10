package com.example.mvvmbeginner.models

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

class GithubViewModelFactory @Inject constructor(model: DataModel) : ViewModelProvider.Factory {

    private val dataModel = model

    @Suppress("UNCHECKED_CAST")
    @NonNull
    override fun <T : ViewModel?> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepoViewModel::class.java))
            return RepoViewModel(this.dataModel) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}