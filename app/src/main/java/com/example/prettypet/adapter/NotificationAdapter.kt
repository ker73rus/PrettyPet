package com.example.prettypet.adapter

import android.content.Context
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prettypet.R
import com.example.prettypet.databinding.NotificationItemBinding
import com.example.prettypet.models.Notification
import java.text.SimpleDateFormat

class NotificationAdapter(val notificationList : ArrayList<Notification>): RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {
    class NotificationHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = NotificationItemBinding.bind(item)
        fun bind(notification: Notification, position: Int) = with(binding){
            name.text = (position+1).toString() + ". " + notification.name
            date.text = (SimpleDateFormat("kk").format(notification.date.toDate()).toInt() + 4).toString()  + SimpleDateFormat(":mm   dd.MM.yy").format(notification.date.toDate())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item,parent,false)
        return NotificationHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.bind(notificationList[position],position)
    }
}