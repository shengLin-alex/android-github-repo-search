package com.example.mvvmbeginner.viewmodels

import android.text.TextUtils
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvvmbeginner.apis.ApiResponse
import com.example.mvvmbeginner.data.DataModel
import com.example.mvvmbeginner.utils.AbsentLiveData
import com.example.mvvmbeginner.data.models.RepoSearchResponse
import javax.inject.Inject

class RepoViewModel @Inject constructor(model: DataModel) : ViewModel() {

    private val query: MutableLiveData<String> = MutableLiveData()

    private val dataModel = model

    var repos: LiveData<ApiResponse<RepoSearchResponse>>? = null

    val isLoading: ObservableBoolean = ObservableBoolean()

    init {
        this.repos = Transformations.switchMap(this.query) {
            if (TextUtils.isEmpty(it)) {
                AbsentLiveData.create()
            } else {
                this.dataModel.searchRepo(it)
            }
        }
    }

    fun searchRepo(query: String) {
        this.query.value = query
    }
}