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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.JobDao;
import com.model.Candidate;
import com.model.Job;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class JobController {
	@Autowired
	JobDao jobDao;
	
	@ApiOperation(value = "getalljobs",notes="getting all jobs",nickname = "getall" )
	@ApiResponses(value= {@ApiResponse(code=200, message="all jobs")})
	@GetMapping("/getalljobs")
	public List<Job> getAllJobs() {
		List<Job> jobs = jobDao.findAll();
		return jobs;
	}
	
	@PostMapping("/addjob")
	public ResponseEntity addjob(@RequestBody Job job) {
		jobDao.save(job);
		return new ResponseEntity("job added successfuly", HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/updatejob")
	public ResponseEntity updateJob(@RequestBody Job job) {
		jobDao.save(job);
		return new ResponseEntity("job updated successfuly", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/deletejob")
	public ResponseEntity deleteJob(@RequestBody Job job) {
		jobDao.delete(job);
		return new ResponseEntity("job deleted successfully", HttpStatus.FOUND);
	}
	
}
