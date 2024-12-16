package com.example.teacher_system.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Assignment {
    int assignment_id;
    int course_id;
    String title;
    String description;
    Timestamp created_time;
    Timestamp deadline;
    String course_name;

    public Assignment(int assignment_id, int course_id, String title, String description, Timestamp created_time, Timestamp deadline) {
        this.assignment_id = assignment_id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.created_time = created_time;
        this.deadline = deadline;
    }

    public Assignment(int assignment_id, int course_id, String title, String description, Timestamp created_time, Timestamp deadline, String course_name) {
        this.assignment_id = assignment_id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.created_time = created_time;
        this.deadline = deadline;
        this.course_name = course_name;
    }
}
