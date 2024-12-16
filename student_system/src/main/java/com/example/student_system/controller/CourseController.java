package com.example.student_system.controller;

import com.example.student_system.entity.Course;
import com.example.student_system.entity.Student;
import com.example.student_system.service.AssignmentService;
import com.example.student_system.service.CourseService;
import com.example.student_system.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class CourseController {
    @Resource
    UserService userService;
    @Resource
    CourseService courseService;
    @Resource
    AssignmentService assignmentService;

    @RequestMapping("/courseHaveJoined")
    public String courseHaveJoined(Model model){
        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        List<Course> courseList=courseService.getStudentCourseList(user.getStudent_id());
        model.addAttribute("courseList",courseList);

        return "courseHaveJoined";
    }

    @RequestMapping("/courseNotJoined")
    public String courseNotJoined(Model model){
        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        List<Course> courseList=courseService.getStudentCourseListNotEnroll(user.getStudent_id());
        model.addAttribute("courseList",courseList);

        return "courseNotJoined";
    }

    @ResponseBody
    @PostMapping("/getCourse")
    public Course getCourse(@RequestParam String course_id){
        System.out.println("正在获取课程信息"+course_id);
        int courseId=Integer.parseInt(course_id);
        return courseService.getCourseById(courseId);
    }

    @ResponseBody
    @PostMapping("/enrollCourse")
    @Transactional
    public String enrollCourse(@RequestParam String courseId){
        Student user=userService.getUser();
        int course_id=Integer.parseInt(courseId);
        Course course=courseService.getCourseById(course_id);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(now.after(course.getDeadline())) throw new RuntimeException("加入课程失败,不在该课程选课时间段");

        if(assignmentService.isCourseHaveAssignment(course_id)){
            List<Integer> assignmentIdList=assignmentService.getCourseAssignmentIdList(course_id);
            for(Integer assignmentId:assignmentIdList){
                int m=assignmentService.addSubmission(assignmentId,user.getStudent_id());
                if(m==0) throw new RuntimeException("加入课程失败,请稍后重试");
            }
        }

        int n=courseService.enrollCourse(user.getStudent_id(),course_id);
        if(n==0) throw new RuntimeException("加入课程失败,请稍后重试");
        return "加入课程成功";
    }


    @ResponseBody
    @PostMapping("/quitCourse")
    @Transactional
    public String quitCourse(@RequestParam String courseId){
        Student user=userService.getUser();
        int course_id=Integer.parseInt(courseId);
        Course course=courseService.getCourseById(course_id);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(now.after(course.getDeadline())) throw new RuntimeException("退出课程失败,不在该课程选课时间段");

        if(assignmentService.isCourseHaveAssignment(course_id)){
            List<Integer> assignmentIdList=assignmentService.getCourseAssignmentIdList(course_id);
            for(Integer assignmentId:assignmentIdList){
                int m=assignmentService.deleteSubmission(assignmentId,user.getStudent_id());
                if(m==0) throw new RuntimeException("退课失败,请稍后重试");
            }
        }

        int n=courseService.quitCourse(user.getStudent_id(),course_id);
        if(n==0) throw new RuntimeException("退课失败,请稍后重试");
        return "退课成功";
    }

    @ResponseBody
    @PostMapping("/goToCourseDetail")
    public String courseNotJoined(HttpSession session,
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
