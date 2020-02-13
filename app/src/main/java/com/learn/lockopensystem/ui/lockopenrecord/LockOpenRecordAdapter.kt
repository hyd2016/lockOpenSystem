package com.learn.lockopensystem.ui.lockopenrecord

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.lockopensystem.R
import com.learn.lockopensystem.model.getresult.Openrecord
import kotlinx.android.synthetic.main.item_lock_open_record.view.*


class LockOpenRecordAdapter(private val openrecords: List<Openrecord>) :
    RecyclerView.Adapter<LockOpenRecordAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lock_open_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        openrecords?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val openrecord = openrecords[position]
        holder.openId.text = openrecord.openid.toString()
        holder.userId.text = openrecord.userid.toString()
        holder.lockOpenTime.text = openrecord.opentime

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val openId: TextView = view.open_id
        val userId: TextView = view.user_id
        val lockOpenTime: TextView = view.lock_open_time

    }
}