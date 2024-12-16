package com.example.student_system.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Enrollment {
    int enrollment_id;
    int student_id;
    int course_id;
    Timestamp enrollment_date;

    public Enrollment(int enrollment_id, int student_id, int course_id, Timestamp enrollment_date) {
        this.enrollment_id = enrollment_id;
        this.student_id = student_id;
        this.course_id = course_id;
        this.enrollment_date = enrollment_date;
    }
}
