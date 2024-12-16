package com.example.teacher_system.sevice;

import com.example.teacher_system.entity.Course;
import com.example.teacher_system.entity.Enrollment;
import com.example.teacher_system.mapper.AssignmentMapper;
import com.example.teacher_system.mapper.CourseMapper;
import com.example.teacher_system.mapper.EnrollmentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CourseService {
    @Resource
    CourseMapper courseMapper;
    @Resource
    EnrollmentMapper enrollmentMapper;
    @Resource
    AssignmentMapper assignmentMapper;

    public List<Course> getCoursesByTid(int teacher_id) {
        return courseMapper.FindCourseListByTeacherId(teacher_id);
    }

    public Course getCourseById(int course_id) {
        return courseMapper.FindCourseById(course_id);
    }

    public int getStudentNum(int course_id){
        return enrollmentMapper.getStudentCountByCourseId(course_id);
    }

    public int deleteCourseStudent(int course_id){
        return enrollmentMapper.deleteCourseStudent(course_id);
    }

    public int deleteCourseAssignment(int course_id){
        return assignmentMapper.deleteCourseAssignment(course_id);
    }

    public int addCourse(int teacher_id, String course_name, String description, Timestamp deadline) {
        return courseMapper.InsertCourse(teacher_id,course_name,description,deadline);
    }

    public int editCourse(int course_id,String course_name, String description, Timestamp deadline) {
        return courseMapper.UpdateCourse(course_id,course_name,description,deadline);
    }

    public int deleteCourse(int course_id) {
        return courseMapper.DeleteCourse(course_id);
    }

    public int addStudent(int student_id, int course_id) {
        return enrollmentMapper.addStudent(student_id,course_id);
    }
}
