package com.example.mvvmbeginner.pojos

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName
import java.util.*

class Owner(@SerializedName("login") @field:NonNull val login: String?,
            @SerializedName("avatar_url") val avatarUrl: String?,
            @SerializedName("url") val url: String?) {

    override fun equals(other: Any?): Boolean {
        if (this == other) return true

        if (other == null || this.javaClass != other::javaClass) return false

        val owner =  other as Owner
        return Objects.equals(this.login, owner.login) &&
                Objects.equals(this.avatarUrl, owner.avatarUrl) &&
                Objects.equals(this.url, owner.url)
    }

    override fun hashCode(): Int {
        var result: Int = if (this.login != null) this.login.hashCode() else 0
        result = 31 * result + (if (this.url != null) this.url.hashCode() else 0)

        return result
    }
}