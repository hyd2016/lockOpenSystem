package com.learn.lockopensystem.model

import androidx.lifecycle.MutableLiveData
import com.learn.lockopensystem.model.getresult.Lock
import com.learn.lockopensystem.model.getresult.OauthResult
import com.learn.lockopensystem.model.getresult.Openrecord
import com.learn.lockopensystem.model.getresult.UserInfo

object DataCenter {
    var oauthResult: OauthResult? = OauthResult(1440, 0, "asagdsagd")
    var locks: MutableLiveData<MutableList<Lock>>? = null

    fun removeLock(lockId: Int) {
        locks?.value?.let {
            for (lock in it) {
                if (lock.lockid == lockId) {
                    it.remove(lock)
                }
            }
            locks?.postValue(it)
        }
    }
}