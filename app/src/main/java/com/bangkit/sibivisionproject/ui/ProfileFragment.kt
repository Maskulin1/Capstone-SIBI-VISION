//package com.bangkit.sibivisionproject.ui
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.bangkit.sibivisionproject.databinding.FragmentProfileBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class ProfileFragment : Fragment() {
//
//    private var _binding: FragmentProfileBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var databaseReference: DatabaseReference
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        firebaseAuth = FirebaseAuth.getInstance()
//        val uid = firebaseAuth.currentUser?.uid
//
//        if (uid != null) {
//            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid)
//            loadUserProfile()
//        } else {
//            // Handle the case where uid is null
//            // For example, redirect to login
//            val intent = Intent(activity, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//        }
//
//        binding.tvEditProfile.setOnClickListener {
//            val intent = Intent(activity, EditProfileActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.tvInformationApps.setOnClickListener {
//            val intent = Intent(activity, InformationAppsActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.tvHelpdesk.setOnClickListener {
//            val intent = Intent(activity, HelpdeskActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.btnLogout.setOnClickListener {
//            firebaseAuth.signOut()
//            val intent = Intent(activity, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//        }
//
//        return binding.root
//    }
//
//    private fun loadUserProfile() {
//        databaseReference.get().addOnSuccessListener {
//            if (it.exists()) {
//                val name = it.child("username").value.toString()
//                binding.tvName.text = if (name != "null") name else "User"
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

package com.bangkit.sibivisionproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.sibivisionproject.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvEditProfile.setOnClickListener {
            startActivity(Intent(activity, EditProfileActivity::class.java))
        }

        binding.tvInformationApps.setOnClickListener {
            startActivity(Intent(activity, InformationAppsActivity::class.java))
        }

        binding.tvHelpdesk.setOnClickListener {
            startActivity(Intent(activity, HelpdeskActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

