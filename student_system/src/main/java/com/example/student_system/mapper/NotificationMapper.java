package com.example.student_system.mapper;

import com.example.student_system.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Select("select n.notification_id, n.title,n.message,n.created_date,n.status,t.name,t.username" +
            " from notification n" +
            " join teacher t on n.teacher_id=t.teacher_id" +
            " where student_id=#{student_id} and poster='teacher'" +
            " order by n.notification_id desc")
    List<Notification> getNotificationList(int student_id);

    @Insert("insert into notification (teacher_id, student_id, poster, title, message) values (#{teacher_id}, #{student_id}, 'student', #{title}, #{message})")
    int addNotification(int teacher_id, int student_id, String title, String message);
}
