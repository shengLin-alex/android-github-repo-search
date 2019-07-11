package com.example.mvvmbeginner.apis

import com.example.mvvmbeginner.data.models.Repo
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class RepoSearchResponse(@SerializedName("total_count") val total: Int,
                         @SerializedName("items") val items:  MutableList<Repo>) {

    fun getRepoIds(): MutableList<Int> {
        val repoIds = ArrayList<Int>()

        for (repo in this.items) {
            repoIds.add(repo.id)
        }

        return repoIds
    }
}