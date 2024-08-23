package com.malhotra.urwork.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.ViewModel.UserViewModel
import com.malhotra.urwork.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    private val userViewModel : UserViewModel by viewModels()

    @SuppressLint("ServiceCast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            val emailView = binding.email
            imm.hideSoftInputFromWindow(emailView.windowToken,0)

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                binding.loggingIn.visibility = View.VISIBLE
                userViewModel.loginUser(email, password, requireContext(), activity)
            }else Toast.makeText(requireContext(), "All Fields Required!!", Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}