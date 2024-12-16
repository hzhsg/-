package com.example.student_system.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notification {
    int notification_id;
    String title;
    String message;
    Timestamp created_date;
    boolean status;
    String teacher_name;
    String teacher_username;

    public Notification(int notification_id, String title, String message, Timestamp created_date, boolean status, String teacher_name, String teacher_username) {
        this.notification_id = notification_id;
        this.title = title;
        this.message = message;
        this.created_date = created_date;
        this.status = status;
        this.teacher_name = teacher_name;
        this.teacher_username = teacher_username;
    }
}
