package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.JobDAO;
import com.enums.JobPostStatus;
import com.exceptions.NoSuchJobFoundException;
import com.model.Job;

@Service
public class JobService {
  
  @Autowired
  JobDAO jobDAO;
  
  public void closeJob(Job j) {
    j.setJobPostStatus(JobPostStatus.CLOSED);
    jobDAO.save(j);
  }
  
  public Job getJobById(int id) throws NoSuchJobFoundException {
    try {      
      Job j = jobDAO.getById(id);
      return j;
    } catch (Exception e) {
      throw new NoSuchJobFoundException(id);
    }
  }
}
