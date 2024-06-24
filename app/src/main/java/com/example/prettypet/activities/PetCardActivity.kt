package com.example.prettypet.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.prettypet.GlobalStuff
import com.example.prettypet.databinding.ActivityPetBinding
import com.example.prettypet.databinding.ActivityPetCardBinding
import com.example.prettypet.models.Kitten
import com.google.firebase.firestore.FirebaseFirestore

class PetCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetCardBinding
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPetCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}