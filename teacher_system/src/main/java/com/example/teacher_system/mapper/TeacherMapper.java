package com.example.teacher_system.mapper;

import com.example.teacher_system.entity.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TeacherMapper {
    @Select("select * from teacher where username=#{username}")
    Teacher FindTeacherByUsername(String username);

    @Select("select * from teacher where email=#{email}")
    Teacher FindTeacherByEmail(String email);

    @Insert("insert into teacher (username,password,email) values (#{username},#{password},#{email})")
    int Register(String username,String password,String email);

    @Update("update teacher set password=#{password} where teacher_id=#{teacher_id}")
    int UpdatePassword(int teacher_id,String password);

    @Update("update teacher set signature=#{signature} where teacher_id=#{teacher_id}")
    int UpdateSignature(int teacher_id,String signature);

    @Update("update teacher set name=#{name},department=#{department},information=#{information} where teacher_id=#{teacher_id}")
    int UpdateInformation(int teacher_id,String name,String department,String information);
}
