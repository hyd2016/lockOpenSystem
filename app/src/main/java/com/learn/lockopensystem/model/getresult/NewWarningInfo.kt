package com.learn.lockopensystem.model.getresult

import com.learn.lockopensystem.model.getresult.AlarmX

/*
查询新告警
 */

data class NewWarningInfo(
    val alarm: List<AlarmX>,
    val result: Int,
    val total: Int
)