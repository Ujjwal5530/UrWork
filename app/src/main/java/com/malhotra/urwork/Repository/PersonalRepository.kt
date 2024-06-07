package com.malhotra.urwork.Repository

import com.malhotra.urwork.ModelClass.ServicesData

interface PersonalRepository {
    fun getPersonalServices() : List<ServicesData>

}