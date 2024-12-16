package com.example.teacher_system.sevice;

import com.example.teacher_system.entity.Student;
import com.example.teacher_system.mapper.EnrollmentMapper;
import com.example.teacher_system.mapper.StudentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StudentService {
    @Resource
    EnrollmentMapper enrollmentMapper;
    @Resource
    StudentMapper studentMapper;

    public List<Student> getStudentListWithEnrollmentDate(int course_id){
        return studentMapper.getStudentListWithEnrollmentDateByCourseId(course_id);
    }

    public Student getStudentById(int student_id){
        return studentMapper.getStudentById(student_id);
    }

    public List<Integer> getStudentIdList(int course_id){
        return enrollmentMapper.getStudentIdListByCourseId(course_id);
    }

    public boolean CourseHaveStudent(int course_id, int student_id){
        int n=enrollmentMapper.getCountStudentId(course_id,student_id);
        return n != 0;
    }

    public boolean haveStudent(int student_id){
        int n=studentMapper.countStudentById(student_id);
        return n != 0;
    }

    public int deleteStudent(int student_id, int course_id){
        return enrollmentMapper.deleteStudent(student_id,course_id);
    }
}
