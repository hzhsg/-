package com.example.teacher_system.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static Timestamp StringToTimestamp(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);// 解析字符串为 LocalDateTime 对象
        return Timestamp.valueOf(dateTime);
    }
}
