//package com.bangkit.sibivisionproject.ui
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.bangkit.sibivisionproject.databinding.ActivityEditProfileBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class EditProfileActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityEditProfileBinding
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var databaseReference: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityEditProfileBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        val uid = firebaseAuth.currentUser?.uid
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
//
//        loadUserData()
//
//        binding.btnSave.setOnClickListener {
//            saveUserData()
//        }
//    }
//
//    private fun loadUserData() {
//        databaseReference.get().addOnSuccessListener {
//            if (it.exists()) {
//                val name = it.child("name").value.toString()
//                val username = it.child("username").value.toString()
//                val email = it.child("email").value.toString()
//
//                binding.etName.setText(if (name != "null") name else "")
//                binding.etUsername.setText(if (username != "null") username else "")
//                binding.etEmail.setText(if (email != "null") email else "")
//            }
//        }
//    }
//
//    private fun saveUserData() {
//        val name = binding.etName.text.toString()
//        val username = binding.etUsername.text.toString()
//        val email = binding.etEmail.text.toString()
//        val password = binding.etPassword.text.toString()
//
//        val userMap = hashMapOf<String, Any>(
//            "name" to name,
//            "username" to username,
//            "email" to email
//        )
//
//        databaseReference.updateChildren(userMap).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                if (password.isNotEmpty()) {
//                    firebaseAuth.currentUser?.updatePassword(password)?.addOnCompleteListener { passwordTask ->
//                        if (passwordTask.isSuccessful) {
//                            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                } else {
//                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
//
package com.bangkit.sibivisionproject.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.sibivisionproject.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

            // Example: Load user data from the database
            databaseReference.get().addOnSuccessListener {
                if (it.exists()) {
                    val username = it.child("username").value as String
                    val email = it.child("email").value as String
                    binding.etUsername.setText(username)
                    binding.etEmail.setText(email)
                } else {
                    Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

