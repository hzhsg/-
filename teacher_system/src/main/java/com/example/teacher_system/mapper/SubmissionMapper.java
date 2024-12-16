package com.example.teacher_system.mapper;

import com.example.teacher_system.entity.Submission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubmissionMapper {
    @Select("select * from submission where submission_id = #{submission_id}")
    Submission getSubmissionById(int submission_id);

    @Select("select sub.submission_id, sub.assignment_id, sub.student_id, sub.submission_date, sub.file_path, sub.version, sub.score, sub.feedback, sub.feedback_date, sub.submission_status, sub.correct_status, sub.file_name, stu.name, stu.username" +
            " from submission sub" +
            " join student stu on sub.student_id = stu.student_id" +
            " where sub.assignment_id = #{assignment_id} and sub.submission_status = 0")
    List<Submission> getUnSubmittedSubmissions(int assignment_id);

    @Select("select sub.submission_id, sub.assignment_id, sub.student_id, sub.submission_date, sub.file_path, sub.version, sub.score, sub.feedback, sub.feedback_date, sub.submission_status, sub.correct_status, sub.file_name, stu.name, stu.username" +
            " from submission sub" +
            " join student stu on sub.student_id = stu.student_id" +
            " where sub.assignment_id = #{assignment_id} and sub.submission_status = 1" +
            " order by sub.correct_status asc")
    List<Submission> getSubmittedSubmissions(int assignment_id);

    @Update("update submission set score=#{score},feedback=#{feedback},correct_status=1 where submission_id=#{submission_id}")
    int updateCorrectInformation(int submission_id, int score, String feedback);

    @Insert("insert into submission (assignment_id, student_id) values (#{assignment_id}, #{student_id})")
    int insertSubmission(int assignment_id, int student_id);

    @Delete("delete from submission where assignment_id = #{assignment_id}")
    int deleteSubmissionByAssignmentId(int assignment_id);

    @Delete("delete from submission where assignment_id = #{assignment_id} and student_id = #{student_id}")
    int deleteStudentSubmission(int assignment_id,int student_id);
}
