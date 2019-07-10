package com.example.mvvmbeginner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmbeginner.ui.RepoFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
