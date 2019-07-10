package com.example.mvvmbeginner.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmbeginner.data.models.Repo

@Database(entities = [RepoSearchResult::class, Repo::class], version = 1)
abstract class GithubDb : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}