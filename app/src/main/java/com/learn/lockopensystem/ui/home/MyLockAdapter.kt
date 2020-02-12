package com.learn.lockopensystem.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.lockopensystem.R
import com.learn.lockopensystem.model.getresult.Lock
import kotlinx.android.synthetic.main.item_my_lock.view.*


public class MyLockAdapter(private val locks: MutableList<Lock>) :
    RecyclerView.Adapter<MyLockAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_lock, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        locks?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lock = locks[position]
        holder.lockIdView.text = lock.lockid.toString()
        holder.lockNickName.text = lock.lockname
        holder.lockStatus.text = if (lock.status == 0) "关闭" else "打开"
        holder.lockUpdateTime.text = lock.updatetime

        onClickListener?.let {
            holder.itemView.setOnClickListener {
                onClickListener?.setClickListener(lock.lockid.toString())
            }
        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lockIdView: TextView = view.lock_id
        val lockNickName: TextView = view.lock_name
        val lockStatus: TextView = view.lock_status
        val lockUpdateTime: TextView = view.lock_update_time

    }

    interface OnClickListener {
        fun setClickListener(lockId: String)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}