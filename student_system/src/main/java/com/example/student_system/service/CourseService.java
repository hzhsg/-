package com.example.student_system.service;

import com.example.student_system.entity.Course;
import com.example.student_system.mapper.CourseMapper;
import com.example.student_system.mapper.EnrollmentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Resource
    CourseMapper courseMapper;
    @Resource
    EnrollmentMapper enrollmentMapper;

    public boolean isStudentHaveCourse(int student_id, int course_id){
        int n=enrollmentMapper.checkEnrollment(student_id,course_id);
        return n!=0;
    }

    public Course getCourseById(int course_id){
        return courseMapper.getCourseById(course_id);
    }

    public List<Course> getStudentCourseList(int student_id){
        return courseMapper.getStudentCourseList(student_id);
    }

    public List<Course> getStudentCourseListNotEnroll(int student_id){
        return courseMapper.getStudentCourseListNotEnroll(student_id);
    }

    public int enrollCourse(int student_id, int course_id){
        return enrollmentMapper.enrollCourse(student_id,course_id);
    }

    public int quitCourse(int student_id, int course_id){
        return enrollmentMapper.quitCourse(student_id,course_id);
    }
}
