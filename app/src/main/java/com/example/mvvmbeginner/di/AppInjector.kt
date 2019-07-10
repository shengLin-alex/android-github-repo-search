package com.example.mvvmbeginner.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.mvvmbeginner.GithubApp
import dagger.android.HasFragmentInjector
import dagger.android.support.AndroidSupportInjection

class AppInjector private constructor() {

    companion object {
        fun init(githubApp: GithubApp) {
            DaggerAppComponent.builder()
                .application(githubApp)
                .build()
                .inject(githubApp)

            githubApp.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                    AppInjector.handleActivity(activity = p0)
                }

                override fun onActivityStarted(p0: Activity) {

                }

                override fun onActivityResumed(p0: Activity) {

                }

                override fun onActivityPaused(p0: Activity) {

                }

                override fun onActivityStopped(p0: Activity) {

                }

                override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

                }

                override fun onActivityDestroyed(p0: Activity) {

                }
            })
        }

        private fun handleActivity(activity: Activity) {
            if (activity is Injectable || activity is HasFragmentInjector) {
                (activity as FragmentActivity).supportFragmentManager
                    .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
            }
        }
    }
}