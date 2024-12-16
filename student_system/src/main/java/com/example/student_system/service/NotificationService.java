package com.example.student_system.service;

import com.example.student_system.entity.Notification;
import com.example.student_system.mapper.NotificationMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Resource
    NotificationMapper notificationMapper;

    public List<Notification> getNotificationList(int student_id){
        return notificationMapper.getNotificationList(student_id);
    }

    public int addNotification(int teacher_id, int student_id, String title, String message){
        return notificationMapper.addNotification(teacher_id,student_id,title,message);
    }
}
