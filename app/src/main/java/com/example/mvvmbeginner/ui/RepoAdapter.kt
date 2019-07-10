package com.example.mvvmbeginner.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmbeginner.databinding.RepoItemBinding
import com.example.mvvmbeginner.pojos.Repo
import java.util.*

class RepoAdapter(private val items: MutableList<Repo>) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    class RepoViewHolder(private val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            this.binding.repo = repo
            this.binding.executePendingBindings()
        }
    }

    private class RepoDiffCallBack(private val oldList: MutableList<Repo>?,
                                   private val newList: MutableList<Repo>?) : DiffUtil.Callback() {

        override fun getNewListSize(): Int {
            return if (this.newList != null) this.newList.size else 0
        }

        override fun getOldListSize(): Int {
            return if (this.oldList != null) this.oldList.size else 0
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (this.oldList != null && this.newList != null) {
                val oldId = this.oldList[oldItemPosition].id
                val newId = this.newList[newItemPosition].id

                return Objects.equals(oldId, newId)
            }

            return false
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (this.oldList != null && this.newList != null) {
                val oldId = this.oldList[oldItemPosition]
                val newId = this.newList[newItemPosition]

                return Objects.equals(oldId, newId)
            }

            return false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RepoItemBinding.inflate(layoutInflater, parent, false)

        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo: Repo = this.items[position]
        holder.bind(repo)
    }

    fun swapItem(newItems: MutableList<Repo>?) {
        if (newItems != null) {
            val result = DiffUtil.calculateDiff(RepoDiffCallBack(this.items, newItems))
            this.items.clear()
            this.items.addAll(newItems)
            result.dispatchUpdatesTo(this)
        } else {
            val oldSize = this.items.size
            this.items.clear()
            this.notifyItemChanged(0, oldSize)
        }
    }
}