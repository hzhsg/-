package com.example.student_system.mapper;

import com.example.student_system.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select * from course where course_id=#{course_id}")
    Course getCourseById(int course_id);

    @Select("select c.course_id,c.teacher_id,c.course_name,c.description,c.created_time,c.deadline,t.name,t.username,t.department" +
            " from course c" +
            " join teacher t on c.teacher_id=t.teacher_id" +
            " join enrollment e on c.course_id=e.course_id" +
            " where e.student_id=#{student_id}")
    List<Course> getStudentCourseList(int student_id);

    @Select("select c.course_id,c.teacher_id,c.course_name,c.description,c.created_time,c.deadline,t.name,t.username,t.department" +
            " from course c" +
            " join teacher t on c.teacher_id=t.teacher_id" +
            " where c.course_id not in(select e.course_id from enrollment e where e.student_id=#{student_id})" +
            " order by c.deadline desc")
    List<Course> getStudentCourseListNotEnroll(int student_id);
}
