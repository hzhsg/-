package com.example.student_system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EnrollmentMapper {
    @Select("select count(*) from enrollment where student_id = #{student_id} and course_id = #{course_id}")
    int checkEnrollment(int student_id, int course_id);

    @Insert("insert into enrollment(student_id, course_id) values(#{student_id}, #{course_id})")
    int enrollCourse(int student_id, int course_id);

    @Delete("delete from enrollment where student_id = #{student_id} and course_id = #{course_id}")
    int quitCourse(int student_id, int course_id);
}
