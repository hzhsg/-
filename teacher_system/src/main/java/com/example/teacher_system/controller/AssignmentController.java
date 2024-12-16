package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Assignment;
import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.sevice.AssignmentService;
import com.example.teacher_system.sevice.UserService;
import jakarta.annotation.Resource;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AssignmentController {
    @Resource
    UserService userService;
    @Resource
    AssignmentService assignmentService;

    @RequestMapping("/assignment")
    public String assignment(Model model){
        Teacher user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }

        List<Assignment> assignmentList= assignmentService.getAllAssignmentList(user.getTeacher_id());
        model.addAttribute("assignmentList",assignmentList);

        return "assignment";
    }
}
