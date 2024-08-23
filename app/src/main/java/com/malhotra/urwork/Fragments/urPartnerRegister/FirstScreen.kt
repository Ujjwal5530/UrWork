package com.malhotra.urwork.Fragments.urPartnerRegister

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.malhotra.urwork.R
import com.malhotra.urwork.ViewModel.PartnerViewModel
import com.malhotra.urwork.databinding.FragmentFirstScreenBinding
import com.malhotra.urwork.databinding.FragmentUrPartnerLoginBinding

class FirstScreen : Fragment() {
    private var _binding : FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    private val partnerViewModel : PartnerViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)

        binding.next.setOnClickListener {

            //Hide Keyboard
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val emailView = binding.email
            imm.hideSoftInputFromWindow(emailView.windowToken,0)

            val email = binding.email.text.toString()
            val name = binding.name.text.toString()
            val phone = binding.phone.text.toString()

            if (email.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()){
                var result = false

                partnerViewModel.emailQuery(email)
                partnerViewModel.result.observe(viewLifecycleOwner){
                    result = it
                }

                if (result){
                    binding.loading.visibility = View.VISIBLE
                    Navigation.findNavController(it).navigate(FirstScreenDirections.actionFirstScreenToSecondScreen(name, email, phone))
                } else Toast.makeText(requireContext() , "Email is already Used, Please log in", Toast.LENGTH_SHORT).show()

            }else Toast.makeText(requireContext() , "All Fields Required", Toast.LENGTH_SHORT).show()
        }

        binding.alreadyLogin.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            Navigation.findNavController(it).navigate(FirstScreenDirections.actionFirstScreenToUrPartnerLogin())
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}