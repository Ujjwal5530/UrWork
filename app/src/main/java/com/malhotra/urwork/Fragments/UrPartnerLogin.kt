package com.malhotra.urwork.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.R
import com.malhotra.urwork.ViewModel.PartnerViewModel
import com.malhotra.urwork.databinding.FragmentUrPartnerLoginBinding


class UrPartnerLogin : Fragment() {

    private var _binding : FragmentUrPartnerLoginBinding? = null
    private val binding get() = _binding!!

    private val partnerViewModel : PartnerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUrPartnerLoginBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val emailView = binding.email
            imm.hideSoftInputFromWindow(emailView.windowToken,0)

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                binding.loggingIn.visibility = View.VISIBLE
                partnerViewModel.loginPartner(email, password, requireContext(), requireActivity())

            }else Toast.makeText(requireContext(), "All Fields Required", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}