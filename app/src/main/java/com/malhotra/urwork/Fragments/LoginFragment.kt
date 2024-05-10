package com.malhotra.urwork.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.R
import com.malhotra.urwork.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        binding.loggingIn.visibility = View.VISIBLE
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            activity?.finish()
                        },500)
                    }else Toast.makeText(requireContext(), it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }else Toast.makeText(requireContext(), "All Fields Required!!", Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}