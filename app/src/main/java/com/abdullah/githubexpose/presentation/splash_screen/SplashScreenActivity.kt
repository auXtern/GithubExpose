package com.abdullah.githubexpose.presentation.splash_screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.abdullah.githubexpose.databinding.ActivitySplashScreenBinding
import com.abdullah.githubexpose.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAnimation()
    }

    private fun setupAnimation() {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1500

        binding.ivLogo.startAnimation(fadeIn)

        lifecycleScope.launch(Dispatchers.Default) {
            delay(SPLASH_DISPLAY_LENGTH)

            launch(Dispatchers.Main) {
                startActivity(nextScreen(this@SplashScreenActivity))
                finish()
            }
        }
    }

    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 2000L
        fun nextScreen(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}