package com.example.student_system.service;

import com.example.student_system.entity.Student;
import com.example.student_system.mapper.StudentMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsManager {
    @Resource
    PasswordEncoder encoder;

    @Resource
    StudentMapper mapper;

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student=mapper.FindStudentByUsername(username);
        if(student==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return User
                .withUsername(username)
                .password(encoder.encode(student.getPassword()))
                .build();
    }
}
