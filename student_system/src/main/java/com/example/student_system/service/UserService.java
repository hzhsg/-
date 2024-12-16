package com.example.student_system.service;


import com.example.student_system.entity.Student;
import com.example.student_system.mapper.StudentMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    StudentMapper studentMapper;

    public Student getUser(){
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return studentMapper.FindStudentByUsername(userDetails.getUsername());
    }

    public int updateSignature(int student_id,String signature){
        return studentMapper.UpdateSignature(student_id,signature);
    }

    public int updateInformation(int student_id,String name,String department,String information){
        return studentMapper.UpdateInformation(student_id,name,department,information);
    }

    public int updatePassword(int student_id,String password){
        return studentMapper.UpdatePassword(student_id,password);
    }
}
