package com.learn.lockopensystem.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.model.getresult.Lock

// 该类没得任何用，就中转个数据，浪费点内存，懒得改
class HomeViewModel : ViewModel() {

    var locks: MutableLiveData<MutableList<Lock>>? = null
    init {
        DataCenter.locks?.let {
            locks = it
        }
    }
}