package com.malhotra.urwork.ViewModel

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.malhotra.urwork.Activites.PartnerActivity
import com.malhotra.urwork.ModelClass.PartnerData

class PartnerViewModel(private val database : FirebaseDatabase = FirebaseDatabase.getInstance(),
                       private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
                       private val auth : FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    fun loginPartner(email : String, password : String, context: Context, activity : FragmentActivity?){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                //setting partner log = true
                activity?.getSharedPreferences("partnerLogin", Context.MODE_PRIVATE)
                    ?.edit()?.putBoolean("isLoggedIn", true)?.apply()

                Handler(Looper.getMainLooper()).postDelayed({
                    ContextCompat.startActivity(
                        context,
                        Intent(context, PartnerActivity::class.java),
                        null
                    )
                    activity?.finish()
                },600)
            }else Toast.makeText(context, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun logout(activity : FragmentActivity?){
        //setting partner log = false
        activity?.getSharedPreferences("partnerLogin", Context.MODE_PRIVATE)
            ?.edit()?.putBoolean("isLoggedIn", false)?.apply()
        auth.signOut()
    }

    fun registerPartner(email: String, password: String, partner : PartnerData, context: Context, activity : FragmentActivity?){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){

                //setting partner log = true
                activity?.getSharedPreferences("partnerLogin", Context.MODE_PRIVATE)
                    ?.edit()?.putBoolean("isLoggedIn", true)?.apply()

                partner.id = it.result.user?.uid
                updatePartner(partner)
                Handler(Looper.getMainLooper()).postDelayed({
                    ContextCompat.startActivity(
                        context,
                        Intent(context, PartnerActivity::class.java),
                        null
                    )
                    activity?.finish()
                    Toast.makeText(context, "Register Complete", Toast.LENGTH_SHORT).show()
                },600)
            } else {
                try {
                    throw it.exception ?: java.lang.Exception("Invalid authentication")
                } catch (e: FirebaseAuthWeakPasswordException) {
                    Toast.makeText(context, "Authentication failed, Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "Authentication failed, Invalid email entered", Toast.LENGTH_SHORT).show()
                } catch (e: FirebaseAuthUserCollisionException) {
                    Toast.makeText(context, "Authentication failed, Email already registered.", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun updatePartner(partner: PartnerData){
        firestore.collection("Partners").document(partner.id.toString()).set(partner)
        database.getReference("Partners").child(partner.category.toString()).child(partner.subCategory.toString())
            .child(partner.id.toString()).setValue(partner)
    }


    private val _partner = MutableLiveData<PartnerData>()
    val partner : LiveData<PartnerData> get() = _partner
    fun getPartnerData(id : String){
        firestore.collection("Partners").document(id).get().addOnSuccessListener {
            val name = it.get("name").toString()
            val email = it.get("email").toString()
            val phone = it.get("phone").toString()
            val category = it.get("category").toString()

            _partner.value = PartnerData(id, name, email, phone, category)
        }
    }

    fun emailQuery(email : String) {
        firestore.collection("Partners").whereEqualTo(FieldPath.of("email"), email).get().addOnSuccessListener {
            if (it.isEmpty){
                _result.value = true
            }
        }
    }

    private var _result = MutableLiveData<Boolean>()
    val result : LiveData<Boolean> get() =  _result

//    fun idQuery(id : String){
//        val list = arrayListOf<PartnerData>()
//        val reference = database.reference.child("Partners")
//            .orderByChild("id").equalTo(id)
//
//        reference.get().addOnSuccessListener {
//            for (snap in it.children){
//                val taxData = snap.getValue(PartnerData::class.java)
//                if (taxData != null) list.add(taxData)
//            }
//
//            _result.value = list.isEmpty()
//        }
//    }

}