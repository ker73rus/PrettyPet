package com.example.prettypet.activities

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.prettypet.GlobalStuff
import com.example.prettypet.models.Note
import com.example.prettypet.databinding.ActivityPetBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat


class PetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetBinding
    val db = FirebaseFirestore.getInstance()
    val today = SimpleDateFormat("dd.MM.yy").format(Calendar.getInstance().time)
    var curDay = today.substring(0,2).toInt()
    var curM = today.substring(3,5).toInt()
    var curY = today.substring(6,8).toInt()
    var curNote = ""
    var note = 0
    var Notes = ArrayList<Note>()
    var NotesId = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db.collection("PetDiary").whereEqualTo("petId",GlobalStuff.curKitten).get().addOnSuccessListener {
            if (!it.isEmpty) {
                val noteDate = it.documents.last()?.get("date").toString()
                if (noteDate.substring(6, 8).toInt() <= curY)
                    if (noteDate.substring(3, 5).toInt() <= curM)
                        if (noteDate.substring(0, 2).toInt() <= curDay) {
                            updateDate(noteDate)
                            curNote = it.documents.last().id
                            note = it.documents.size - 1
                        }
                for (n in it.documents) {
                    Notes.add(
                        Note(
                            n.data?.get("petId").toString(),
                            n.data?.get("date").toString(),
                            n.data?.get("text").toString()
                        )
                    )
                    NotesId.add(n.id)
                }
            }
                if (Notes.size <= 1) {
                    binding.buttonLeft2.visibility = View.INVISIBLE
                    binding.buttonRight2.visibility = View.INVISIBLE
                    binding.textView4.text = "Тут пока пусто"
                    binding.textView3.text =
                        curDay.toString() + "." + curM.toString() + "." + curY.toString()
                } else {
                    binding.buttonLeft2.visibility = View.VISIBLE
                    binding.buttonRight2.visibility = View.VISIBLE

                    binding.textView4.text = Notes[note].text
                }



        }
        binding.buttonLeft2.setOnClickListener {
            if (note -1 < 0){
                note = Notes.size-1
            }
            else note--
            updateDate(Notes[note].date)
            binding.textView4.text = Notes[note].text

        }
        binding.buttonRight2.setOnClickListener {
            if(note +1 >= Notes.size)
                note = 0
            else note++
            updateDate(Notes[note].date)
            binding.textView4.text = Notes[note].text

        }
        binding.petCardButton.setOnClickListener {
            val intent = Intent(this, PetCardActivity::class.java)
            startActivity(intent)
        }
        binding.createNote.setOnClickListener {
            if (binding.createNote.text == "Сделать запись"){
                binding.editTextText.visibility = View.VISIBLE
                binding.textView4.visibility = View.INVISIBLE
                binding.createNote.text = "Сохранить"
            }
            else{
                db.collection("PetDiary").add(Note(GlobalStuff.curKitten, SimpleDateFormat("dd.MM.yy").format(Calendar.getInstance().time),binding.editTextText.text.toString() )).addOnSuccessListener {
                    binding.editTextText.visibility = View.INVISIBLE
                    binding.textView4.visibility = View.VISIBLE
                    binding.createNote.text = "Сделать запись"
                    db.collection("PetDiary").whereEqualTo("petId",GlobalStuff.curKitten).get().addOnSuccessListener {
                        val noteDate = it.documents.last()?.get("date").toString()
                        if(noteDate.substring(6,8).toInt() <= curY)
                            if (noteDate.substring(3,5).toInt() <= curM)
                                if (noteDate.substring(0,2).toInt() <= curDay){
                                    updateDate(noteDate)
                                    curNote = it.documents.last().id
                                    note = it.documents.size-1
                                }
                        for (n in it.documents){
                            Notes.add(Note(n.data?.get("petId").toString(),n.data?.get("date").toString(),n.data?.get("text").toString()))
                            NotesId.add(n.id)
                        }
                        if (Notes.size <= 1){
                            binding.buttonLeft2.visibility = View.INVISIBLE
                            binding.buttonRight2.visibility = View.INVISIBLE
                        }
                        else{
                            binding.buttonLeft2.visibility = View.VISIBLE
                            binding.buttonRight2.visibility = View.VISIBLE
                        }
                        binding.textView3.text = curDay.toString() + "." + curM.toString()+ "."  + curY.toString()
                        binding.textView4.text = Notes[note].text
                    }
                }
            }
        }
    }

    fun updateDate(date:String){
        curDay = date.substring(0,2).toInt()
        curM = date.substring(3,5).toInt()
        curY = date.substring(6,8).toInt()
        binding.textView3.text = curDay.toString() + "." + curM.toString()+ "."  + curY.toString()
    }

}