package com.example.teacher_system.sevice;

import com.example.teacher_system.entity.Notification;
import com.example.teacher_system.mapper.NotificationMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Resource
    NotificationMapper notificationMapper;

    public List<Notification> getNotificationList(int teacher_id){
        return notificationMapper.getNotificationList(teacher_id);
    }

    public int addNotification(int teacher_id, int student_id, String title, String message){
        return notificationMapper.addNotification(teacher_id,student_id,title,message);
    }
}
