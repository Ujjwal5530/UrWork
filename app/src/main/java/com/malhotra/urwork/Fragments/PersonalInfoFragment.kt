package com.malhotra.urwork.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.set
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.malhotra.urwork.ModelClass.UserData
import com.malhotra.urwork.R
import com.malhotra.urwork.ViewModel.UserViewModel
import com.malhotra.urwork.databinding.FragmentPersonalInfoBinding

class PersonalInfoFragment : Fragment() {
    private var _binding : FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private val userViewModel : UserViewModel by viewModels()
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val storage : StorageReference = FirebaseStorage.getInstance().getReference("Users")

    private var imageuri : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)

        val id = auth.currentUser?.uid.toString()

        storage.child(id).downloadUrl.addOnSuccessListener { uri ->
            Glide.with(requireContext()).load(uri).into(binding.accProfile)
            imageuri = uri.toString()
        }

        userViewModel.getUserData(id)
        userViewModel.user.observe(viewLifecycleOwner){
            binding.personalName.setText(it.name)
            binding.personalEmail.setText(it.email)

            if (it.phone.isNullOrEmpty()){
                binding.addPhone.visibility = View.VISIBLE
                binding.personalPhone.visibility = View.GONE
            }else {
                binding.addPhone.visibility = View.GONE
                binding.personalPhone.visibility = View.VISIBLE
                binding.personalPhone.setText(it.phone)
            }
        }

        val changeImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK) {
                val imageUri = it.data?.data
                binding.accProfile.setImageURI(imageUri)
                storage.child(id).putFile(imageUri!!).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Photo Uploaded", Toast.LENGTH_SHORT).show()
                    storage.child(id).downloadUrl.addOnSuccessListener {uri ->
                        Glide.with(requireContext()).load(uri).into(binding.accProfile)
                        userViewModel.setImage(uri.toString(), id)
                    }
                }
            }
        }

        binding.textView.setOnClickListener {
           changeImage.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
        }

        binding.done.setOnClickListener {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val emailView = binding.personalEmail
            imm.hideSoftInputFromWindow(emailView.windowToken,0)

            val name = binding.personalName.text.toString()
            val email = binding.personalEmail.text.toString()
            val phone = binding.personalPhone.text.toString()

            userViewModel.updateUser(UserData(id, name, email, phone, imageuri))

            Navigation.findNavController(it).navigate(PersonalInfoFragmentDirections.actionPersonalInfoFragmentToAccountFragment())
            Toast.makeText(requireContext(), "Account information Saved", Toast.LENGTH_SHORT).show()
        }

        binding.addPhone.setOnClickListener {
            binding.addPhone.visibility = View.GONE
            binding.personalPhone.visibility = View.VISIBLE
        }

        binding.back.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(PersonalInfoFragmentDirections.actionPersonalInfoFragmentToAccountFragment())
        }

        return binding.root
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}