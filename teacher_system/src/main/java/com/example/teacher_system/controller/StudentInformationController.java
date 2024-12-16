package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Course;
import com.example.teacher_system.entity.Student;
import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.sevice.*;
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
    @Resource
    AssignmentService assignmentService;
    @Resource
    NotificationService notificationService;

    @RequestMapping("/student_information")
    public String studentInformation(HttpSession session, Model model){
        System.out.println(session.getAttribute("CourseId"));
        if(session.getAttribute("CourseId")==null) return "redirect:/course";

        int courseId=(int) session.getAttribute("CourseId");
        Course course=courseService.getCourseById(courseId);
        List<Student> studentList=studentService.getStudentListWithEnrollmentDate(courseId);
        System.out.println(studentList);

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
        model.addAttribute("studentList",studentList);

        return "student_information";
    }

    @ResponseBody
    @PostMapping("/addStudent")
    @Transactional
    public String addStudent(@RequestParam String student_id, HttpSession session){
        Teacher user=userService.getUser();
        int courseId=(int) session.getAttribute("CourseId");
        int studentId=Integer.parseInt(student_id);
        Course course=courseService.getCourseById(courseId);

        //判断学生是否存在
        if(!studentService.haveStudent(studentId)) return "该学生不存在";
        //判断学生是否已经存在课程
        if(studentService.CourseHaveStudent(courseId,studentId)) return "学生已存在课程中";

        if(assignmentService.courseHaveAssignment(courseId)){
            List<Integer> assignmentIdList=assignmentService.getCourseAssignmentIdList(courseId);
            for(Integer assignmentId:assignmentIdList){
                int m=assignmentService.addSubmission(assignmentId,studentId);
                if(m==0) throw new RuntimeException("添加学生失败");
            }
        }
        int n1 = notificationService.addNotification(user.getTeacher_id(), studentId, "进入课程",
                "老师已将你添加进"+course.getCourse_name()+"课程");
        if (n1 == 0) throw new RuntimeException("添加学生失败");

        int n=courseService.addStudent(studentId,courseId);
        if(n==0) throw new RuntimeException("添加学生失败");
        return "添加学生成功";
    }

    @ResponseBody
    @PostMapping("/deleteStudent")
    public String deleteStudent(@RequestParam String studentId, HttpSession session){
        if(studentId.isBlank()) return "获取学生id失败，请稍后重试";
        Teacher user=userService.getUser();
        int courseId=(int) session.getAttribute("CourseId");
        int student_id=Integer.parseInt(studentId);
        Course course=courseService.getCourseById(courseId);

        if(assignmentService.courseHaveAssignment(courseId)){
            List<Integer> assignmentIdList=assignmentService.getCourseAssignmentIdList(courseId);
            for(Integer assignmentId:assignmentIdList){
                int m=assignmentService.deleteStudentSubmission(assignmentId,student_id);
                if(m==0) throw new RuntimeException("删除学生作业失败");
            }
        }
        int n1 = notificationService.addNotification(user.getTeacher_id(), student_id, "退出课程",
                "老师已将你踢出"+course.getCourse_name()+"课程");
        if (n1 == 0) throw new RuntimeException("删除学生通知失败");

        int n=studentService.deleteStudent(student_id,courseId);
        if(n==0) throw new RuntimeException("删除学生失败");
        return "已删除学生";
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
