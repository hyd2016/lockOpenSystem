package com.learn.lockopensystem.model.getresult
/*
获取用户信息
 */

data class UserInfo(
    val nickname: String,
    val phonenum: String,
    val result: Int,
    val userid: Int
)