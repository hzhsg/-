package com.example.student_system.service;

import com.example.student_system.entity.Submission;
import com.example.student_system.mapper.SubmissionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {
    @Resource
    SubmissionMapper submissionMapper;

    public Submission getSubmission(int assignment_id,int student_id){
        return submissionMapper.getSubmission(assignment_id,student_id);
    }

    public Submission getSubmissionById(int assignment_id,int student_id){
        return submissionMapper.getSubmissionById(assignment_id,student_id);
    }

    public int getUnSubmittedNum(int student_id){
        return submissionMapper.getUnSubmittedNum(student_id);
    }

    public int getSubmittedNum(int student_id){
        return submissionMapper.getSubmittedNum(student_id);
    }

    public int getUnCorrectedNum(int student_id){
        return submissionMapper.getUnCorrectedNum(student_id);
    }

    public int getCorrectedNum(int student_id){
        return submissionMapper.getCorrectedNum(student_id);
    }
}
