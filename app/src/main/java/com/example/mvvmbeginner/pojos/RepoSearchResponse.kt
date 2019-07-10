package com.example.mvvmbeginner.pojos

import com.google.gson.annotations.SerializedName

class RepoSearchResponse(@SerializedName("total_count") val total: Int?,
                         @SerializedName("items") val items:  MutableList<Repo>?)