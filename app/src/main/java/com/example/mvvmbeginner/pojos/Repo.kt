package com.example.mvvmbeginner.pojos

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

class Repo(val id: Int,
           @SerializedName("name") @field:NonNull val name: String?,
           @SerializedName("full_name") val fullName: String?,
           @SerializedName("description") val description: String?,
           @SerializedName("stargazers_count") val stars: Int?,
           @SerializedName("owner") @field:NonNull val owner: Owner?
)