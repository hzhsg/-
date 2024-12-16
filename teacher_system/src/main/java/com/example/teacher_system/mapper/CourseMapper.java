package com.example.teacher_system.mapper;

import com.example.teacher_system.entity.Course;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select * from course where teacher_id=#{teacher_id}")
    List<Course> FindCourseListByTeacherId(int teacher_id);

    @Select("select * from course where course_id=#{course_id}")
    Course FindCourseById(int course_id);

    @Insert("insert into course(teacher_id,course_name,description,deadline) values(#{teacher_id},#{course_name},#{description},#{deadline})")
    int InsertCourse(int teacher_id, String course_name, String description, Timestamp deadline);

    @Update("update course set course_name=#{course_name},description=#{description},deadline=#{deadline} where course_id=#{course_id}")
    int UpdateCourse(int course_id, String course_name, String description, Timestamp deadline);

    @Delete("delete from course where course_id=#{course_id}")
    int DeleteCourse(int course_id);
}
