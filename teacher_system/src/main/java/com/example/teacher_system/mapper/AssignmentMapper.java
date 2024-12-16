package com.example.teacher_system.mapper;

import com.example.teacher_system.entity.Assignment;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface AssignmentMapper {
    @Select("select count(*) from assignment where course_id = #{course_id}")
    int getCourseAssignment(int course_id);

    @Select("select assignment_id from assignment where course_id = #{course_id}")
    List<Integer> getCourseAssignmentIdList(int course_id);

    @Select("select * from assignment where assignment_id = #{assignment_id}")
    Assignment getAssignmentById(int assignment_id);

    @Select("select * from assignment where title = #{title}")
    Assignment getAssignmentByTitle(int title);

    @Select("select * from assignment where course_id = #{course_id}")
    List<Assignment> getCourseAssignmentList(int course_id);

    @Select("select a.assignment_id, a.course_id, a.title, a.description, a.created_time, a.deadline, c.course_name" +
            " from assignment a" +
            " join Course c on a.course_id = c.course_id" +
            " where teacher_id = #{teacher_id}")
    List<Assignment> getAllAssignmentList(int teacher_id);

    @Insert("insert into assignment (course_id, title, description, deadline) values (#{course_id}, #{title}, #{description}, #{deadline})")
    int addAssignment(int course_id, String title, String description, Timestamp deadline);

    @Select("select last_insert_id()")
    int getLastInsertId();

    @Update("update assignment set title = #{title}, description = #{description}, deadline = #{deadline} where assignment_id = #{assignment_id}")
    int updateAssignment(int assignment_id, String title, String description, Timestamp deadline);

    @Delete("delete from assignment where assignment_id = #{assignment_id}")
    int deleteAssignment(int assignment_id);

    @Delete("delete from assignment where course_id = #{course_id}")
    int deleteCourseAssignment(int course_id);
}
