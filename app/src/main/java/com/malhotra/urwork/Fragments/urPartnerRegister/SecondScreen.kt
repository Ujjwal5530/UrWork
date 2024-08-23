package com.malhotra.urwork.Fragments.urPartnerRegister

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.malhotra.urwork.ModelClass.PartnerData
import com.malhotra.urwork.R
import com.malhotra.urwork.ViewModel.PartnerViewModel
import com.malhotra.urwork.ViewModel.ServicesViewModel
import com.malhotra.urwork.databinding.FragmentSecondBinding
import com.malhotra.urwork.databinding.FragmentUrPartnerLoginBinding

class SecondScreen : Fragment() {

    private val args : SecondScreenArgs by navArgs()

    private var _binding : FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val serviceViewModel : ServicesViewModel by viewModels()

    private val partnerViewModel : PartnerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        lateinit var itemSelected : String
        lateinit var category : String

        serviceViewModel.getPersonalServices()
        val personalList = arrayListOf<String>()
        serviceViewModel.personalList.observe(viewLifecycleOwner){ it ->
            it.forEach {data ->
                personalList.add(data.title.toString())
            }
        }
        val homeList = arrayListOf<String>()
        serviceViewModel.getHomeServices()
        serviceViewModel.homeList.observe(viewLifecycleOwner){
            it.forEach {data ->
                homeList.add(data.title.toString())
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkID ->
            val list = if (checkID == binding.homeServices.id){
                homeList
            } else personalList

            category = if (checkID == binding.homeServices.id){
                "Home Services"
            }else "Personal Services"

            Log.d("Category", category)

            val autoCompleteTextView : AutoCompleteTextView = binding.autoCompleteTextView
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, list)
            autoCompleteTextView.setAdapter(adapter)
            autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener{
                    adapterView, view, i, l ->

                val item = adapterView.getItemAtPosition(i)
                itemSelected = item.toString()
                Log.d("SubCategory", itemSelected)
            }
        }

        binding.next.setOnClickListener {

            val partnerData = PartnerData("", args.Name, args.Email, args.Phone, category, itemSelected, binding.city.text.toString())
            Log.d("Category", category)

            Log.d("SubCategory", itemSelected)
            binding.loading.visibility = View.VISIBLE
            Navigation.findNavController(it).navigate(SecondScreenDirections
                .actionSecondScreenToThirdScreen(partnerData))

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}