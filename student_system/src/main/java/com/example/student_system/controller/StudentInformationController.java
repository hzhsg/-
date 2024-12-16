package com.example.student_system.controller;

import com.example.student_system.entity.Course;
import com.example.student_system.entity.Student;
import com.example.student_system.service.CourseService;
import com.example.student_system.service.StudentService;
import com.example.student_system.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StudentInformationController {
    @Resource
    StudentService studentService;
    @Resource
    CourseService courseService;
    @Resource
    UserService userService;

    @RequestMapping("/student_information")
    public String studentInformation(HttpSession session, Model model){
        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }

        System.out.println(session.getAttribute("CourseId"));
        if(session.getAttribute("CourseId")==null) return "redirect:/courseHaveJoined";

        int courseId=(int) session.getAttribute("CourseId");
        Course course=courseService.getCourseById(courseId);
        List<Student> studentList=studentService.getStudentListWithEnrollmentDate(courseId);
        System.out.println(studentList);

        model.addAttribute("course_id",course.getCourse_id());
        model.addAttribute("courseName",course.getCourse_name());
        model.addAttribute("courseDescription",course.getDescription());
        model.addAttribute("studentList",studentList);

        return "student_information";
    }

    @ResponseBody
    @PostMapping("/viewStudent")
    public String viewStudent(@RequestParam String studentId, HttpSession session){
        if(studentId.isBlank()) return "fail";
        if(session.getAttribute("StudentId")!=null) {
            session.removeAttribute("StudentId");
        }
        int StudentId=Integer.parseInt(studentId);
        session.setAttribute("StudentId",StudentId);
        if(session.getAttribute("StudentId")==null) return "fail";
        return "success";
    }
}
