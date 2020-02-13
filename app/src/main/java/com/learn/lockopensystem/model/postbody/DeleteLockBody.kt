package com.learn.lockopensystem.model.postbody

data class DeleteLockBody(
    val token: String,
    val lockid: String
)