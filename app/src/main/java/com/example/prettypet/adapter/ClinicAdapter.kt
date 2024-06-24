package com.example.prettypet.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prettypet.R
import com.example.prettypet.databinding.ClinicItemBinding
import com.example.prettypet.models.Clinic

class ClinicAdapter(val context: Context): RecyclerView.Adapter<ClinicAdapter.ClinicHolder>() {
    val clinicList = ArrayList<Clinic>(listOf(
        Clinic("Ковчег","г. Ульянвоск \nул. Федерации, 105","https://kovcheg-vet.ru"),
        Clinic("9 жизней","г. Ульянвоск \nБуинский пер., 1","https://vetklinika73.ru"),
        Clinic("Доктор Зoo","г. Ульянвоск \nКазанская ул., 1А","https://doczoo.ru"),
        Clinic("ЗооРитм","г. Ульянвоск \nул. Артёма, 7/59","https://vk.com/zoorythm"),
        Clinic("Велес","г. Ульянвоск \nул. Аблукова, 20","https://veterinarnaja-klinika-veles.clients.site")
    ))
    class ClinicHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = ClinicItemBinding.bind(item)
        fun bind(clinic: Clinic, context: Context) = with(binding){
            titile.text = clinic.title
            description.text = clinic.adress
            imageButton.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(clinic.link))
                context.startActivity(browserIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clinic_item,parent,false)
        return ClinicHolder(view)
    }

    override fun getItemCount(): Int {
        return clinicList.size
    }

    override fun onBindViewHolder(holder: ClinicHolder, position: Int) {
        holder.bind(clinicList[position],context)
    }
}