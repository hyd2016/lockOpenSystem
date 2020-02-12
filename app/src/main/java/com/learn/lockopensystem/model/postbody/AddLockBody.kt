package com.learn.lockopensystem.model.postbody

data class AddLockBody(
    val lockid: String,
    val lockpass: String,
    val token: String
)