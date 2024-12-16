package com.example.teacher_system.mapper;

import com.example.teacher_system.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Select("select n.notification_id, n.title,n.message,n.created_date,n.status,s.name,s.username" +
            " from notification n" +
            " join student s on n.student_id=s.student_id" +
            " where teacher_id=#{teacher_id} and poster='student'" +
            " order by n.notification_id desc")
    List<Notification> getNotificationList(int teacher_id);

    @Insert("insert into notification (teacher_id, student_id, poster, title, message) values (#{teacher_id}, #{student_id}, 'teacher', #{title}, #{message})")
    int addNotification(int teacher_id, int student_id, String title, String message);
}
