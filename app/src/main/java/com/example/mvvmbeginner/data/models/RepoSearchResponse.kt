package com.example.mvvmbeginner.data.models

import com.google.gson.annotations.SerializedName

class RepoSearchResponse(@SerializedName("total_count") val total: Int?,
                         @SerializedName("items") val items:  MutableList<Repo>?)