package com.example.student_system.controller;

import com.example.student_system.entity.*;
import com.example.student_system.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class AssignmentController {
    @Resource
    AssignmentService assignmentService;
    @Resource
    UserService userService;
    @Resource
    SubmissionService submissionService;
    @Resource
    CourseService courseService;
    @Resource
    TeacherService teacherService;
    @Resource
    NotificationService notificationService;

    @RequestMapping("/assignment")
    public String assignment(Model model){
        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        List<Assignment> assignmentList=assignmentService.getDoneAssignmentList(user.getStudent_id());
        model.addAttribute("assignmentList",assignmentList);

        return "assignment";
    }

    @RequestMapping("/assignmentUnDo")
    public String assignmentUnDo(Model model){
        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        List<Assignment> assignmentList=assignmentService.getUnDoAssignmentList(user.getStudent_id());
        model.addAttribute("assignmentList",assignmentList);

        return "assignmentUnDo";
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
    @PostMapping("/submitAssignment")
    @Transactional
    public String submitAssignment(@RequestParam String assignmentId,
                                   @RequestParam MultipartFile file,
                                   @RequestParam String fileType) {
        Student user=userService.getUser();
        int assignment_id=Integer.parseInt(assignmentId);
        Assignment assignment=assignmentService.getAssignmentById(assignment_id);
        Submission submission=submissionService.getSubmission(assignment_id, user.getStudent_id());
        Timestamp now=new Timestamp(System.currentTimeMillis());
        Course course=courseService.getCourseById(assignment.getCourse_id());

        if(now.after(assignment.getDeadline())){
            return "已过截止日期";
        }

        if (file.isEmpty()) {
            return "文件不能为空";
        }
        System.out.println("assignment"+submission.getSubmission_id()+fileType);

        try {
            String directoryPath = "E:/IDEA/assignment_management/student_system/src/main/resources/repository/";
            String pathName = directoryPath + "assignment" + submission.getSubmission_id() + fileType;
            File fileObj = new File(pathName);
            file.transferTo(fileObj);

            String student_id=String.valueOf(user.getStudent_id());
            String file_name;
            int m;
            if(user.getName()!=null){
                file_name=student_id+"_"+user.getName()+"_"+assignment.getTitle()+fileType;
                m=notificationService.addNotification(course.getTeacher_id(), user.getStudent_id(), "作业提交", student_id+"_"+user.getName()+"提交了"+course.getCourse_name()+"的"+assignment.getTitle()+"作业");
            }
            else{
                file_name=student_id+"_"+user.getUsername()+"_"+assignment.getTitle()+fileType;
                m=notificationService.addNotification(course.getTeacher_id(), user.getStudent_id(), "作业提交", student_id+"_"+user.getUsername()+"提交了"+course.getCourse_name()+"的"+assignment.getTitle()+"作业");
            }
            int n=assignmentService.submitAssignment(assignment_id, user.getStudent_id(), pathName, now, file_name);
            if(n==0) throw new RuntimeException("作业上传失败");
            if(m==0) throw new RuntimeException("发送通知失败");

        }catch (IOException e) {
            return "作业上传失败";
        }

        return "作业上传成功";
    }

    @ResponseBody
    @PostMapping("/getCorrectInformation")
    public Submission getCorrectInformation(@RequestParam String assignmentId) {
        Student user=userService.getUser();
        int assignment_id=Integer.parseInt(assignmentId);
        System.out.println("正在获取批改信息");
        System.out.println(submissionService.getSubmissionById(assignment_id, user.getStudent_id()));
        return submissionService.getSubmissionById(assignment_id, user.getStudent_id());
    }
}
