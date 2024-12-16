package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Course;
import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.mapper.TeacherMapper;
import com.example.teacher_system.sevice.AssignmentService;
import com.example.teacher_system.sevice.CourseService;
import com.example.teacher_system.sevice.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Resource
    CourseService courseService;
    @Resource
    UserService userService;
    @Resource
    AssignmentService assignmentService;

    @RequestMapping("/")
    public String index(HttpSession session, Model model){
        Teacher user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        List<Course> courseList=courseService.getCoursesByTid(user.getTeacher_id());
        model.addAttribute("courseNum",courseList.size());
        int assignmentNum=0;
        for(Course course:courseList) {
            assignmentNum+=assignmentService.getAssignmentNum(course.getCourse_id());
        }
        model.addAttribute("assignmentNum",assignmentNum);
        return "index";
    }
}
