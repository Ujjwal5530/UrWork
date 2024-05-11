package com.malhotra.urwork.Fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.malhotra.urwork.Activites.MainActivity
import com.malhotra.urwork.Activites.SplashScreen
import com.malhotra.urwork.R
import com.malhotra.urwork.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding : FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var dialog : Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.signout_dialog)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(getDrawable(requireContext(), R.drawable.custom_dialog))

        binding.back.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(AccountFragmentDirections.actionAccountFragmentToHomeFragment())
        }

        binding.signOut.setOnClickListener {
            dialog.show()
            dialog.findViewById<TextView>(R.id.confirm_button).setOnClickListener {
                dialog.dismiss()
                firebaseAuth.signOut()
                startActivity(Intent(requireContext(), SplashScreen::class.java))
                activity?.finish()
            }

            dialog.findViewById<TextView>(R.id.cancel_button).setOnClickListener {
                dialog.dismiss()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}