package com.learn.lockopensystem.ui.lockalarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.lockopensystem.R
import com.learn.lockopensystem.model.getresult.Alarm
import kotlinx.android.synthetic.main.item_lock_alarm.view.*


class LockAlarmAdapter(private val alarms: List<Alarm>) :
    RecyclerView.Adapter<LockAlarmAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lock_alarm, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        alarms?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.alarmId.text = alarm.alarmid.toString()
        holder.alarmMessage.text = alarm.alarmmessage
        holder.alarmTime.text = alarm.alarmtime

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val alarmId: TextView = view.alarm_id
        val alarmMessage: TextView = view.alarm_message
        val alarmTime: TextView = view.alarm_time

    }
}