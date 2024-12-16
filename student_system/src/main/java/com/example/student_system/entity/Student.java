package com.example.student_system.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Student {
    int student_id;
    String username;
    String name;
    String password;
    String email;
    String information;
    Timestamp created_time;
    String signature;
    String department;
    Timestamp enrollment_date;

    public Student(int student_id, String username, String name, String password, String email, String information, Timestamp created_time, String signature, String department) {
        this.student_id = student_id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.information = information;
        this.created_time = created_time;
        this.signature = signature;
        this.department = department;
    }

    public Student(int student_id, String username, String name, String email, String information, Timestamp created_time, String signature, String department) {
        this.student_id = student_id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.information = information;
        this.created_time = created_time;
        this.signature = signature;
        this.department = department;
    }

    public Student(int student_id, String username, String name, Timestamp created_time, String department, Timestamp enrollment_date) {
        this.student_id = student_id;
        this.username = username;
        this.name = name;
        this.created_time = created_time;
        this.department = department;
        this.enrollment_date = enrollment_date;
    }
}
