package com.malhotra.urwork.ViewModel

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.ModelClass.UserData

class UserViewModel(val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
                    , val auth: FirebaseAuth = FirebaseAuth.getInstance()) : ViewModel() {

    fun loginUser(email: String, password: String, context: Context, activity: FragmentActivity?){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                //setting user log = true
                activity?.getSharedPreferences("userLogin", Context.MODE_PRIVATE)
                    ?.edit()?.putBoolean("isLoggedIn", true)?.apply()

                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(context, Intent(context, MainActivity::class.java), null)
                    activity?.finish()
                },600)
            }else Toast.makeText(context, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun registerUser(email: String, password: String, user : UserData, context: Context, activity: FragmentActivity? ){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                //setting user log = true
                activity?.getSharedPreferences("userLogin", Context.MODE_PRIVATE)
                    ?.edit()?.putBoolean("isLoggedIn", true)?.apply()

                user.id = it.result.user?.uid.toString()
                updateUser(user)
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(context, Intent(context, MainActivity::class.java), null)
                    activity?.finish()
                }, 600)
            }else{
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

    fun logout(activity: FragmentActivity?){
        //setting user log = false
        activity?.getSharedPreferences("userLogin", Context.MODE_PRIVATE)
            ?.edit()?.putBoolean("isLoggedIn", false)?.apply()
        auth.signOut()
    }

    fun updateUser(user : UserData){
        firestore.collection("Users").document(user.id).set(user)
    }

    private val _user = MutableLiveData<UserData>()
    val user : LiveData<UserData> get() = _user
    fun getUserData(id : String){
        firestore.collection("Users").document(id).get().addOnSuccessListener {
            val name = it.get("name").toString()
            val email = it.get("email").toString()
            val phone = it.get("phone").toString()
            val image = it.get("image").toString()

            _user.value = UserData(id, name, email, phone, image)
        }
    }

    fun setImage(image : String, id: String){
        firestore.collection("Users").document(id).update("image", image)
    }
}