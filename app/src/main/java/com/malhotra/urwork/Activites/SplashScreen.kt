package com.malhotra.urwork.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.malhotra.urwork.R
import com.malhotra.urwork.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView)

        setupActionBarWithNavController(navController)

        supportActionBar?.hide()

        binding.mop
            .startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.slide_right))

        binding.paint
            .startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.slide_right))

        binding.carpenter
            .startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.slide_down))

        binding.wrench
            .startAnimation(
                AnimationUtils
                    .loadAnimation(applicationContext, R.anim.slide_left))

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
    }
}