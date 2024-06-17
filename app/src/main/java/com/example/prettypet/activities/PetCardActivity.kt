package com.example.prettypet.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.prettypet.databinding.ActivityPetBinding
import com.example.prettypet.databinding.ActivityPetCardBinding

class PetCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPetCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}