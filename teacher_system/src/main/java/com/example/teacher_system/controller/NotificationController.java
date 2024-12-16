package com.example.teacher_system.controller;

import com.example.teacher_system.entity.Notification;
import com.example.teacher_system.entity.Teacher;
import com.example.teacher_system.sevice.NotificationService;
import com.example.teacher_system.sevice.UserService;
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
        Teacher user=userService.getUser();
        if(user.getName()==null) {
            model.addAttribute("name", user.getUsername());
        }
        else{
            model.addAttribute("name",user.getName());
        }

        List<Notification> notificationList=notificationService.getNotificationList(user.getTeacher_id());
        model.addAttribute("notificationList",notificationList);

        return "notification";
    }
}
