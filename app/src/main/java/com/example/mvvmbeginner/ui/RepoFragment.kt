package com.example.mvvmbeginner.ui

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmbeginner.data.models.Repo
import com.example.mvvmbeginner.data.models.Resource
import com.example.mvvmbeginner.databinding.RepoFragmentBinding
import com.example.mvvmbeginner.di.Injectable
import com.example.mvvmbeginner.viewmodels.GithubViewModelFactory
import com.example.mvvmbeginner.viewmodels.RepoViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RepoFragment : DaggerFragment(), Injectable {

    companion object {
        const val TAG = "Repo"

        val newInstance = RepoFragment()
    }

    private var binding: RepoFragmentBinding? = null

    @Inject
    lateinit var factory: GithubViewModelFactory

    private var repoViewModel: RepoViewModel? = null

    private val repoAdapter: RepoAdapter = RepoAdapter(ArrayList())

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = RepoFragmentBinding.inflate(inflater, container, false)

        this.binding?.editorQuery?.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                this.doSearch()
                return@setOnKeyListener true
            }

            return@setOnKeyListener false
        }

        this.binding?.buttonSearch?.setOnClickListener {
            this.doSearch()
        }

        this.binding?.recyclerView?.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        this.binding?.recyclerView?.adapter = this.repoAdapter

        return this.binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.repoViewModel = ViewModelProviders.of(this, this.factory).get(RepoViewModel::class.java)
        this.repoViewModel?.repos?.observe(this, Observer<Resource<MutableList<Repo>>> {
            if (it != null) {
                this.binding?.resource = it
                this.binding?.executePendingBindings()

                if (it.data != null) {
                    this.repoAdapter.swapItem(it.data)
                }
            }
        })
    }

    private fun doSearch() {
        val query = this.binding?.editorQuery?.text.toString()

        this.repoViewModel?.searchRepo(query)
        this.dismissKeyboard()
    }

    private fun dismissKeyboard() {
        val view = this.activity?.currentFocus
        if (view != null) {
            val imm = this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}