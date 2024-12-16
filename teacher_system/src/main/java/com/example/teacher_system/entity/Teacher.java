package com.example.teacher_system.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Teacher {
    int teacher_id;
    String username;
    String name;
    String password;
    String email;
    String information;
    Timestamp created_time;
    String signature;
    String department;

    public Teacher(int teacher_id, String username, String name, String password, String email, String information, Timestamp created_time, String signature, String department) {
        this.teacher_id = teacher_id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.information = information;
        this.created_time = created_time;
        this.signature = signature;
        this.department = department;
    }
}
