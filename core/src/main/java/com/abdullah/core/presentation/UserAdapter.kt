package com.abdullah.core.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.core.databinding.ItemRowUserBinding
import com.abdullah.core.domain.model.GithubUser
import com.bumptech.glide.Glide

class UserAdapter : ListAdapter<GithubUser, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userGithub = getItem(position)
        holder.bind(userGithub)

        // Menambahkan onClickListener
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(userGithub)
        }
    }

    class MyViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(githubUser: GithubUser){
            binding.apply {
                Glide.with(root.context)
                    .load(githubUser.photo)
                    .into(civAvatar)

                tvName.text = githubUser.username
                tvUserId.text = "#${githubUser.id}"
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}