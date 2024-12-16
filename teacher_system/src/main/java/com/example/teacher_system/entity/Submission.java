package com.example.teacher_system.entity;

import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
public class Submission {
    int submission_id;
    int assignment_id;
    int student_id;
    Timestamp submission_date;
    String file_path;
    Integer version;
    Integer score;
    String feedback;
    Timestamp feedback_date;
    boolean submission_status;
    boolean correct_status;
    String file_name;
    String student_name;
    String student_username;

    public Submission(int submission_id, int assignment_id, int student_id, Timestamp submission_date, String file_path, Integer version, Integer score, String feedback, Timestamp feedback_date, boolean submission_status, boolean correct_status, String file_name, String student_name, String student_username) {
        this.submission_id = submission_id;
        this.assignment_id = assignment_id;
        this.student_id = student_id;
        this.submission_date = submission_date;
        this.file_path = file_path;
        this.version = version;
        this.score = score;
        this.feedback = feedback;
        this.feedback_date = feedback_date;
        this.submission_status = submission_status;
        this.correct_status = correct_status;
        this.file_name=file_name;
        this.student_name = student_name;
        this.student_username = student_username;
    }

    public Submission(int submission_id, int assignment_id, int student_id, Timestamp submission_date, String file_path, Integer version, Integer score, String feedback, Timestamp feedback_date, boolean submission_status, boolean correct_status, String file_name) {
        this.submission_id = submission_id;
        this.assignment_id = assignment_id;
        this.student_id = student_id;
        this.submission_date = submission_date;
        this.file_path = file_path;
        this.version = version;
        this.score = score;
        this.feedback = feedback;
        this.feedback_date = feedback_date;
        this.submission_status = submission_status;
        this.correct_status = correct_status;
        this.file_name=file_name;
    }
}
