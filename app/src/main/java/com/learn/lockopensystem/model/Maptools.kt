package com.learn.lockopensystem.model

import java.util.*

object Maptools {
    fun objectToMap(obj: Any): Map<String, String> {
        val map = HashMap<String, String>()
        val clazz = obj.javaClass
        for (field in clazz.declaredFields) {
            field.isAccessible = true
            val fieldName = field.name
            val value = field.get(obj) as String
            map[fieldName] = value
        }
        return map
    }
}