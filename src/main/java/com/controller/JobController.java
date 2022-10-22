package com.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.model.Job;
import com.service.JobService;

@RestController
public class JobController {

	@Autowired
	JobService jobService;
	
	@PostMapping("/addjob")
	public ResponseEntity<String> addJob(@RequestBody Job job)
	{
		String response = jobService.addJob(job);
		
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getalljobs")
	public List<Job> getAllJobs()
	{
		return jobService.getAllJobs();		
	}
	
	@PostMapping("/getall")
	public List<Job> getAllJobBySkill(@RequestBody List<String> option){
		
		
		return jobService.getAllJobBySkill(option);
	}
	
	@PatchMapping("/updatejob")
	public ResponseEntity<String> upDateJob(@RequestBody Job job) 
	{
		String response=jobService.upDateJob(job);
		 return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/deletejob")
	public ResponseEntity<String> DeleteJob(@RequestBody Job job) 
	{
		String response= jobService.DeleteJob(job);
		 return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	@GetMapping("findbyjobid/{id}")
	public Job getUser(@PathVariable int id)
	{
		
		return jobService.getUser(id);
		
	}
}
