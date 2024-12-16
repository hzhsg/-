package com.example.teacher_system.sevice;

import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.mapper.TeacherMapper;
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
    TeacherMapper mapper;

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
        Teacher teacher=mapper.FindTeacherByUsername(username);
        if(teacher==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return User
                .withUsername(username)
                .password(encoder.encode(teacher.getPassword()))
                .build();
    }
}
