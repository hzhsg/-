package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Course;
import com.example.teacher_system.entity.Student;
import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.mapper.CourseMapper;
import com.example.teacher_system.mapper.TeacherMapper;
import com.example.teacher_system.sevice.*;
import com.example.teacher_system.util.Util;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class CourseController {
    @Resource
    UserService userService;
    @Resource
    CourseService courseService;
    @Resource
    AssignmentService assignmentService;
    @Resource
    StudentService studentService;
    @Resource
    NotificationService notificationService;

    @RequestMapping("/course")
    public String course(Model model){
        Teacher user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        model.addAttribute("course_list",courseService.getCoursesByTid(user.getTeacher_id()));
        return "course";
    }

    @ResponseBody
    @PostMapping("/addCourse")
    public String addCourse(@RequestParam String courseName,
                            @RequestParam String courseDescription,
                            @RequestParam String courseDeadline){
        System.out.println("正在加入课程");
        Teacher user=userService.getUser();

        if (StringUtils.isBlank(courseName)) {
            return "课程名称不能为空";
        }
        else if (StringUtils.isBlank(courseDeadline)) {
            return "课程截止时间不能为空";
        }

        Timestamp deadline=Util.StringToTimestamp(courseDeadline);;

        int n=courseService.addCourse(user.getTeacher_id(),courseName,courseDescription,deadline);
        if(n==0) return "添加课程失败";
        return "添加课程成功";
    }

    @ResponseBody
    @PostMapping("/getCourse")
    public Course getCourse(@RequestParam String course_id){
        System.out.println("正在获取课程信息");
        int courseId=Integer.parseInt(course_id);
        return courseService.getCourseById(courseId);
    }

    @ResponseBody
    @PostMapping("/editCourse")
    public String editCourse(@RequestParam String courseId,
                             @RequestParam String courseName,
                             @RequestParam String courseDescription,
                             @RequestParam String courseDeadline){
        if (StringUtils.isBlank(courseName)) {
            return "课程名称不能为空";
        }
        else if (StringUtils.isBlank(courseDeadline)) {
            return "课程截止时间不能为空";
        }

        Timestamp deadline=Util.StringToTimestamp(courseDeadline);;

        int course_id=Integer.parseInt(courseId);

        int n=courseService.editCourse(course_id,courseName,courseDescription,deadline);
        if(n==0) return "编辑课程失败";
        return "编辑课程成功";
    }

    @ResponseBody
    @PostMapping("/deleteCourse")
    @Transactional
    public String deleteCourse(@RequestParam String courseId){
        Teacher user=userService.getUser();
        System.out.println(courseId);
        int course_id=Integer.parseInt(courseId);
        int student_num=courseService.getStudentNum(course_id);
        int assignment_num= assignmentService.getAssignmentNum(course_id);
        Course course=courseService.getCourseById(course_id);

        if(student_num!=0){
            List<Integer> studentIdList=studentService.getStudentIdList(course_id);
            if(assignment_num!=0) {
                List<Integer> assignmentIdList=assignmentService.getCourseAssignmentIdList(course_id);
                for (int student_id : studentIdList) {
                    for (int assignment_id : assignmentIdList) {
                        int m=assignmentService.deleteStudentSubmission(assignment_id,student_id);
                        if(m==0) throw new RuntimeException("删除课程作业失败");
                    }
                }
                int t=courseService.deleteCourseAssignment(course_id);
                if(t<assignment_num) throw new RuntimeException("删除课程作业失败");
            }

            for(int student_id : studentIdList){
                if(user.getName()!=null) {
                    int f = notificationService.addNotification(user.getTeacher_id(), student_id, "课程删除", user.getName()+"老师删除了"+course.getCourse_name()+"课程");
                    if (f == 0) throw new RuntimeException("添加通知失败");
                }
            }

            int s=courseService.deleteCourseStudent(course_id);
            if(s<student_num) throw new RuntimeException("删除课程学生失败");
        }
        else if(assignment_num!=0){
            int t=courseService.deleteCourseAssignment(course_id);
            if(t<assignment_num) throw new RuntimeException("删除课程作业失败");
        }

        int n=courseService.deleteCourse(course_id);
        if(n==0) throw new RuntimeException("删除课程失败");
        return "已删除课程";
    }

    @ResponseBody
    @PostMapping("/goToCourseDetail")
    public String goToCourseDetail(HttpSession session,
                                   @RequestParam String courseId){
        if(courseId.isBlank()) return "fail";
        if(session.getAttribute("CourseId")!=null) {
            session.removeAttribute("CourseId");
        }
        int CourseId=Integer.parseInt(courseId);
        session.setAttribute("CourseId",CourseId);
        if(session.getAttribute("CourseId")==null) return "fail";
        return "success";
    }
}
