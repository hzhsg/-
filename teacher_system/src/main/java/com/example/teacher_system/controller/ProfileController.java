package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.mapper.TeacherMapper;
import com.example.teacher_system.sevice.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Validated
public class ProfileController {
    @Resource
    UserService userService;

    @RequestMapping("/profile")
    public String profile(Model model){
        Teacher user=userService.getUser();
        System.out.println(user.getSignature());
        System.out.println(user.getInformation());
        if(user.getName()==null) {
            model.addAttribute("show_name", user.getUsername());
        }
        else{
            model.addAttribute("show_name",user.getName());
        }
        model.addAttribute("signature",user.getSignature());
        model.addAttribute("name",user.getName());
        model.addAttribute("teacherId",user.getTeacher_id());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("department",user.getDepartment());
        model.addAttribute("information",user.getInformation());
        return "profile";
    }

    @ResponseBody
    @PostMapping("/saveInformation")
    public String saveInformation(@RequestParam String signature){
        Teacher user=userService.getUser();
        int n=userService.updateSignature(user.getTeacher_id(),signature);
        if(n==0) return "保存失败";
        return "保存成功";
    }

    @ResponseBody
    @PostMapping("/updateInformation")
    public String updateInformation(@RequestParam String name,
                                    @RequestParam String department,
                                    @RequestParam String information){
        Teacher user=userService.getUser();
        int n= userService.updateInformation(user.getTeacher_id(),name,department,information);
        if(n==0) return "更新失败";
        return "更新成功";
    }
}