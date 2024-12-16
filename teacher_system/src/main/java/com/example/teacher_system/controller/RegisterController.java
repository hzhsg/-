package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.mapper.TeacherMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Random;

@Controller
@Validated
public class RegisterController {
    @Resource
    JavaMailSender sender;
    @Resource
    TeacherMapper mapper;

    @RequestMapping("/register")
    public String Register(){
        return "register";
    }

    @ResponseBody
    @PostMapping("/getCode")
    public String getCode(HttpSession session,
                           @RequestParam @Length(min = 5,max = 30) @Email String email){
        System.out.println("正在执行");
        Teacher student=mapper.FindTeacherByEmail(email);
        if(student!=null) return "该邮箱已被注册";

        Random random=new Random();
        int code=random.nextInt(900000)+100000;
        String code1=Integer.toString(code);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        session.setAttribute("registerCode",code1);
        session.setAttribute("registerEmail",email);
        session.setAttribute("lastTime",now);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("13626658674@163.com");
        message.setSubject("您收到的验证码");
        message.setText("您的验证码是："+code+"验证码将在5min后失效，请妥善保管您的验证码");
        message.setTo(email);
        sender.send(message);
        return "验证码已成功发送";
    }

    @ResponseBody
    @PostMapping("/doRegister")
    public String doRegister(HttpSession session,
                           @RequestParam @Length(min = 5,max = 30) String username,
                           @RequestParam @Length(min = 5,max = 30) String password,
                           @RequestParam @Length(min = 5,max = 30) @Email String email,
                           @RequestParam String code){
        System.out.println(username);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        Timestamp past=(Timestamp) session.getAttribute("lastTime");
        long lastTime=(now.getTime()-past.getTime())/1000;      //单位为s
        if(lastTime>=300){
            session.removeAttribute("registerCode");
            return "验证码已失效，请重新获取验证码";
        }

        String sessionCode=(String) session.getAttribute("registerCode");
        String sessionEmail=(String) session.getAttribute("registerEmail");
        if(sessionCode==null) return "请先获取验证码";
        if(!sessionEmail.equals(email)) return "请先获取验证码";
        if(!sessionCode.equals(code)) return "验证码错误";

        Teacher teacher1=mapper.FindTeacherByUsername(username);
        Teacher teacher2=mapper.FindTeacherByEmail(email);
        if(teacher1!=null) return "该用户名已被注册";
        if(teacher2!=null) return "该邮箱已被注册";


        int n= mapper.Register(username,password,email);
        if(n==0) return "注册失败";
        return "注册成功";
    }
}
