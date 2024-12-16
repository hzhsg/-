package com.example.student_system.mapper;

import com.example.student_system.entity.Assignment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssignmentMapper {
    @Select("select count(*) from assignment where course_id = #{course_id}")
    int getCourseAssignment(int course_id);

    @Select("select * from assignment where assignment_id=#{assignment_id}")
    Assignment getAssignmentById(int assignment_id);

    @Select("select assignment_id from assignment where course_id = #{course_id}")
    List<Integer> getCourseAssignmentIdList(int course_id);

    @Select("select a.assignment_id, a.course_id, a.title, a.description, a.created_time, a.deadline, s.submission_status, s.correct_status" +
            " from assignment a" +
            " join submission s on a.assignment_id=s.assignment_id" +
            " where course_id = #{course_id} and s.student_id=#{student_id}" +
            " order by s.submission_status asc")
    List<Assignment> getCourseAssignmentList(int course_id,int student_id);

    @Select("select a.assignment_id, a.course_id, a.title, a.description, a.created_time, a.deadline, s.submission_status, s.correct_status,c.course_name" +
            " from assignment a" +
            " join submission s on a.assignment_id=s.assignment_id" +
            " join course c on a.course_id=c.course_id" +
            " where s.student_id=#{student_id} and s.submission_status=1" +
            " order by s.correct_status desc")
    List<Assignment> getDoneAssignmentList(int studentId);

    @Select("select a.assignment_id, a.course_id, a.title, a.description, a.created_time, a.deadline, s.submission_status, s.correct_status, c.course_name" +
            " from assignment a" +
            " join submission s on a.assignment_id=s.assignment_id" +
            " join course c on a.course_id=c.course_id" +
            " where s.student_id=#{student_id} and s.submission_status=0")
    List<Assignment> getUnDoAssignmentList(int studentId);
}
