package com.example.student_system.mapper;

import com.example.student_system.entity.Submission;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

@Mapper
public interface SubmissionMapper {
    @Select("select * from submission where assignment_id=#{assignment_id} and student_id=#{student_id}")
    Submission getSubmission(int assignment_id, int student_id);

    @Select("select * from submission where assignment_id=#{assignment_id} and student_id=#{student_id}")
    Submission getSubmissionById(int assignment_id,int student_id);

    @Select("select count(*) from submission where student_id=#{student_id} and submission_status=0")
    int getUnSubmittedNum(int student_id);

    @Select("select count(*) from submission where student_id=#{student_id} and submission_status=1")
    int getSubmittedNum(int student_id);

    @Select("select count(*) from submission where student_id=#{student_id} and submission_status=1 and correct_status=0")
    int getUnCorrectedNum(int student_id);

    @Select("select count(*) from submission where student_id=#{student_id} and submission_status=1 and correct_status=1")
    int getCorrectedNum(int student_id);

    @Update("update submission set submission_date=#{submission_date},file_path=#{file_path},submission_status=1,file_name=#{file_name} where assignment_id=#{assignment_id} and student_id=#{student_id}")
    int submitAssignment(int assignment_id, int student_id, String file_path, Timestamp submission_date,String file_name);

    @Insert("insert into submission (assignment_id, student_id) values (#{assignment_id}, #{student_id})")
    int insertSubmission(int assignment_id, int student_id);

    @Delete("delete from submission where assignment_id = #{assignment_id} and student_id = #{student_id}")
    int deleteSubmission(int assignment_id,int student_id);
}
