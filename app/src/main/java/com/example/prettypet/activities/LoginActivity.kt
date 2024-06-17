package com.example.prettypet.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prettypet.GlobalStuff
import com.example.prettypet.R
import com.example.prettypet.databinding.ActivityLoginBinding
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userNumber: EditText = findViewById(R.id.editTextPhone)
        val userPassword: EditText = findViewById(R.id.editTextNumberPassword)
        val registrationButton: Button = findViewById(R.id.registrationButton)
        val enterButton: Button = findViewById(R.id.enterButton)

        enterButton.setOnClickListener{
            val phone = userNumber.text.toString()
            val password = userPassword.text.toString()
            db.collection("user").whereEqualTo("phone",phone).get().addOnSuccessListener {
                for (u in it) {
                    if (u.get("password") == password){
                        Toast.makeText(this,"Cool",Toast.LENGTH_SHORT).show()
                        GlobalStuff.user = phone
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                        Toast.makeText(this,"Пароль неверный",Toast.LENGTH_SHORT).show()
                }
                if(it.documents.isEmpty()){
                    Toast.makeText(this,"Такой пользователь не зарегистрирован",Toast.LENGTH_SHORT).show()
                }
            }

        }
        registrationButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}