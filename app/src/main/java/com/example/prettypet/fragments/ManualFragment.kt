package com.example.prettypet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prettypet.R
import com.example.prettypet.adapter.ManualAdapter
import com.example.prettypet.databinding.FragmentManualBinding

class ManualFragment : Fragment() {
    lateinit var binding: FragmentManualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManualBinding.inflate(inflater)
        val adapter = context?.let { ManualAdapter(it) }
        binding.rec.layoutManager = LinearLayoutManager(context)
        binding.rec.adapter = adapter

        return binding.root
    }

}