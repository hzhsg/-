package com.example.student_system.service;

import com.example.student_system.entity.Student;
import com.example.student_system.mapper.StudentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Resource
    StudentMapper studentMapper;

    public List<Student> getStudentListWithEnrollmentDate(int course_id){
        return studentMapper.getStudentListWithEnrollmentDateByCourseId(course_id);
    }

    public Student getStudentById(int student_id){
        return studentMapper.getStudentById(student_id);
    }
}
