package com.example.teacher_system.sevice;

import com.example.teacher_system.entity.Submission;
import com.example.teacher_system.mapper.SubmissionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {
    @Resource
    SubmissionMapper submissionMapper;

    public Submission getSubmissionById(int submission_id) {
        return submissionMapper.getSubmissionById(submission_id);
    }

    public int updateCorrectInformation(int submission_id, int score, String feedback) {
        return submissionMapper.updateCorrectInformation(submission_id, score, feedback);
    }

    public List<Submission> getUnSubmittedSubmissions(int assignment_id) {
        return submissionMapper.getUnSubmittedSubmissions(assignment_id);
    }

    public List<Submission> getSubmittedSubmissions(int assignment_id) {
        return submissionMapper.getSubmittedSubmissions(assignment_id);
    }
}
