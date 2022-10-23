package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.EmployerDAO;
import com.dao.JobDAO;
import com.dto.NewJobDTO;
import com.model.Employer;
import com.model.Job;

import io.swagger.annotations.ApiOperation;

@RestController
public class JobController {
	
	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	EmployerDAO employerDAO;
	
	@GetMapping("/hello")
	public String hello() {
	  return "Hello world";
	}
	
	@ApiOperation(value = "add a job", notes = "Adding a new job", nickname = "add-job")
	@PostMapping("/addJob")
	public ResponseEntity<String> addJob(@RequestBody NewJobDTO jobDTO) {
		
		List<Employer> employerList = employerDAO.findAll();
		System.out.println(employerList);
		Job job = new Job();
		job.setJobDescription(jobDTO.getJobDescription());
		job.setIndustry(jobDTO.getIndustry());
		job.setJobStatus(jobDTO.getJobStatus());
		Employer first = employerList.get(0);
		job.setCreatedBy(first);
		jobDAO.save(job);
		
		first.getJobs().addAll(Arrays.asList(job));
		employerDAO.save(first);
		return new ResponseEntity<>("Job saved successfully", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getAllJobs")
	public ResponseEntity<List<Job>> getAllJobs() {
		return new ResponseEntity<>(jobDAO.findAll(), HttpStatus.OK);
	}
	
	
	@GetMapping("/getJobsByIndustry/{industry}")
	public ResponseEntity<List<Job>> getJobsByIndustry(@PathVariable String industry) {
		List<Job> jobList = jobDAO.findByIndustry(industry);
		return new ResponseEntity<>(jobList, HttpStatus.OK);
	}
	
	@GetMapping("/getJobsByLocation/{location}")
    public ResponseEntity<List<Job>> getJobsByLocation(@PathVariable String location) {
	    List<Employer> employerList = employerDAO.findByLocation(location);
	    List<Job> jobList = new ArrayList<>();
	    
	    for (Employer em: employerList) {
	      jobList.addAll(em.getJobs());
	    }
	    
	    return new ResponseEntity<>(jobList, HttpStatus.OK);
    }
	
}
