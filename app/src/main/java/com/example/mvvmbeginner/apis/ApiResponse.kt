package com.example.mvvmbeginner.apis

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class ApiResponse<T> {

    var code: Int = 500

    var body: T? = null

    var errorMessage: String? = null

    constructor(error: Throwable) {
        this.errorMessage = error.message
    }

    constructor(response: Response<T>) {
        this.code = response.code()
        if (response.isSuccessful) {
            this.body = response.body()
            this.errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Timber.e(ignored, "error while parsing response")
                }
            }

            if (message == null || message.trim().isEmpty()) {
                message = response.message()
            }

            this.errorMessage = message
            this.body = null
        }
    }

    fun isSuccessful(): Boolean {
        return this.code in 200..299
    }
}