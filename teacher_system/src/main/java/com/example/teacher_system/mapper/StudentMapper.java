package com.example.teacher_system.mapper;

import com.example.teacher_system.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select s.student_id, s.username, s.name, s.created_time, s.department, e.enrollment_date "+
            "from student s "+
            "join enrollment e on s.student_id = e.student_id "+
            "where e.course_id = #{course_id}" )
    List<Student> getStudentListWithEnrollmentDateByCourseId(int course_id);

    @Select("select s.student_id, s.username, s.name, s.email, s.information, s.created_time, s.signature, s.department" +
            " from student s" +
            " where student_id = #{student_id}")
    Student getStudentById(int student_id);

    @Select("select count(*) from student where student_id = #{student_id}")
    int countStudentById(int student_id);
}
