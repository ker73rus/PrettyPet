package com.example.prettypet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prettypet.R
import com.example.prettypet.adapter.ClinicAdapter
import com.example.prettypet.adapter.ManualAdapter
import com.example.prettypet.databinding.FragmentClinicBinding
import com.example.prettypet.databinding.FragmentManualBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ClinicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClinicFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentClinicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClinicBinding.inflate(inflater)
        val adapter = context?.let { ClinicAdapter(it) }
        binding.rec.layoutManager = LinearLayoutManager(context)
        binding.rec.adapter = adapter

        return binding.root
    }

}