package com.malhotra.urwork.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.Adapters.ServicesAdapter
import com.malhotra.urwork.ModelClass.ServicesData
import com.malhotra.urwork.R
import com.malhotra.urwork.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.recyclerView.adapter = ServicesAdapter(requireContext(), servicesList())

        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.recyclerView2.adapter = ServicesAdapter(requireContext(), homeServicesList())

        binding.profile.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAccountFragment())
        }

        (activity as MainActivity).supportActionBar?.hide()
        return binding.root
    }

    fun servicesList() : ArrayList<ServicesData> {
        val servicesList = arrayListOf<ServicesData>()

        servicesList.add(ServicesData(1, R.drawable.salon, "Hair Salon"))
        servicesList.add(ServicesData(2, R.drawable.spa, "Spa"))
        servicesList.add(ServicesData(3, R.drawable.nail, "Nails"))
        servicesList.add(ServicesData(4, R.drawable.massage, "Massage"))
        servicesList.add(ServicesData(5, R.drawable.makeup_artist, "Make-up Artist"))

        return servicesList
    }

    fun homeServicesList() : ArrayList<ServicesData>{
        val servicesList = arrayListOf<ServicesData>()

        servicesList.add(ServicesData(1, R.drawable.cleaning, "Cleaning"))
        servicesList.add(ServicesData(2, R.drawable.plumbing, "Plumbing"))
        servicesList.add(ServicesData(3, R.drawable.electrical, "Electrical"))
        servicesList.add(ServicesData(4, R.drawable.home_design, "Home Design"))
        servicesList.add(ServicesData(5, R.drawable.yardwork, "Yard Work"))

        return servicesList

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}