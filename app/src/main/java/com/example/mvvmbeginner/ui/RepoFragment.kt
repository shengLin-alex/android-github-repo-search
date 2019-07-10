package com.example.mvvmbeginner.ui

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmbeginner.apis.ApiResponse
import com.example.mvvmbeginner.databinding.RepoFragmentBinding
import com.example.mvvmbeginner.models.GithubViewModelFactory
import com.example.mvvmbeginner.models.RepoViewModel
import com.example.mvvmbeginner.pojos.RepoSearchResponse

class RepoFragment : Fragment() {

    companion object {
        const val TAG = "Repo"

        val newInstance = RepoFragment()
    }

    private var binding: RepoFragmentBinding? = null

    private val factory: GithubViewModelFactory = GithubViewModelFactory()

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
        this.binding?.repoViewModel = this.repoViewModel
        this.repoViewModel?.repos?.observe(this, Observer<ApiResponse<RepoSearchResponse>> {
            this.repoViewModel?.isLoading?.set(false)
            if (it == null) {
                this.repoAdapter.swapItem(null)
                return@Observer
            }

            if (it.isSuccessful()) {
                this.repoAdapter.swapItem(it.body?.items)
            } else {
                Toast.makeText(this.context, "連線發生錯誤", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun doSearch() {
        val query = this.binding?.editorQuery?.text.toString()
        this.repoViewModel?.isLoading?.set(true)
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