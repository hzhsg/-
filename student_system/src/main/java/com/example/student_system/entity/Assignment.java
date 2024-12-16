package com.example.student_system.entity;

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
    boolean submission_status;
    boolean correct_status;
    String course_name;

    public Assignment(int assignment_id, int course_id, String title, String description, Timestamp created_time, Timestamp deadline) {
        this.assignment_id = assignment_id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.created_time = created_time;
        this.deadline = deadline;
    }

    public Assignment(int assignment_id, int course_id, String title, String description, Timestamp created_time, Timestamp deadline, boolean submission_status, boolean correct_status) {
        this.assignment_id = assignment_id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.created_time = created_time;
        this.deadline = deadline;
        this.submission_status = submission_status;
        this.correct_status = correct_status;
    }

    public Assignment(int assignment_id, int course_id, String title, String description, Timestamp created_time, Timestamp deadline, boolean submission_status, boolean correct_status, String course_name) {
        this.assignment_id = assignment_id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.created_time = created_time;
        this.deadline = deadline;
        this.submission_status = submission_status;
        this.correct_status = correct_status;
        this.course_name = course_name;
    }
}
