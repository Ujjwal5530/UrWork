package com.malhotra.urwork.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        binding.profile.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAccountFragment())
        }
        return binding.root
    }

    fun servicesList() : ArrayList<ServicesData> {
        val servicesList = arrayListOf<ServicesData>()

        servicesList.add(ServicesData(1, R.drawable.ic_baseline_person_24, "Electricity"))
        servicesList.add(ServicesData(2, R.drawable.ic_baseline_person_24, "Electricity 2"))

        return servicesList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}