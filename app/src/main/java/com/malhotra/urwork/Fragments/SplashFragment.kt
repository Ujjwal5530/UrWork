package com.malhotra.urwork.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import com.malhotra.urwork.R
import com.malhotra.urwork.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentSplashBinding.inflate(inflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.linearLayout.apply {
                visibility = View.VISIBLE
                startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in))
            }
            binding.signup.apply {
                visibility = View.VISIBLE
                startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in))
            }
            binding.view.apply {
                visibility = View.VISIBLE
                startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in))
            }
        }, 800)

        binding.login.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }

        binding.register.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}