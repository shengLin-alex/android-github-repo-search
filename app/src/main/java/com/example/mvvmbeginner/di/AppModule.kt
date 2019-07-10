package com.example.mvvmbeginner.di

import androidx.room.Room
import com.example.mvvmbeginner.GithubApp
import com.example.mvvmbeginner.apis.GithubService
import com.example.mvvmbeginner.data.db.GithubDb
import com.example.mvvmbeginner.data.db.RepoDao
import com.example.mvvmbeginner.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideGithubService(): GithubService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideDb(app: GithubApp): GithubDb {
        return Room.databaseBuilder(app, GithubDb::class.java, "github.db").build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.repoDao()
    }
}