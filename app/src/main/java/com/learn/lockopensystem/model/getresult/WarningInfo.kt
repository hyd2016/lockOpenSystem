package com.learn.lockopensystem.model.getresult

import com.learn.lockopensystem.model.getresult.Alarm

/*
查询告警记录
 */

data class WarningInfo(
    val alarm: List<Alarm>,
    val result: Int,
    val total: Int
)