package com.example.mvvmbeginner.viewmodels

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvvmbeginner.data.RepoRepository
import com.example.mvvmbeginner.data.models.Repo
import com.example.mvvmbeginner.data.models.Resource
import com.example.mvvmbeginner.utils.AbsentLiveData
import javax.inject.Inject

class RepoViewModel @Inject constructor(private val repoRepository: RepoRepository) : ViewModel() {

    private val query: MutableLiveData<String> = MutableLiveData()

    var repos: LiveData<Resource<MutableList<Repo>>>? = null

    init {
        this.repos = Transformations.switchMap(this.query) {
            if (TextUtils.isEmpty(it)) {
                AbsentLiveData.create()
            } else {
                this@RepoViewModel.repoRepository.search(it)
            }
        }
    }

    fun searchRepo(query: String) {
        this.query.value = query
    }
}