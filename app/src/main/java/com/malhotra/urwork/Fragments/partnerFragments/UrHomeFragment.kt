package com.malhotra.urwork.Fragments.partnerFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.malhotra.urwork.Activites.SplashScreen
import com.malhotra.urwork.ViewModel.PartnerViewModel
import com.malhotra.urwork.databinding.FragmentHome2Binding

class UrHomeFragment : Fragment() {

    private var _binding : FragmentHome2Binding? = null
    private val binding get() = _binding!!

    private val partnerViewModel : PartnerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHome2Binding.inflate(inflater, container, false)

        binding.textView3.setOnClickListener {
            partnerViewModel.logout(activity)
            startActivity(Intent(requireContext(), SplashScreen::class.java))
            activity?.finish()
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}