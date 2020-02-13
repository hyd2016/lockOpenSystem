package com.learn.lockopensystem.model.getresult

/*
用户登录
 */

data class OauthResult(
    val expiresin: Int,
    val result: Int,
    val token: String
)