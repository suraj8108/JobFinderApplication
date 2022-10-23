package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enums.InterviewStatus;
import com.model.Employer;
import com.model.Interview;
import com.model.Job;

public interface InterviewDAO extends JpaRepository<Interview, Integer> {
  Interview findByCandidateAndEmployerAndJob(String candidate, Employer employer, Job job);
  List<Interview> findByJobAndInterviewStatus(Job job, InterviewStatus status);
  List<Interview> findByJobAndInterviewStatusIsNot(Job job, InterviewStatus status);
  List<Interview> findByJobAndInterviewIdIsNot(Job job, int id);
  List<Interview> findByJob(Job job);
}
