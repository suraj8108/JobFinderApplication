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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.EmployerDAO;
import com.dao.JobDAO;
import com.dto.NewJobDTO;
import com.dto.NewJobDTO2;
import com.enums.JobPostStatus;
import com.exceptions.NoEmployersException;
import com.model.Employer;
import com.model.Job;
import com.service.EmployerService;

import io.swagger.annotations.ApiOperation;

@RestController
public class JobController {
	
	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	EmployerDAO employerDAO;
	
	@Autowired
	EmployerService employerService;
	
	@GetMapping("/hello")
	public String hello() {
	  return "Hello world";
	}
	
	@ApiOperation(value = "add a job", notes = "Adding a new job", nickname = "add-job")
	@PostMapping("/addJob")
	public ResponseEntity<String> addJob(@RequestBody NewJobDTO jobDTO) {
		
		try {
		  List<Employer> employerList = employerService.findAllEmployers();
	      Job job = new Job(jobDTO.getJobDescription(), jobDTO.getIndustry());
//	      job.setJobPostStatus(JobPostStatus.OPEN);
	      Employer first = employerList.get(0);
	      job.setCreatedBy(first);
	      jobDAO.save(job);
	        
	      first.getJobs().addAll(Arrays.asList(job));
	      employerDAO.save(first);
	      return new ResponseEntity<>("Job added successfully", HttpStatus.ACCEPTED);
		} catch (NoEmployersException e) {
		  return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiOperation(value = "add a job", notes = "Adding a new job", nickname = "add-job")
    @PostMapping("/addJobManually")
    public ResponseEntity<String> addJobManually(@RequestBody NewJobDTO2 jobDTO) {
        Employer e = employerDAO.getById(jobDTO.getEid());
//        List<Employer> employerList = employerDAO.findAll();
        Job job = new Job(jobDTO.getJobDescription(), jobDTO.getIndustry());
//        job.setJobPostStatus(JobPostStatus.OPEN);
//        Employer first = employerList.get(0);
        job.setCreatedBy(e);
        jobDAO.save(job);
        
        e.getJobs().addAll(Arrays.asList(job));
        employerDAO.save(e);
        return new ResponseEntity<>("Job added successfully", HttpStatus.ACCEPTED);
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
