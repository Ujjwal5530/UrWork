package com.malhotra.urwork.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.Activites.SplashScreen
import com.malhotra.urwork.R
import com.malhotra.urwork.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding : FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.back.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(AccountFragmentDirections.actionAccountFragmentToHomeFragment())
        }

        binding.signOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), SplashScreen::class.java))
            activity?.finish()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}