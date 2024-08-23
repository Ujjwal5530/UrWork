package com.malhotra.urwork.Fragments.urPartnerRegister

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
import androidx.navigation.fragment.navArgs
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.ModelClass.PartnerData
import com.malhotra.urwork.R
import com.malhotra.urwork.ViewModel.PartnerViewModel
import com.malhotra.urwork.databinding.FragmentThirdScreenBinding
import com.malhotra.urwork.databinding.FragmentUrPartnerLoginBinding

class ThirdScreen : Fragment() {

    private val args : ThirdScreenArgs by navArgs()

    private var _binding : FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!

    private val partnerViewModel : PartnerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)

        binding.register.setOnClickListener {

            //Hide Keyboard
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val emailView = binding.newPassword
            imm.hideSoftInputFromWindow(emailView.windowToken,0)

            val password = binding.newPassword.text.toString()
            val verifyPassword = binding.verifyPassword.text.toString()

            if (password.isNotEmpty() && verifyPassword.isNotEmpty()){

                if (password == verifyPassword){

                    binding.loggingIn.visibility = View.VISIBLE
                    partnerViewModel.registerPartner(args.partnerData.email.toString(), password, args.partnerData, requireContext(),
                        requireActivity()
                    )

                }else Toast.makeText(requireContext(), "Password do not match!!", Toast.LENGTH_SHORT).show()

            }else Toast.makeText(requireContext(), "All Fields Required!!", Toast.LENGTH_SHORT).show()


        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}