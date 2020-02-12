package com.learn.lockopensystem.model.getresult

/*
获取用户的锁
 */

data class LockInfo(
    val lock: List<Lock>,
    val result: Int,
    val total: Int
)
