package com.example.mvvmbeginner.di

import com.example.mvvmbeginner.ui.RepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contriubteRepoFragment(): RepoFragment
}