package com.example.prettypet.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prettypet.R
import com.example.prettypet.databinding.ActivityRegistrationBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userNumber: EditText = findViewById(R.id.editTextPhone)
        val userPassword: EditText = findViewById(R.id.editTextPassword)
        val userlabelPassword: TextView = findViewById(R.id.labelpassword)
        val loginButton: Button = findViewById(R.id.registrationButton)
        val enterButton: Button = findViewById(R.id.enterButton)
        var step = 0
        val sms = Random.nextInt(1000,9999)
        enterButton.setOnClickListener{
            if (step == 0){
                val phone = userNumber.text.toString()

                db.collection("user").whereEqualTo("phone",phone).get().addOnSuccessListener  {
                    for(u in it){
                        Toast.makeText(this,"${u.data.get("phone")} Уже зарегистрирован",Toast.LENGTH_LONG).show()
                    }

                    if (it.documents.isEmpty()){
                        userlabelPassword.text = "Введите код из смс"
                        userPassword.visibility = View.VISIBLE
                        userlabelPassword.visibility = View.VISIBLE
                        userNumber.isEnabled = false
                        Toast.makeText(this,"${sms}",Toast.LENGTH_SHORT).show()
                        step = 1
                    }

                }
            }
            else if(step == 1){
                if(userPassword.text.toString() == sms.toString()){
                    userPassword.text.clear()
                    userlabelPassword.text = "Придумайте пароль"
                    step = 2
                }
            }
            else if( step == 2){
                if (userPassword.text.toString() != ""){
                    val user = hashMapOf(
                        "phone" to userNumber.text.toString(),
                        "password" to userPassword.text.toString()
                    )
                    db.collection("user").add(user)
                        .addOnFailureListener {  }
                    Toast.makeText(this,"Вы зарегистрировались", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }

            }

        }
        loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}