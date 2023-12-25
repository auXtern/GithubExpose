package com.abdullah.githubexpose.presentation.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah.core.presentation.UserAdapter
import com.abdullah.core.data.Resource
import com.abdullah.core.domain.model.GithubUser
import com.abdullah.githubexpose.R
import com.abdullah.githubexpose.databinding.ActivityMainBinding
import com.abdullah.githubexpose.presentation.detail_user.DetailUserActivity
import com.abdullah.githubexpose.presentation.theme_setting.ThemeSettingActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.hide()

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }

    private fun setupAction() {
        // Muat data sesuai hasil pencarian sebelumnya jika tersedia
        val savedSearchQuery = sharedPreferences.getString(PREF_SEARCH_KEY, DEFAULT_USERNAME)
        binding.searchBar.setText(savedSearchQuery)

        observeGetUsers(savedSearchQuery!!)

        binding.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    sharedPreferences.edit().putString(PREF_SEARCH_KEY, searchView.text.toString())
                        .apply()
                    observeGetUsers(searchView.text.toString())
                    false
                }

            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.itemFavorite -> {
                        showFavoriteUser()
                        true
                    }

                    R.id.itemSetting -> {
                        showThemeSetting()
                        true
                    }

                    else -> true
                }
            }
        }

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun observeGetUsers(searchQuery: String) {
        mainViewModel.getUsers(searchQuery).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(
                            application,
                            "Terjadi kesalahan " + result.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Resource.Loading -> {
                        showLoading(true)
                    }

                    is Resource.Success -> {
                        showLoading(false)
                        setUsersData(result.data)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUsersData(userList: List<GithubUser>?) {
        if (userList.isNullOrEmpty()) {
            binding.llError.visibility = View.VISIBLE
            binding.rvUsers.adapter = null
        } else {
            binding.llError.visibility = View.GONE
            val adapter = UserAdapter()
            adapter.submitList(userList)
            binding.rvUsers.adapter = adapter
            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: GithubUser) {
                    showDetailUser(data.username)
                }
            })
        }
    }

    private fun showDetailUser(username: String) {
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        intent.putExtra(DetailUserActivity.EXTRA_PREV_ACTIVITY, "MainActivity")
        startActivity(intent)
    }

    private fun showFavoriteUser() {
        try {
            installFavoriteModule()
        } catch (e: ClassNotFoundException) {
            Toast.makeText(application, "Modul belum tersedia!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showThemeSetting() {
        val intent = Intent(this@MainActivity, ThemeSettingActivity::class.java)
        startActivity(intent)
    }

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleFavorite = "favorite"
        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            moveToFavoriteActivity()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToFavoriteActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToFavoriteActivity() {
        val intent = Intent(
            this@MainActivity,
            Class.forName("com.abdullah.favorite.presentation.FavoriteUserActivity")
        )
        startActivity(intent)
    }

    companion object {
        private const val PREF_SEARCH_KEY = "search_key"
        private const val DEFAULT_USERNAME = "arif"
    }


}