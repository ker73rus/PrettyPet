package com.example.prettypet.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prettypet.R
import com.example.prettypet.databinding.ManualItemBinding
import com.example.prettypet.models.Manual

class ManualAdapter(val context: Context): RecyclerView.Adapter<ManualAdapter.ManualHolder>() {
    val manualList = ArrayList<Manual>(listOf(
        Manual("Пособие по питанию","Расскажем как и чем лучше кормить вашего питомца","https://www.royalcanin.com/ru/cats/thinking-of-getting-a-cat/how-to-care-for-a-kitten-or-cat"),
        Manual("Поддержание здоровья шерсти","Несколько советов, о том как сделать ваших питомцев красивыми ","https://www.royalcanin.com/ru/cats/thinking-of-getting-a-cat/how-to-care-for-a-kitten-or-cat"),
        Manual("Помывка. Нужно ли?","Правила приёма ванной, нужно ли это питомцу?","https://www.royalcanin.com/ru/cats/thinking-of-getting-a-cat/how-to-care-for-a-kitten-or-cat"),
        Manual("Стрижка и вычёсывание","Как вычёсывать питомца и в каких случаях это вредно","https://www.royalcanin.com/ru/cats/thinking-of-getting-a-cat/how-to-care-for-a-kitten-or-cat"),
        Manual("Лайфхаки по уходу","Расскажем, что нравится вашему питомцу и как поддерживать его здоровье","https://www.royalcanin.com/ru/cats/thinking-of-getting-a-cat/how-to-care-for-a-kitten-or-cat")))
    class ManualHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = ManualItemBinding.bind(item)
        fun bind(manual: Manual, context: Context) = with(binding){
            titile.text = manual.title
            description.text = manual.description
            imageButton.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(manual.link))
                context.startActivity(browserIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManualHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.manual_item,parent,false)
        return ManualHolder(view)
    }

    override fun getItemCount(): Int {
        return manualList.size
    }

    override fun onBindViewHolder(holder: ManualHolder, position: Int) {
        holder.bind(manualList[position],context)
    }
}