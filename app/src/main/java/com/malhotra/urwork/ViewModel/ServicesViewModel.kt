package com.malhotra.urwork.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.malhotra.urwork.ModelClass.ServicesData

class ServicesViewModel : ViewModel() {

    private val database : DatabaseReference = FirebaseDatabase.getInstance()
        .getReference("Services")

    private val _personalList = MutableLiveData<ArrayList<ServicesData>>()
    val personalList : LiveData<ArrayList<ServicesData>> get() = _personalList
    fun getPersonalServices(){
        val list = arrayListOf<ServicesData>()
        database.child("Personal Services").get().addOnSuccessListener {
            for (snap in it.children){
                val service = snap.getValue(ServicesData::class.java)
                if (service != null) list.add(service)
            }
            _personalList.value = list
        }
    }

    private val _homeList = MutableLiveData<ArrayList<ServicesData>>()
    val homeList : LiveData<ArrayList<ServicesData>> get() = _homeList

    fun getHomeServices(){
        val list = arrayListOf<ServicesData>()
        database.child("Home Services").get().addOnSuccessListener {
            for (snap in it.children){
                val service = snap.getValue(ServicesData::class.java)
                if (service != null) list.add(service)
            }
            _homeList.value = list
        }
    }
}