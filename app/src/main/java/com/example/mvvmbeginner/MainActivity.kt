package com.example.mvvmbeginner

import android.os.Bundle
import com.example.mvvmbeginner.ui.RepoFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.main_activity)
        val tag = RepoFragment.TAG

        if (this.supportFragmentManager.findFragmentByTag(tag) == null) {
            val fragment = RepoFragment.newInstance
            this.supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment, tag)
                .commit()
        }
    }
}
