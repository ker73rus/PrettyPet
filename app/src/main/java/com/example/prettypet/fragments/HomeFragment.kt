package com.example.prettypet.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.prettypet.GlobalStuff
import com.example.prettypet.models.Pet
import com.example.prettypet.R
import com.example.prettypet.activities.PetActivity
import com.example.prettypet.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val kittens = intArrayOf(
        R.drawable.kitten_1,
        R.drawable.kitten_2,
        R.drawable.kitten_3,
        R.drawable.kitten_4
    )
    var cur_kitten = 0
    var userCats : ArrayList<Pet> = ArrayList()
    var kittensId : ArrayList<String> = ArrayList()
    val db = FirebaseFirestore.getInstance()
    var curCat = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    fun showCloseCreate(show : Boolean){
        if(show){
            binding.createPetConfirm.visibility = View.VISIBLE
            binding.buttonLeft.visibility = View.VISIBLE
            binding.buttonRight.visibility = View.VISIBLE
            binding.editTextName.visibility = View.VISIBLE
            binding.imageView.visibility = View.VISIBLE
            binding.createPet.visibility = View.INVISIBLE
            binding.buttonLeft2.visibility = View.INVISIBLE
            binding.buttonRight2.visibility = View.INVISIBLE
            binding.textName.visibility = View.INVISIBLE
            binding.imageView2.visibility = View.INVISIBLE

        }
        else
        {
            binding.createPetConfirm.visibility = View.INVISIBLE
            binding.buttonLeft.visibility = View.INVISIBLE
            binding.buttonRight.visibility = View.INVISIBLE
            binding.editTextName.visibility = View.INVISIBLE
            binding.imageView.visibility = View.INVISIBLE
            binding.createPet.visibility = View.VISIBLE
            if (userCats.count() > 1){
                binding.buttonLeft2.visibility = View.VISIBLE
                binding.buttonRight2.visibility = View.VISIBLE
            }
            binding.textName.visibility = View.VISIBLE
            binding.imageView2.visibility = View.VISIBLE
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        db.collection("pet").whereEqualTo("owner", GlobalStuff.user).get().addOnSuccessListener {
            for(p in it){
                userCats.add(
                    Pet(
                        p.data.get("name").toString(),
                        p.data.get("icon").toString().toInt(),
                        p.data.get("owner").toString()
                    )
                )
                kittensId.add(p.id)
            }

            showKittens()
        }
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }
    fun showKittens(){
        if (!userCats.isEmpty()){
            binding.imageView2.visibility = View.VISIBLE
            binding.imageView2.setImageResource(kittens[userCats[curCat].icon])
            binding.textName.text = userCats[curCat].name
            if (userCats.count() > 1)
            {
                binding.buttonLeft2.visibility = View.VISIBLE
                binding.buttonRight2.visibility = View.VISIBLE
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!userCats.isEmpty()){
            binding.imageView2.visibility = View.VISIBLE
            binding.imageView2.setImageResource(kittens[userCats[curCat].icon])
            binding.textName.text = userCats[curCat].name
            binding.textName.visibility = View.VISIBLE
            if (userCats.count() > 1)
            {
                binding.buttonLeft2.visibility = View.VISIBLE
                binding.buttonRight2.visibility = View.VISIBLE
            }
        }
        binding.buttonRight2.setOnClickListener {
            if(curCat >=userCats.count()-1){
                curCat = 0
            }
            else curCat++
            binding.imageView2.setImageResource(kittens[userCats[curCat].icon])
            binding.textName.text = userCats[curCat].name
        }
        binding.buttonLeft2.setOnClickListener {
            if(curCat <= 0){
                curCat = userCats.count()-1
            }
            else curCat--
            binding.imageView2.setImageResource(kittens[userCats[curCat].icon])
            binding.textName.text = userCats[curCat].name
        }



        binding.createPet.setOnClickListener {
            showCloseCreate(true)
        }
        binding.createPetConfirm.setOnClickListener {
            val name  = binding.editTextName.text.toString()
            if (name != "")
            {
                db.collection("pet").add(Pet(name, cur_kitten, GlobalStuff.user)).addOnCompleteListener() {
                    binding.editTextName.text.clear()
                    cur_kitten = 0
                    db.collection("pet").whereEqualTo("owner", GlobalStuff.user).get().addOnSuccessListener {
                        for(p in it){
                            userCats.add(
                                Pet(
                                    p.data.get("name").toString(),
                                    p.data.get("icon").toString().toInt(),
                                    p.data.get("owner").toString()
                                )
                            )
                        }
                        showKittens()
                        showCloseCreate(false)
                        Toast.makeText(context, "Питомец создан", Toast.LENGTH_LONG).show()
                    }

                }

            }

        }
        binding.buttonRight.setOnClickListener {
            if(cur_kitten >=3){
                cur_kitten = 0
            }
            else cur_kitten++
            binding.imageView.setImageResource(kittens[cur_kitten])
        }
        binding.buttonLeft.setOnClickListener {
            if(cur_kitten <=0 ){
                cur_kitten = 3
            }
            else cur_kitten--
            binding.imageView.setImageResource(kittens[cur_kitten])
        }
        binding.imageView2.setOnClickListener {
            GlobalStuff.curKitten = kittensId[curCat].toString()
            val intent = Intent(this.context, PetActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
            }
    }
}