package com.example.mvvmbeginner.data

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mvvmbeginner.apis.ApiResponse
import com.example.mvvmbeginner.apis.GithubService
import com.example.mvvmbeginner.data.db.RepoDao
import com.example.mvvmbeginner.data.models.Repo
import com.example.mvvmbeginner.apis.RepoSearchResponse
import com.example.mvvmbeginner.data.db.RepoSearchResult
import com.example.mvvmbeginner.data.models.Resource
import com.example.mvvmbeginner.utils.AbsentLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(private val repoDao: RepoDao, private val githubService: GithubService) {

    fun search(query: String): LiveData<Resource<MutableList<Repo>>> {

        val resource = object: NetworkBoundResource<MutableList<Repo>, RepoSearchResponse>() {

            override fun loadFromDb(): LiveData<MutableList<Repo>> {
                return Transformations.switchMap(this@RepoRepository.repoDao.search(query), Function {
                    if (it == null) {
                        return@Function AbsentLiveData.create<MutableList<Repo>>()
                    } else {
                        return@Function this@RepoRepository.repoDao.loadOrdered(it.repoIds)
                    }
                })
            }

            override fun shouldFetch(data: MutableList<Repo>?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<RepoSearchResponse>> {
                return this@RepoRepository.githubService.searchRepos(query)
            }

            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.getRepoIds()
                val repoSearchResult = RepoSearchResult(query, repoIds, item.total)

                this@RepoRepository.repoDao.insertRepos(item.items)
                this@RepoRepository.repoDao.insert(repoSearchResult)
            }
        }

        return resource.asLiveData()
    }
}