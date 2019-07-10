package com.example.mvvmbeginner.data.models

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(indices = [Index("id"), Index("owner_login")],
    primaryKeys = ["name", "owner_login"])
class Repo(val id: Int,
           @SerializedName("name") @field:NonNull val name: String?,
           @SerializedName("full_name") val fullName: String?,
           @SerializedName("description") val description: String?,
           @SerializedName("stargazers_count") val stars: Int?,
           @SerializedName("owner") @Embedded(prefix = "owner_") @field:NonNull val owner: Owner?
)