package com.example.student_system.mapper;

import com.example.student_system.entity.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherMapper {
    @Select("select * from teacher where username=#{username}")
    Teacher FindTeacherByUsername(String username);

    @Select("select * from teacher where username=#{email}")
    Teacher FindTeacherByEmail(String email);

    @Insert("insert into teacher (username,password,email) values (#{username},#{password},#{email})")
    int Register(String username,String password,String email);

    @Select("select teacher_id,username,name,email,information,created_time,signature,department" +
            " from teacher where teacher_id=#{teacher_id}")
    Teacher getTeacherById(int teacher_id);
}
