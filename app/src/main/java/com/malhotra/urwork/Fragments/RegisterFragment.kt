package com.malhotra.urwork.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
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
import com.malhotra.urwork.ModelClass.UserData
import com.malhotra.urwork.R
import com.malhotra.urwork.ViewModel.UserViewModel
import com.malhotra.urwork.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    private val userViewModel : UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.register.setOnClickListener {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val emailView = binding.email
            imm.hideSoftInputFromWindow(emailView.windowToken,0)

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            val name = binding.name.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()){
                binding.loggingIn.visibility = View.VISIBLE
                userViewModel.registerUser(email, password, UserData("", name, email, null), requireContext(), activity)
            } else {
                Toast.makeText(requireContext(), "All Fields Required!!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}