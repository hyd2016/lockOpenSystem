package com.learn.lockopensystem.model.postbody

data class ChangeLockNameBody(
    val token: String,
    val lockid: String,
    val lockname: String
)