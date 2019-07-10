package com.example.mvvmbeginner.data.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(GithubTypeConverters::class)
class RepoSearchResult(@NonNull @PrimaryKey val query: String, val repoIds: List<Int>, val totalCount: Int)