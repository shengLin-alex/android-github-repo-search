package com.example.mvvmbeginner.data.models

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import java.util.*

class Resource<T>(@NonNull val status: Status, @Nullable val data: T?, @Nullable val message: String?) {

    companion object {

        fun <T> success(@Nullable data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(@Nullable data: T?, message: String?): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(@Nullable data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun equals(other: Any?): Boolean {
        if (this == other) return true

        if (other == null || this.javaClass != other.javaClass) return false

        val resource = other as Resource<Any>
        return Objects.equals(this.status, resource.status) &&
                Objects.equals(this.message, resource.message) &&
                Objects.equals(this.data, resource.data)
    }

    override fun hashCode(): Int {
        var result = this.status.hashCode()
        result = 31 * result + if (this.message != null) this.message.hashCode() else 0
        result = 31 * result + if (this.data != null) this.data.hashCode() else 0

        return result
    }

    override fun toString(): String {
        return "Resource{status=${this.status}, message=${this.message}, data=${this.data}}"
    }
}