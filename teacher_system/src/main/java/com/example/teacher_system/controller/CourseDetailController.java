package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Assignment;
import com.example.teacher_system.entity.Course;
import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.sevice.*;
import com.example.teacher_system.util.Util;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class CourseDetailController {
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

    @RequestMapping("/course_detail")
    public String courseDetail(Model model,HttpSession session){
        System.out.println(session.getAttribute("CourseId"));
        if(session.getAttribute("CourseId")==null) return "redirect:/course";

        int courseId=(int) session.getAttribute("CourseId");
        Course course=courseService.getCourseById(courseId);

        Teacher user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        model.addAttribute("course_id",course.getCourse_id());
        model.addAttribute("courseName",course.getCourse_name());
        model.addAttribute("courseDescription",course.getDescription());
        model.addAttribute("assignment_list",assignmentService.getCourseAssignmentList(courseId));
        System.out.println(courseId);
        return "course_detail";
    }

    @ResponseBody
    @PostMapping("/addAssignment")
    @Transactional
    public String addAssignment(HttpSession session,
                                @RequestParam String assignmentName,
                                @RequestParam String assignmentDescription,
                                @RequestParam String assignmentDeadline){
        System.out.println("正在添加作业");
        int courseId=(int) session.getAttribute("CourseId");
        System.out.println(courseId);
        Teacher user=userService.getUser();

        if (StringUtils.isBlank(assignmentName)) {
            return "课程名称不能为空";
        }
        else if (StringUtils.isBlank(assignmentDeadline)) {
            return "课程截止时间不能为空";
        }

        Timestamp deadline= Util.StringToTimestamp(assignmentDeadline);
        int n=assignmentService.addAssignment(courseId,assignmentName,assignmentDescription,deadline);
        int assignmentId=assignmentService.getAssignmentInsertId();
        if(n==0) throw new RuntimeException("添加作业失败");
        Assignment assignment=assignmentService.getAssignmentById(assignmentId);
        Course course=courseService.getCourseById(courseId);

        if(courseService.getStudentNum(courseId)!=0) {
            List<Integer> studentIdList = studentService.getStudentIdList(courseId);
            for (Integer studentId : studentIdList) {
                System.out.println(studentId);
                int m = assignmentService.addSubmission(assignmentId, studentId);
                if (m == 0) throw new RuntimeException("添加作业失败");
                int n1 = notificationService.addNotification(user.getTeacher_id(), studentId, "教师添加作业",
                        "老师在"+course.getCourse_name()+"课程布置了"+assignment.getTitle()+"作业");
                if (n1 == 0) throw new RuntimeException("添加作业失败");
            }
        }

        return "添加作业成功";
    }

    @ResponseBody
    @PostMapping("/getAssignment")
    public Assignment getAssignment(@RequestParam String assignmentId){
        System.out.println("正在获取作业信息");
        System.out.println(assignmentId);
        int assignment_id=Integer.parseInt(assignmentId);
        return assignmentService.getAssignmentById(assignment_id);
    }

    @ResponseBody
    @PostMapping("/editAssignment")
    @Transactional
    public String editAssignment(@RequestParam String assignmentId,
                                 @RequestParam String assignmentName,
                                 @RequestParam String assignmentDescription,
                                 @RequestParam String assignmentDeadline) {
        System.out.println("正在编辑作业");
        Teacher user=userService.getUser();
        if (StringUtils.isBlank(assignmentName)) {
            return "课程名称不能为空";
        }
        else if (StringUtils.isBlank(assignmentDeadline)) {
            return "课程截止时间不能为空";
        }

        Timestamp deadline=Util.StringToTimestamp(assignmentDeadline);;
        int assignment_id=Integer.parseInt(assignmentId);
        Assignment assignment=assignmentService.getAssignmentById(assignment_id);
        int courseId=assignment.getCourse_id();
        Course course=courseService.getCourseById(courseId);

        int n=assignmentService.editAssignment(assignment_id,assignmentName,assignmentDescription,deadline);
        if(n==0) throw new RuntimeException("编辑课程失败");

        if(courseService.getStudentNum(courseId)!=0) {
            List<Integer> studentIdList = studentService.getStudentIdList(courseId);
            //添加通知
            for (Integer studentId : studentIdList) {
                int n1 = notificationService.addNotification(user.getTeacher_id(), studentId, "教师编辑作业",
                        "老师编辑了"+course.getCourse_name()+"课程"+assignment.getTitle()+"作业的内容");
                if (n1 == 0) throw new RuntimeException("删除作业失败");
            }
        }

        return "编辑作业成功";
    }

    @ResponseBody
    @PostMapping("/deleteAssignment")
    @Transactional
    public String deleteAssignment(HttpSession session,@RequestParam String assignmentId){
        System.out.println("正在删除作业");
        Teacher user=userService.getUser();
        int assignment_id=Integer.parseInt(assignmentId);
        int courseId=(int) session.getAttribute("CourseId");
        Assignment assignment=assignmentService.getAssignmentById(assignment_id);
        Course course=courseService.getCourseById(courseId);

        int n=assignmentService.deleteAssignment(assignment_id);
        if(n==0) return "删除作业失败";

        if(courseService.getStudentNum(courseId)!=0) {
            int student_num=courseService.getStudentNum(courseId);
            List<Integer> studentIdList = studentService.getStudentIdList(courseId);
            //删除submission
            int m = assignmentService.deleteSubmission(assignment_id);
            if (m != student_num) throw new RuntimeException("删除作业失败");
            //添加通知
            for (Integer studentId : studentIdList) {
                int n1 = notificationService.addNotification(user.getTeacher_id(), studentId, "教师删除作业",
                        "老师删除了"+course.getCourse_name()+"课程的"+assignment.getTitle()+"作业");
                if (n1 == 0) throw new RuntimeException("删除作业失败");
            }
        }

        return "已删除作业";
    }

    @ResponseBody
    @PostMapping("/goToAssignmentDetail")
    public String goToAssignmentDetail(HttpSession session,
                                       @RequestParam String assignmentId){
        if(assignmentId.isBlank()) return "fail";
        if(session.getAttribute("AssignmentId")!=null) {
            session.removeAttribute("AssignmentId");
        }
        int AssignmentId=Integer.parseInt(assignmentId);
        session.setAttribute("AssignmentId",AssignmentId);
        if(session.getAttribute("AssignmentId")==null) return "fail";
        return "success";
    }
}
