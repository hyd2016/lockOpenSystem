package com.learn.lockopensystem.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.lockopensystem.model.DataCenter
import com.learn.lockopensystem.model.getresult.Lock

class HomeViewModel : ViewModel() {

    private val _locks = MutableLiveData<MutableList<Lock>>().apply {
        value = DataCenter.getLocks().value
    }
    val locks:LiveData<MutableList<Lock>> = _locks
}