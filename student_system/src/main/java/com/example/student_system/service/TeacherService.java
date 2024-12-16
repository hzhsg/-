package com.example.student_system.service;

import com.example.student_system.entity.Teacher;
import com.example.student_system.mapper.TeacherMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Resource
    TeacherMapper teacherMapper;

    public Teacher getTeacherById(int id){
        return teacherMapper.getTeacherById(id);
    }
}
