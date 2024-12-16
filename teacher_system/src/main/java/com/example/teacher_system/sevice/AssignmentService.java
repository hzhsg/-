package com.example.teacher_system.sevice;

import com.example.teacher_system.entity.Assignment;
import com.example.teacher_system.entity.Submission;
import com.example.teacher_system.mapper.AssignmentMapper;
import com.example.teacher_system.mapper.SubmissionMapper;
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

    public boolean courseHaveAssignment(int course_id){
        int n=assignmentMapper.getCourseAssignment(course_id);
        return n != 0;
    }

    public int getAssignmentNum(int course_id){
        return assignmentMapper.getCourseAssignment(course_id);
    }

    public List<Integer> getCourseAssignmentIdList(int course_id){
        return assignmentMapper.getCourseAssignmentIdList(course_id);
    }

    public Assignment getAssignmentById(int assignment_id){
        return assignmentMapper.getAssignmentById(assignment_id);
    }

    public List<Assignment> getCourseAssignmentList(int course_id){
        return assignmentMapper.getCourseAssignmentList(course_id);
    }

    public List<Assignment> getAllAssignmentList(int teacher_id){
        return assignmentMapper.getAllAssignmentList(teacher_id);
    }

    public int addAssignment(int course_id, String title, String description, Timestamp deadline){
        return assignmentMapper.addAssignment(course_id, title, description, deadline);
    }

    public int getAssignmentInsertId(){
        return assignmentMapper.getLastInsertId();
    }

    public int addSubmission(int assignment_id, int student_id){
        return submissionMapper.insertSubmission(assignment_id, student_id);
    }

    public int deleteSubmission(int assignment_id){
        return submissionMapper.deleteSubmissionByAssignmentId(assignment_id);
    }

    public int deleteStudentSubmission(int assignment_id, int student_id){
        return submissionMapper.deleteStudentSubmission(assignment_id, student_id);
    }

    public int editAssignment(int assignment_id, String title, String description, Timestamp deadline){
        return assignmentMapper.updateAssignment(assignment_id, title, description, deadline);
    }

    public int deleteAssignment(int assignment_id){
        return assignmentMapper.deleteAssignment(assignment_id);
    }
}
