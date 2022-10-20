package com.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.JobDao;
import com.model.Job;
import com.model.JobSkillSet;

@RestController
public class JobController {

	@Autowired
	JobDao jobdao;
	
	@PostMapping("/addjob")
	public ResponseEntity<String> addJob(@RequestBody Job job)
	{
		List<JobSkillSet> skills = job.getJobSkillSet();
		
		for(JobSkillSet skill : skills) {
			skill.setJob(job);
		}
		
		jobdao.save(job);
		
		return new ResponseEntity<String>("Job added successfully",HttpStatus.OK);
	}
	
	@GetMapping("/getalljobs")
	public List<Job> getAllItems()
	{
		return jobdao.findAll();
		
	}
	
	@PostMapping("/getall")
	public List<Job> getAllJobBySkill(@RequestBody List<String> option){
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
	
	@PatchMapping("/updatejob")
	public ResponseEntity<String> upDateItem(@RequestBody Job job) 
	{
		jobdao.save(job);
		 return new ResponseEntity<String>("Entity updated",HttpStatus.OK);
	}
	
	@DeleteMapping("/deletejob")
	public ResponseEntity<String> DeleteItem(@RequestBody Job job) 
	{
		 jobdao.delete(job);
		 return new ResponseEntity<String>("entity deleted",HttpStatus.OK);
	}
	
	@GetMapping("findbyjobid/{id}")
	public Job getUser(@PathVariable int id)
	{
		Optional<Job> user=jobdao.findById(id);
		return user.get();
		
	}
}
