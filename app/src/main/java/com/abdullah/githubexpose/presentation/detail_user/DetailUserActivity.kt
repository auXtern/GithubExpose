package com.abdullah.githubexpose.presentation.detail_user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.abdullah.core.data.Resource
import com.abdullah.core.domain.model.GithubDetailUser
import com.abdullah.core.domain.model.GithubUser
import com.abdullah.githubexpose.R
import com.abdullah.githubexpose.adapter.SectionsPagerAdapter
import com.abdullah.githubexpose.databinding.ActivityDetailUserBinding
import com.abdullah.githubexpose.presentation.main.MainActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private var isFavorite: Boolean = false

    private val detailUserViewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView(){
        supportActionBar?.hide()

        binding.apply {
            tvName.text = ""
            tvUsername.text = ""
            fabFavorite.visibility = View.INVISIBLE
        }
    }

    private fun setupAction(){
        val username = intent.getStringExtra(EXTRA_USERNAME)
        if(username != null){
            observeDetailUser(username)
            observeGetFavoriteUser(username)
        }else{
            backToPrevActivity()
        }

        binding.ivBack.setOnClickListener {
            backToPrevActivity()
        }
    }

    private fun observeGetFavoriteUser(username: String){
        detailUserViewModel.getFavoriteUserByUsername(username).observe(this){
            if(it != null){
                changeFavoriteButton(username == it.username)
            }else{
                changeFavoriteButton(false)
            }
        }
    }

    private fun observeDetailUser(username: String){
        detailUserViewModel.getDetailUser(username).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        if(result.data != null){
                            setUserData(result.data!!)
                        }else{
                            backToPrevActivity()
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            application,
                            "Terjadi kesalahan " + result.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun changeFavoriteButton(isFavorite: Boolean){
        this.isFavorite = isFavorite
        if(isFavorite){
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_full)
        }else{
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_empty)
        }
    }

    private fun backToPrevActivity(){
        val prevActivity = intent.getStringExtra(EXTRA_PREV_ACTIVITY)
        if(prevActivity == "FavoriteUserActivity"){
            try{
                val intent = Intent(this@DetailUserActivity, Class.forName("com.abdullah.favorite.presentation.FavoriteUserActivity"))
                startActivity(intent)
            }catch (e: ClassNotFoundException){
                Toast.makeText(application, "Modul belum tersedia!", Toast.LENGTH_SHORT).show()
            }
            finish()
        }else{
            val intent = Intent(this@DetailUserActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUserData(detailUser: GithubDetailUser) {
        binding.apply {
            tvName.text = detailUser.name
            tvUsername.text = detailUser.username
            fabFavorite.visibility = View.VISIBLE
            Glide.with(this@DetailUserActivity)
                .load(detailUser.photo)
                .into(civAvatar)

            val githubUser = GithubUser(
                id = detailUser.id,
                username = detailUser.username,
                photo = detailUser.photo
            )

            fabFavorite.setOnClickListener {
                val state = !isFavorite
                detailUserViewModel.setFavoriteUser(githubUser, state)
                observeGetFavoriteUser(githubUser.username)
            }
        }

        val arrFollow = intArrayOf(detailUser.followers, detailUser.following)
        loadTabLayout(detailUser.username, arrFollow)
    }

    private fun loadTabLayout(username: String, arrFollow: IntArray){
        val viewPager = binding.viewPager
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter
        sectionsPagerAdapter.username = username

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position], arrFollow[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_PREV_ACTIVITY = "extra_prev_activity"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}