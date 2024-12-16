package com.example.student_system.mapper;

import com.example.student_system.entity.Student;
import com.example.student_system.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select * from student where username=#{username}")
    Student FindStudentByUsername(String username);

    @Select("select * from student where email=#{email}")
    Student FindStudentByEmail(String email);

    @Select("select s.student_id, s.username, s.name, s.created_time, s.department, e.enrollment_date "+
            "from student s "+
            "join enrollment e on s.student_id = e.student_id "+
            "where e.course_id = #{course_id}" )
    List<Student> getStudentListWithEnrollmentDateByCourseId(int course_id);

    @Select("select s.student_id, s.username, s.name, s.email, s.information, s.created_time, s.signature, s.department" +
            " from student s" +
            " where student_id = #{student_id}")
    Student getStudentById(int student_id);

    @Insert("insert into student (username,password,email) values (#{username},#{password},#{email})")
    int Register(String username,String password,String email);

    @Update("update student set password=#{password} where student_id=#{student_id}")
    int UpdatePassword(int student_id,String password);

    @Update("update student set signature=#{signature} where student_id=#{student_id}")
    int UpdateSignature(int student_id,String signature);

    @Update("update student set name=#{name},department=#{department},information=#{information} where student_id=#{student_id}")
    int UpdateInformation(int student_id, String name,String department,String information);

}
