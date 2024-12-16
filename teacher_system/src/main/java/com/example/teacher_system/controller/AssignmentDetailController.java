package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Assignment;
import com.example.teacher_system.entity.Submission;
import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.sevice.AssignmentService;
import com.example.teacher_system.sevice.SubmissionService;
import com.example.teacher_system.sevice.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class AssignmentDetailController {
    @Resource
    UserService userService;
    @Resource
    AssignmentService assignmentService;
    @Resource
    SubmissionService submissionService;

    //已完成页面
    @RequestMapping("/assignmentDetail_finished")
    public String assignmentDetail(HttpSession session, Model model){
        Teacher user= userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        if(session.getAttribute("AssignmentId")==null) return "redirect:/assignment";

        int assignmentId = (int) session.getAttribute("AssignmentId");
        System.out.println(assignmentId);
        List<Submission> submissionList=submissionService.getSubmittedSubmissions(assignmentId);
        Assignment assignment= assignmentService.getAssignmentById(assignmentId);
        System.out.println(submissionList);
        model.addAttribute("title", assignment.getTitle());
        model.addAttribute("assignmentDescription", assignment.getDescription());
        model.addAttribute("assignmentId",assignmentId);
        model.addAttribute("submissionList", submissionList);

        return "assignmentDetail_finished";
    }

    //未完成页面
    @RequestMapping("/assignmentDetail_unfinished")
    public String assignmentDetail_unfinished(HttpSession session, Model model){
        System.out.println("正在进入未完成页面");
        Teacher user= userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        if(session.getAttribute("AssignmentId")==null) return "redirect:/assignment";

        int assignmentId = (int) session.getAttribute("AssignmentId");
        List<Submission> submissionList = submissionService.getUnSubmittedSubmissions(assignmentId);
        System.out.println(assignmentId);
        Assignment assignment= assignmentService.getAssignmentById(assignmentId);
        System.out.println(submissionList);
        model.addAttribute("title", assignment.getTitle());
        model.addAttribute("assignmentDescription", assignment.getDescription());
        model.addAttribute("assignmentId",assignmentId);
        model.addAttribute("submissionList", submissionList);

        return "assignmentDetail_unfinished";
    }

    @ResponseBody
    @PostMapping("/getCorrectInformation")
    public Submission getCorrectInformation(@RequestParam String submissionId) {
        System.out.println("正在获取批改信息");
        int submission_id = Integer.parseInt(submissionId);
        return submissionService.getSubmissionById(submission_id);
    }

    @ResponseBody
    @PostMapping("/editCorrect")
    public String editCorrect(HttpSession session,
                              @RequestParam String submissionId,
                              @RequestParam String score,
                              @RequestParam String feedback) {
        System.out.println("正在编辑批改信息");
        System.out.println(submissionId+" "+score+" "+feedback);

        if(score.isBlank()){
            return "分数不能为空，请重新输入分数";
        }

        int submission_id = Integer.parseInt(submissionId);
        int assignment_score = Integer.parseInt(score);
        if(assignment_score<0||assignment_score>100){
            return "分数应在0-100之间，请重新输入分数";
        }

        int n=submissionService.updateCorrectInformation(submission_id, assignment_score, feedback);
        if(n==0) return "批改失败";
        return "批改成功";
    }

    @ResponseBody
    @GetMapping("/download")
    public void download(HttpServletResponse response,
                         Model model,
                         @RequestParam String submissionId) {
        response.setContentType("multipart/form-data");
        int submission_id = Integer.parseInt(submissionId);

        Submission submission=submissionService.getSubmissionById(submission_id);
        String file_path=submission.getFile_path();
        String fileName = file_path.substring(file_path.lastIndexOf("/") + 1);
        model.addAttribute("file_name",fileName);

        try(OutputStream stream = response.getOutputStream();
            InputStream inputStream = new FileInputStream(file_path)){
            IOUtils.copy(inputStream, stream);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
