package com.example.mvvmbeginner.apis

import com.example.mvvmbeginner.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Rest api access root
 */
class RetrofitManager private constructor() {

    companion object {
        private val INSTANCE = RetrofitManager()

        fun getAPI(): GithubService? {
            return this.INSTANCE.githubService
        }
    }

    private var githubService: GithubService? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()

        this.githubService = retrofit.create(GithubService::class.java)
    }
}