package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.mapper.TeacherMapper;
import com.example.teacher_system.sevice.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.http.HttpRequest;

@Controller
@Validated
public class ResetPwController {
    @Resource
    UserService userService;

    @RequestMapping("/reset_pws")
    public String reset_pws(){
        return "reset_pws";
    }

    @ResponseBody
    @PostMapping("/doResetPws")
    public String doResetPws(@RequestParam String oldPassword,
                             @RequestParam @Length(min = 5,max = 30) String newPassword1,
                             @RequestParam @Length(min = 5,max = 30) String newPassword2){
        Teacher user=userService.getUser();
        String password=user.getPassword();
        if(oldPassword.equals(password)){
            if(newPassword1.equals(newPassword2)){
                int n= userService.updatePassword(user.getTeacher_id(),newPassword1);
                if(n==0) return "修改密码失败";
                return "修改密码成功";
            }
            return "两次输入的密码不同";
        }
        return "原密码错误";
    }
}
