package com.example.mvvmbeginner.data

import android.os.AsyncTask
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mvvmbeginner.apis.ApiResponse
import com.example.mvvmbeginner.data.models.Resource

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 *     You can read more about it in the <a href="https://developer.android.com/arch">Architecture Guide</a>
 * @param <Res> : Type for the resource data
 * @param <Req> : Type for the API Response
 */
abstract class NetworkBoundResource<Res, Req> @MainThread constructor() {

    private val result: MediatorLiveData<Resource<Res>> = MediatorLiveData()

    init {
        this.result.value = Resource.loading(null)
        val dbSource = this.loadFromDb()

        this.result.addSource(dbSource) { data ->
            this.result.removeSource(dbSource)

            if (this.shouldFetch(data)) {
                this.fetchFromNetwork(dbSource)
            } else {
                this.result.addSource(dbSource) { newData ->
                    this.result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<Res>) {
        val apiResponse = this.createCall()

        this.result.addSource(dbSource) { newData ->
            this.result.value = Resource.loading(newData)
        }

        this.result.addSource(apiResponse) { response ->
            this.result.removeSource(apiResponse)
            this.result.removeSource(dbSource)

            if (response.isSuccessful()) {
                this.saveResultAndReInit(response)
            } else {
                this.onFetchFailed()
                this.result.addSource(dbSource) { newData ->
                    this.result.value = Resource.error(newData, response.errorMessage)
                }
            }
        }
    }

    @MainThread
    private fun saveResultAndReInit(response: ApiResponse<Req>) {
        val task = ResultAndReInitTask(this, response)
        task.execute()
    }

    protected fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<Res>> {
        return this.result
    }

    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): LiveData<Res>

    @MainThread
    protected abstract fun shouldFetch(data: Res?): Boolean

    @NonNull
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<Req>>

    @WorkerThread
    protected abstract fun saveCallResult(item: Req)

    private class ResultAndReInitTask<Res, Req> internal constructor(
        private val resource: NetworkBoundResource<Res, Req>,
        private val response: ApiResponse<Req>) : AsyncTask<Void?, Void?, Void?>() {

        override fun doInBackground(vararg p0: Void?): Void? {
            if (this.response.body != null) this.resource.saveCallResult(this.response.body!!)

            return null
        }

        override fun onPostExecute(p0: Void?) {
            this.resource.result.addSource(this.resource.loadFromDb()) { newData ->
                this.resource.result.value = Resource.success(newData)
            }
        }
    }
}