package com.example.mvvmbeginner.di

import com.example.mvvmbeginner.GithubApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuildersModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: GithubApp): Builder
        fun build(): AppComponent
    }

    fun inject(app: GithubApp)
}