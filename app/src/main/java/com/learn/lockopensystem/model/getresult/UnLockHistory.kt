package com.learn.lockopensystem.model.getresult

/*
 查询开锁记录
 */

data class UnLockHistory(
    val openrecord: List<Openrecord>,
    val result: Int,
    val total: Int
)

