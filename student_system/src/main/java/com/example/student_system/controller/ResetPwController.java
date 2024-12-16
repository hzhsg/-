package com.example.student_system.controller;

import com.example.student_system.entity.Student;
import com.example.student_system.service.UserService;
import jakarta.annotation.Resource;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
                             @RequestParam @Length(min = 5,max = 15) String newPassword1,
                             @RequestParam @Length(min = 5,max = 15) String newPassword2){
        Student user=userService.getUser();
        String password=user.getPassword();
        if(oldPassword.equals(password)){
            if(newPassword1.equals(newPassword2)){
                int n= userService.updatePassword(user.getStudent_id(),newPassword1);
                if(n==0) return "修改密码失败";
                return "修改密码成功";
            }
            return "两次输入的密码不同";
        }
        return "原密码错误";
    }
}
