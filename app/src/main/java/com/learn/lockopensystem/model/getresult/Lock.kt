package com.learn.lockopensystem.model.getresult

data class Lock(
    val lockid: Int,
    val lockname: String,
    val patchtime: String,
    val status: Int,
    val updatetime: String
)