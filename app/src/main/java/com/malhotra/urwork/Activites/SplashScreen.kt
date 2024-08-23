package com.malhotra.urwork.Activites

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.malhotra.urwork.R
import com.malhotra.urwork.ViewModel.PartnerViewModel
import com.malhotra.urwork.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var navController: NavController
    private lateinit var firebaseAuth: FirebaseAuth

    private val partnerViewModel: PartnerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userLogin = getSharedPreferences("userLogin", Context.MODE_PRIVATE).getBoolean("isLoggedIn", false)

        val partnerLogin = getSharedPreferences("partnerLogin", Context.MODE_PRIVATE).getBoolean("isLoggedIn", false)

        if (userLogin){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else if (partnerLogin){
            startActivity(Intent(this, PartnerActivity::class.java))
            finish()
        }

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