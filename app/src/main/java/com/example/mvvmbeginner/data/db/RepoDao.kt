package com.example.mvvmbeginner.data.db

import android.util.SparseIntArray
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmbeginner.data.models.Repo
import java.util.*
import kotlin.Comparator

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: RepoSearchResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repo: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: MutableList<Repo>)

    @Query("SELECT * FROM Repo WHERE owner_login = :login AND name = :name")
    abstract fun load(login: String, name: String): LiveData<Repo>

    @Query("SELECT * FROM Repo WHERE owner_login = :owner ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): LiveData<MutableList<Repo>>

    @Query("SELECT * FROM Repo WHERE id IN (:repoIds)")
    abstract fun loadById(repoIds: MutableList<Int>): LiveData<MutableList<Repo>>

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<RepoSearchResult>

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun findSearchResult(query: String): RepoSearchResult

    fun loadOrdered(repoIds: MutableList<Int>): LiveData<MutableList<Repo>> {
        val order = SparseIntArray()
        for ((idx, repoId) in repoIds.withIndex()) {
            order.put(repoId, idx)
        }

        return Transformations.map(this.loadById(repoIds)) {
            it.sortWith(Comparator { p0, p1 ->
                val pos1 = order[p0!!.id]
                val pos2 = order[p1!!.id]

                pos1 - pos2
            })

            it
        }
    }
}