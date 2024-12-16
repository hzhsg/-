package com.example.student_system.controller;

import com.example.student_system.entity.Course;
import com.example.student_system.entity.Student;
import com.example.student_system.entity.Teacher;
import com.example.student_system.service.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CourseDetailController {
    @Resource
    UserService userService;
    @Resource
    CourseService courseService;
    @Resource
    AssignmentService assignmentService;
    @Resource
    TeacherService teacherService;
    @Resource
    NotificationService notificationService;

    @RequestMapping("/course_detail")
    public String courseDetail(Model model, HttpSession session){
        System.out.println(session.getAttribute("CourseId"));
        if(session.getAttribute("CourseId")==null) return "redirect:/courseHaveJoined";

        int courseId=(int) session.getAttribute("CourseId");
        Course course=courseService.getCourseById(courseId);
        int teacher_id=course.getTeacher_id();
        Teacher teacher=teacherService.getTeacherById(teacher_id);

        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        if(teacher.getName()==null) {
            model.addAttribute("teacher_name", teacher.getUsername());
        }
        else{
            model.addAttribute("teacher_name",teacher.getName());
        }
        model.addAttribute("course_id",course.getCourse_id());
        model.addAttribute("courseName",course.getCourse_name());
        model.addAttribute("courseDescription",course.getDescription());
        model.addAttribute("assignment_list",assignmentService.getCourseAssignmentList(courseId, user.getStudent_id()));

        System.out.println(courseId);
        return "course_detail";
    }
}
