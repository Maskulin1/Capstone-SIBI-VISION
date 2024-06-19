package com.bangkit.sibivisionproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.sibivisionproject.R
import android.content.Intent
import android.widget.Toast
import com.bangkit.sibivisionproject.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            val email = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this)
                { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Register Sucessful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Register Unsucessful", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                Toast.makeText(this, "Enter Username and Password", Toast.LENGTH_SHORT).show()
            }
            binding.registerButton.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}