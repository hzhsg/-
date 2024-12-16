package com.example.student_system.controller;

import com.example.student_system.entity.Notification;
import com.example.student_system.entity.Student;
import com.example.student_system.service.NotificationService;
import com.example.student_system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class NotificationController {
    @Resource
    UserService userService;
    @Resource
    NotificationService notificationService;

    @RequestMapping("/notification")
    public String notification(Model model){
        Student user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }
        List<Notification> notificationList=notificationService.getNotificationList(user.getStudent_id());
        model.addAttribute("notificationList",notificationList);

        return "notification";
    }
}
