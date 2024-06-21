package com.bangkit.sibivisionproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.sibivisionproject.R
import android.content.Intent
import android.view.View.OnClickListener
import android.widget.Toast
import com.bangkit.sibivisionproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.footerRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.etUsername1.text.toString()
            val password = binding.etPassword1.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this)
                { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Sucessful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login Unsucessful", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Enter Username and Password", Toast.LENGTH_SHORT).show()
            }
            binding.loginButton.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }
    }
}