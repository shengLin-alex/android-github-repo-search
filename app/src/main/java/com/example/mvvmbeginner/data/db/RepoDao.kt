package com.example.mvvmbeginner.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmbeginner.data.models.Repo

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepo(repositories: List<Repo>)

    @Query("SELECT * FROM Repo WHERE id IN (:repoIds)")
    abstract fun loadById(repoIds: List<Int>): List<Repo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: RepoSearchResult)

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun search(query: String): RepoSearchResult
}