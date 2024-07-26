package com.malhotra.urwork.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.Adapters.ServicesAdapter
import com.malhotra.urwork.ModelClass.ServicesData
import com.malhotra.urwork.R
import com.malhotra.urwork.ViewModel.ServicesViewModel
import com.malhotra.urwork.ViewModel.UserViewModel
import com.malhotra.urwork.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage : StorageReference = FirebaseStorage.getInstance().getReference("Users")

    private val servicesViewModel : ServicesViewModel by viewModels()
    private val userViewModel : UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //set name in home fragment
        val id = auth.currentUser?.uid.toString()

        storage.child(id).downloadUrl.addOnSuccessListener { uri ->
            Glide.with(requireContext()).load(uri).into(binding.profile)
        }
        userViewModel.getUserData(id)
        userViewModel.user.observe(viewLifecycleOwner){
            binding.accountName.text = it.name
        }

        //get Personal Services List

        servicesViewModel.getPersonalServices()
        servicesViewModel.personalList.observe(viewLifecycleOwner){
            if (it != null) {
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                binding.recyclerView.adapter = ServicesAdapter(requireContext(), it)
            }
        }

        //get Home Services List

        servicesViewModel.getHomeServices()
        servicesViewModel.homeList.observe(viewLifecycleOwner){
            if (it != null){
                binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                binding.recyclerView2.adapter = ServicesAdapter(requireContext(), it)
            }
        }

        binding.profile.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAccountFragment())
        }


        (activity as MainActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}