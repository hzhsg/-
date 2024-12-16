package com.example.student_system.controller;

import com.example.student_system.entity.Student;
import com.example.student_system.service.StudentService;
import com.example.student_system.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentDetailController {
    @Resource
    StudentService studentService;
    @Resource
    UserService userService;

    @RequestMapping("/studentDetail")
    public String studentDetail(Model model, HttpSession session){
        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }

        System.out.println(session.getAttribute("StudentId"));
        if(session.getAttribute("StudentId")==null) return "redirect:/courseHaveJoined";

        int student_id=(int) session.getAttribute("StudentId");
        Student student=studentService.getStudentById(student_id);

        if(student.getName()==null) {
            model.addAttribute("show_name", student.getUsername());
        }
        else{
            model.addAttribute("show_name", student.getName());
        }
        model.addAttribute("signature",student.getSignature());
        model.addAttribute("studentName",student.getName());
        model.addAttribute("studentId",student.getStudent_id());
        model.addAttribute("email",student.getEmail());
        model.addAttribute("department",student.getDepartment());
        model.addAttribute("information",student.getInformation());

        return "studentDetail";
    }
}
