package com.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.JobDao;
import com.model.Job;
import com.model.JobSkillSet;


@Service
public class JobService {

		@Autowired
		JobDao jobdao;
		
		
		public String addJob( Job job)
		{
			List<JobSkillSet> skills = job.getJobSkillSet();
			
			for(JobSkillSet skill : skills) {
				skill.setJob(job);
			}
			
			jobdao.save(job);
			
			return "Job added successfully";
		}
		
		
		public List<Job> getAllJobs()
		{
			return jobdao.findAll();
			
		}
		
		
		public List<Job> getAllJobBySkill( List<String> option){
			List<Job> allJobs = new LinkedList<>();

			for(String category : option) {
				//System.out.println(category);
				List<Job> temp = jobdao.findByJobSkillSetSkillName(category);
				allJobs.addAll(temp);
			}
			
			//Removing Duplicates
			Set<Integer> ids = new HashSet<>();
			List<Job> resultant = new LinkedList<>();
			for(Job j : allJobs) {
				if(!ids.contains(j.getJobId())){
					resultant.add(j);
				}
				ids.add(j.getJobId());
			}
			
			return resultant;
		}
		
	
		public String upDateJob( Job job) 
		{
			jobdao.save(job);
			 return "Entity updated";
		}
		
		
		public String DeleteJob( Job job) 
		{
			 jobdao.delete(job);
			 return "entity deleted";
		}
		
		
		public Job getUser( int id)
		{
			Optional<Job> user=jobdao.findById(id);
			return user.get();
			
		}
	}


