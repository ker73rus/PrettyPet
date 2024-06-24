package com.example.prettypet.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prettypet.GlobalStuff
import com.example.prettypet.R
import com.example.prettypet.adapter.ClinicAdapter
import com.example.prettypet.adapter.NotificationAdapter
import com.example.prettypet.databinding.FragmentClinicBinding
import com.example.prettypet.databinding.FragmentEventBinding
import com.example.prettypet.databinding.NotificationItemBinding
import com.example.prettypet.models.Notification
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar


class EventFragment : Fragment() {
    lateinit var binding: FragmentEventBinding
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)

        binding.createNot.setOnClickListener {
            if (binding.rec.visibility == View.VISIBLE){
                binding.rec.visibility = View.INVISIBLE
                binding.editNotName.visibility = View.VISIBLE
                binding.labelDate.visibility = View.VISIBLE
                binding.editTextDate2.visibility = View.VISIBLE
            }
            else{
                db.collection("notification").add(Notification(binding.editNotName.text.toString(),Timestamp.now(),GlobalStuff.user)).addOnCompleteListener{
                    binding.rec.visibility = View.VISIBLE
                    val notificationList = ArrayList<Notification>()
                    db.collection("notification").whereEqualTo("user",GlobalStuff.user).get().addOnSuccessListener {
                        for (i in it){
                            val name = i.data.get("name").toString()
                            val date = i.data.get("date")
                            if (date is Timestamp){
                                notificationList.add(Notification(name, date))
                            }

                        }

                        if (!notificationList.isEmpty()){
                            val adapter = NotificationAdapter(notificationList)
                            binding.rec.layoutManager = LinearLayoutManager(context)
                            binding.rec.adapter = adapter
                        }
                        else
                            binding.empty.visibility = View.VISIBLE
                    }
                    binding.editNotName.visibility = View.INVISIBLE
                    binding.editNotName.text.clear()
                    binding.editTextDate2.visibility = View.INVISIBLE
                    binding.editTextDate2.text.clear()
                    binding.labelDate.visibility = View.INVISIBLE
                }

            }


        }

        var notificationList = ArrayList<Notification>()
        db.collection("notification").whereEqualTo("user",GlobalStuff.user).get().addOnSuccessListener {
            for (i in it){
                val name = i.data.get("name").toString()
                val date = i.data.get("date")
                if (date is Timestamp){
                    notificationList.add(Notification(name, date))
                }
                else{
                    notificationList.add(Notification(name,Timestamp.now()))
                }

            }

            if (!notificationList.isEmpty()){
                val adapter = NotificationAdapter(notificationList)
                binding.rec.layoutManager = LinearLayoutManager(context)
                binding.rec.adapter = adapter
            }
            else
                binding.empty.visibility = View.VISIBLE
        }


        return binding.root
    }

}