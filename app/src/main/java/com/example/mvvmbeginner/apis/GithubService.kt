package com.example.mvvmbeginner.apis

import androidx.lifecycle.LiveData
import com.example.mvvmbeginner.data.models.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Rest api access points
 */
interface GithubService {
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): LiveData<ApiResponse<RepoSearchResponse>>
}