package com.example.student_system.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Course {
    int course_id;
    int teacher_id;
    String course_name;
    String description;
    Timestamp created_time;
    Timestamp deadline;
    String teacher_name;
    String teacher_username;
    String department;

    public Course(int course_id, int teacher_id, String course_name, String description, Timestamp created_time, Timestamp deadline, String teacher_name, String teacher_username, String department) {
        this.course_id = course_id;
        this.teacher_id = teacher_id;
        this.course_name = course_name;
        this.description = description;
        this.created_time = created_time;
        this.deadline = deadline;
        this.teacher_name = teacher_name;
        this.teacher_username = teacher_username;
        this.department = department;
    }

    public Course(int course_id, int teacher_id, String course_name, String description, Timestamp created_time, Timestamp deadline) {
        this.course_id = course_id;
        this.teacher_id = teacher_id;
        this.course_name = course_name;
        this.description = description;
        this.created_time = created_time;
        this.deadline = deadline;
    }
}
