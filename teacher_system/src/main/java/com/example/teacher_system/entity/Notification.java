package com.example.teacher_system.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notification {
    int notification_id;
    String title;
    String message;
    Timestamp created_date;
    boolean status;
    String student_name;
    String student_username;

    public Notification(int notification_id, String title, String message, Timestamp created_date, boolean status, String student_name, String student_username) {
        this.notification_id = notification_id;
        this.title = title;
        this.message = message;
        this.created_date = created_date;
        this.status = status;
        this.student_name = student_name;
        this.student_username = student_username;
    }
}
