package com.learn.lockopensystem.model

import androidx.lifecycle.MutableLiveData
import com.learn.lockopensystem.model.getresult.Lock

object DataCenter {
    fun getLocks(): MutableLiveData<MutableList<Lock>> {
        val locks:MutableLiveData<MutableList<Lock>> = MutableLiveData()
        val listLocks = ArrayList<Lock>()
        val lock1 = Lock(1, "我的锁", "2019/12/17 17:34:10", 1, "2019/12/16 2:47:14")
        val lock2 = Lock(2, "我的锁锁", "2019/12/18 17:34:10", 1, "2019/12/18 2:47:14")
        val lock3 = Lock(3, "123", "2019/12/19 17:34:10", 1, "2019/12/19 2:47:14")
        listLocks.add(lock1)
        listLocks.add(lock2)
        listLocks.add(lock3)
        locks.value = listLocks
        return locks
    }
}