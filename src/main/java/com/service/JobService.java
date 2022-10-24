package com.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.JobDAO;
import com.enums.JobStatus;
import com.exception.NoSuchJobFoundException;
import com.model.Job;
@Service
public class JobService {
  
  @Autowired
  JobDAO jobDAO;
  
	



  public void closeJob(Job j) {
    j.setJobStatus(JobStatus.CLOSED);
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
  
  public Job findJobById(int id) throws NoSuchJobFoundException {	    
      Job j = jobDAO.findById(id).get();
      if (j==null) {
    	  throw new NoSuchJobFoundException(id);
      }
      return j;
  }
  
  public List<Job> getJobBySkillName(String skillNames){
	  List<Job> result = new ArrayList<>();
  	
  		List<Job> jobs= jobDAO.findAll();
  		
		String [] skillsRequired = skillNames.split(",");
		
		System.out.println(Arrays.toString(skillsRequired));
		System.out.println(skillNames);
		
		for(Job j: jobs) {
			
			for(String skill : skillsRequired) {
				
				if( j.getJobDescription().contains(skill)) {
					
					result.add(j);
					break;
					
				}
				
			}
			
		}
		
		return result;
  	}

}
