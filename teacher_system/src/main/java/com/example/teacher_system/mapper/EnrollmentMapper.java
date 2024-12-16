package com.example.teacher_system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface EnrollmentMapper {
    @Select("select count(*) from enrollment where course_id=#{course_id}")
    int getStudentCountByCourseId(int course_id);

    @Select("select count(*) from enrollment where course_id=#{course_id} and student_id=#{student_id}")
    int getCountStudentId(int course_id,int student_id);

    @Select("select student_id from enrollment where course_id=#{course_id}")
    List<Integer> getStudentIdListByCourseId(int course_id);

    @Insert("insert into enrollment(student_id, course_id) values(#{student_id}, #{course_id})")
    int addStudent(int student_id, int course_id);

    @Delete("delete from enrollment where student_id=#{student_id} and course_id=#{course_id}")
    int deleteStudent(int student_id, int course_id);

    @Delete("delete from enrollment where course_id=#{course_id}")
    int deleteCourseStudent(int course_id);
}
