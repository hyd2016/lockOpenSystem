//package com.learn.lockopensystem.model;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Map转换工具类
// */
//
//
//public class MapToolsUtil {
//    /**
//     * 将Object对象里面的属性和值转化成Map对象
//     *
//     * @return
//     * @throws IllegalAccessException
//     */
//    public static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
//        Map<String, String> map = new HashMap<String,String>();
//        Class<?> clazz = obj.getClass();
//        for (Field field : clazz.getDeclaredFields()) {
//            field.setAccessible(true);
//            String fieldName = field.getName();
//            String value = (String) field.get(obj);
//            map.put(fieldName, value);
//        }
//        return map;
//    }
//}
