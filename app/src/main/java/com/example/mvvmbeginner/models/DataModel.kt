package com.example.mvvmbeginner.models

import androidx.lifecycle.LiveData
import com.example.mvvmbeginner.apis.ApiResponse
import com.example.mvvmbeginner.apis.RetrofitManager
import com.example.mvvmbeginner.pojos.RepoSearchResponse

class DataModel {

    private val githubService = RetrofitManager.getAPI()

    fun searchRepo(query: String): LiveData<ApiResponse<RepoSearchResponse>>? {
        return this.githubService?.searchRepos(query)
    }
}