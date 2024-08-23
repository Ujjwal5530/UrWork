package com.malhotra.urwork.Activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.malhotra.urwork.R
import com.malhotra.urwork.databinding.ActivityPartnerBinding

class PartnerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPartnerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityPartnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}