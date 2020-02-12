package com.learn.lockopensystem.model.postbody

data class RegisterBody(
    val phonenum:String,
    val smscode:String,
    val password:String,
    val nickname:String)