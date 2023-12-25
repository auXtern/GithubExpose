package com.abdullah.githubexpose.presentation.theme_setting

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.abdullah.githubexpose.databinding.ActivityThemeSettingBinding
import com.abdullah.githubexpose.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThemeSettingActivity : AppCompatActivity() {

    private val themeSettingViewModel: ThemeSettingViewModel by viewModels()
    private lateinit var binding: ActivityThemeSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            themeSettingViewModel.getThemeSettings()
                .observe(this@ThemeSettingActivity) { isDarkModeActive: Boolean ->
                    changeTheme(isDarkModeActive)
                }

            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                themeSettingViewModel.saveThemeSetting(isChecked).observe(this@ThemeSettingActivity) {  isDarkModeActive: Boolean ->
                    changeTheme(isDarkModeActive)
                }
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            backToHome()
        }
    }

    private fun backToHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun changeTheme(isDarkModeActive: Boolean){
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.switchTheme.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchTheme.isChecked = false
        }
    }
}