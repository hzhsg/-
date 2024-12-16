package com.example.student_system.controller;

import com.example.student_system.entity.Student;
import com.example.student_system.mapper.StudentMapper;
import com.example.student_system.service.SubmissionService;
import com.example.student_system.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Resource
    UserService userService;
    @Resource
    SubmissionService submissionService;

    @RequestMapping("/")
    public String index(HttpSession session, Model model){
        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        model.addAttribute("haveDone",submissionService.getSubmittedNum(user.getStudent_id()));
        model.addAttribute("toBeDone",submissionService.getUnSubmittedNum(user.getStudent_id()));
        model.addAttribute("toBeCorrected",submissionService.getUnCorrectedNum(user.getStudent_id()));
        model.addAttribute("haveCorrected",submissionService.getCorrectedNum(user.getStudent_id()));

        return "index";
    }
}
