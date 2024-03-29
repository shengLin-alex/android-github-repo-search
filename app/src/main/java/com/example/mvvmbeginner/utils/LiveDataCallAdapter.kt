package com.example.mvvmbeginner.utils

import androidx.lifecycle.LiveData
import com.example.mvvmbeginner.apis.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType(): Type {
        return this.responseType
    }

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object: LiveData<ApiResponse<R>>() {
            val started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()

                if (this.started.compareAndSet(false, true)) {
                    call.enqueue(object: Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(ApiResponse(response))
                        }

                        override fun onFailure(call: Call<R>, t: Throwable) {
                            postValue(ApiResponse(t))
                        }
                    })
                }
            }
        }
    }
}