package com.example.mvvmbeginner.models

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GithubViewModelFactory : ViewModelProvider.Factory {

    private val dataModelField = DataModel()

    @Suppress("UNCHECKED_CAST")
    @NonNull
    override fun <T : ViewModel?> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepoViewModel::class.java))
            return RepoViewModel(this.dataModelField) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}