package com.abdullah.favorite.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah.core.domain.model.GithubUser
import com.abdullah.core.presentation.UserAdapter
import com.abdullah.favorite.databinding.ActivityFavoriteUserBinding
import com.abdullah.favorite.di.ViewModelFactory
import com.abdullah.githubexpose.presentation.detail_user.DetailUserActivity
import com.abdullah.githubexpose.presentation.main.MainActivity

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding

    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView(){
        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavoriteUsers.addItemDecoration(itemDecoration)
    }

    private fun setupAction(){
        favoriteUserViewModel.favoriteUsers.observe(this){
            showLoading(true)
            if(it != null){
                setUsersData(it)
            }
            showLoading(false)
        }

        onBackPressedDispatcher.addCallback() {
            backToHome()
        }

        binding.topAppBar.setNavigationOnClickListener {
           backToHome()
        }
    }

    private fun backToHome(){
        val intent = Intent(this@FavoriteUserActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUsersData(githubUserList: List<GithubUser>?) {
        if(githubUserList.isNullOrEmpty()){
            binding.llError.visibility = View.VISIBLE
            binding.rvFavoriteUsers.adapter = null
        }else{
            binding.llError.visibility = View.GONE
            val adapter = UserAdapter()
            adapter.submitList(githubUserList)
            binding.rvFavoriteUsers.adapter = adapter
            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: GithubUser) {
                    showDetailUser(data.username)
                }
            })
        }
    }

    private fun showDetailUser(username: String) {
        val intent = Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        intent.putExtra(DetailUserActivity.EXTRA_PREV_ACTIVITY, "FavoriteUserActivity")
        startActivity(intent)
    }
}