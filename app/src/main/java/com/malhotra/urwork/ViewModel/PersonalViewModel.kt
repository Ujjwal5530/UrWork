package com.malhotra.urwork.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malhotra.urwork.ModelClass.ServicesData
import com.malhotra.urwork.Repository.PersonalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor
    (val repository: PersonalRepository) : ViewModel() {

        private val _services = MutableLiveData<List<ServicesData>>()
        val services : LiveData<List<ServicesData>> get() = _services
        fun getPersonalServices() {
            _services.value = repository.getPersonalServices()
        }

}