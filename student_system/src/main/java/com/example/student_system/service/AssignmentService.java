package com.example.student_system.service;

import com.example.student_system.entity.Assignment;
import com.example.student_system.mapper.AssignmentMapper;
import com.example.student_system.mapper.SubmissionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AssignmentService {
    @Resource
    AssignmentMapper assignmentMapper;
    @Resource
    SubmissionMapper submissionMapper;

    public Assignment getAssignmentById(int assignment_id){
        return assignmentMapper.getAssignmentById(assignment_id);
    }

    public boolean isCourseHaveAssignment(int course_id){
        int n=assignmentMapper.getCourseAssignment(course_id);
        return n != 0;
    }

    public List<Integer> getCourseAssignmentIdList(int course_id){
        return assignmentMapper.getCourseAssignmentIdList(course_id);
    }

    public List<Assignment> getCourseAssignmentList(int course_id,int student_id){
        return assignmentMapper.getCourseAssignmentList(course_id,student_id);
    }

    public List<Assignment> getDoneAssignmentList(int student_id){
        return assignmentMapper.getDoneAssignmentList(student_id);
    }

    public List<Assignment> getUnDoAssignmentList(int student_id){
        return assignmentMapper.getUnDoAssignmentList(student_id);
    }

    public int addSubmission(int assignment_id, int student_id){
        return submissionMapper.insertSubmission(assignment_id, student_id);
    }

    public int submitAssignment(int assignment_id, int student_id, String file_path, Timestamp submission_date,String file_name){
        return submissionMapper.submitAssignment(assignment_id, student_id, file_path, submission_date, file_name);
    }

    public int deleteSubmission(int assignment_id, int student_id){
        return submissionMapper.deleteSubmission(assignment_id, student_id);
    }
}
