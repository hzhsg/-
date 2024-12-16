package com.example.teacher_system.sevice;

import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.mapper.TeacherMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    TeacherMapper teacherMapper;

    public Teacher getUser(){
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return teacherMapper.FindTeacherByUsername(userDetails.getUsername());
    }

    public int updateSignature(int student_id,String signature){
        return teacherMapper.UpdateSignature(student_id,signature);
    }

    public int updateInformation(int student_id,String name,String department,String information){
        return teacherMapper.UpdateInformation(student_id,name,department,information);
    }

    public int updatePassword(int student_id,String password){
        return teacherMapper.UpdatePassword(student_id,password);
    }
}
