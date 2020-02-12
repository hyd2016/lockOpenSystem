package com.learn.lockopensystem.model.postbody

data class PasswordBody(
    val phonenum:String,
    val smscode:String,
    val password:String
)