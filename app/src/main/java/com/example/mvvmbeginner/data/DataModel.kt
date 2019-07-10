package com.example.mvvmbeginner.data

import androidx.lifecycle.LiveData
import com.example.mvvmbeginner.apis.ApiResponse
import com.example.mvvmbeginner.apis.GithubService
import com.example.mvvmbeginner.data.models.RepoSearchResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataModel @Inject constructor(service: GithubService) {

    private val githubService = service

    fun searchRepo(query: String): LiveData<ApiResponse<RepoSearchResponse>>? {
        return this.githubService.searchRepos(query)
    }
}