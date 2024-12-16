package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.mapper.TeacherMapper;
import com.example.teacher_system.sevice.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Random;

@Controller
@Validated
public class ForgetPwController {
    @Resource
    TeacherMapper mapper;

    @Resource
    UserService userService;

    @Resource
    JavaMailSender sender;

    @RequestMapping("/forgot")
    public String forgot_pws(){
        return "forgot";
    }

    @RequestMapping("/reset")
    public String reset_pws(HttpSession session){
        if((boolean) session.getAttribute("authenticated")) {
            return "reset";
        }
        return "redirect:forgot";
    }

    @ResponseBody
    @PostMapping("/getForgetCode")
    public String getForgetCode(HttpSession session,
                           @RequestParam @Length(min = 5,max = 30) @Email String email){
        System.out.println("正在给"+email+"发送验证码");
        Teacher teacher=mapper.FindTeacherByEmail(email);
        if(teacher==null) return "该邮箱未被注册";
        Random random=new Random();
        int code= random.nextInt(900000)+100000;
        String code_str=Integer.toString(code);
        Timestamp time=new Timestamp(System.currentTimeMillis());
        session.setAttribute("forgetCode",code_str);
        session.setAttribute("forgetEmail",email);
        session.setAttribute("getCode_time",time);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("13626658674@163.com");
        message.setSubject("您收到的验证码");
        message.setText("您的验证码是："+code+"验证码将在5min后失效，请妥善保管您的验证码");
        message.setTo(email);
        sender.send(message);
        return "验证码已成功发送";
    }

    @ResponseBody
    @PostMapping("/doForget")
    public String doForget(HttpSession session,
                           @RequestParam @Length(min = 5,max = 30) @Email String email,
                           @RequestParam String code){
        System.out.println("验证验证码");
        Timestamp now=new Timestamp(System.currentTimeMillis());
        Timestamp sessionTime=(Timestamp) session.getAttribute("getCode_time");
        long lastTime=(now.getTime()-sessionTime.getTime())/1000;
        if(lastTime>=300){
            session.removeAttribute("forgetCode");
            return "验证码已失效，请重新获取验证码";
        }

        String sessionCode=(String) session.getAttribute("forgetCode");
        String sessionEmail=(String) session.getAttribute("forgetEmail");
        if(sessionCode==null||!sessionEmail.equals(email)) return "请先获取验证码";
        if(!sessionCode.equals(code)) {
            return "验证码错误";
        }
        else{
            session.setAttribute("userEmail",email);
            session.setAttribute("authenticated",true);
            return "验证成功";
        }
    }

    @ResponseBody
    @PostMapping("/doReset")
    public String doReset(HttpSession session,
                          @RequestParam @Length(min = 5,max = 30) String password1,
                          @RequestParam @Length(min = 5,max = 30) String password2){
        Teacher teacher=mapper.FindTeacherByEmail((String) session.getAttribute("userEmail"));
        if(password1.equals(password2)){
            int n=mapper.UpdatePassword(teacher.getTeacher_id(),password1);
            if(n==0) return "修改密码失败";
            session.removeAttribute("userEmail");
            return "重置密码成功";
        }
        return "请输入相同的密码";
    }
}
