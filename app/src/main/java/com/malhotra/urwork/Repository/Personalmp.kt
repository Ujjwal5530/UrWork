package com.malhotra.urwork.Repository

import com.google.firebase.firestore.FirebaseFirestore
import com.malhotra.urwork.ModelClass.ServicesData
import com.malhotra.urwork.R

class Personalmp(val database : FirebaseFirestore) : PersonalRepository {
    override fun getPersonalServices(): List<ServicesData> {
        val servicesList = arrayListOf<ServicesData>()

        servicesList.add(ServicesData(1, R.drawable.salon, "Hair Salon"))
        servicesList.add(ServicesData(2, R.drawable.spa, "Spa"))
        servicesList.add(ServicesData(3, R.drawable.nail, "Nails"))
        servicesList.add(ServicesData(4, R.drawable.massage, "Massage"))
        servicesList.add(ServicesData(5, R.drawable.makeup_artist, "Make-up Artist"))

        return servicesList
    }

}