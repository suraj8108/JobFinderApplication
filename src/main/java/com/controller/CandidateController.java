package com.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.validation.ValidationException;

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

import com.dao.CandidateDao;
import com.dto.CandiadateRatingFeedbackDTO;
import com.exception.CandidateNotFoundException;
import com.exception.ValidationExceptioncheck;
import com.model.Candidate;
import com.model.Interview;
import com.model.Project;
import com.model.Skill;
import com.service.CandidateService;
import com.service.InterviewService;
import com.model.Job;


@RestController
public class CandidateController {
	
	@Autowired
	CandidateService candidateService;
	@Autowired
	InterviewService interviewService;
	
	
	@GetMapping("/getallcandidates")
	public List<Candidate> getallCandidates() throws ValidationExceptioncheck {
		try {
		return candidateService.getAllCandidates();
		}
		catch(ValidationException v) {
			throw new ValidationExceptioncheck("Validation error");
		}
		
	}
	@GetMapping("/findcandidatebyid/{id}")
	public ResponseEntity getbyid(@PathVariable int  id  ) throws CandidateNotFoundException{
		if(id!=0 && id>0)
		{ 
			try {
		return new ResponseEntity<>(candidateService.findById(id),HttpStatus.FOUND);
			}
			catch (NoSuchElementException c) {
				throw new CandidateNotFoundException("not found");
			
			}
		}
		else {
			return new	ResponseEntity<>("Candidate could not find , check id "
					,HttpStatus.FORBIDDEN);
		}
		
	}
	
	@PostMapping("/addprofile")
	public ResponseEntity addCandidate(@RequestBody Candidate cand)throws ValidationExceptioncheck {
		if(cand!=null)
		{ 
		try {
		candidateService.addCandidate(cand);
		return new	ResponseEntity<>("Candidate added successfully"
				,HttpStatus.ACCEPTED);
		}
		catch(ValidationException v) {
			throw new ValidationExceptioncheck("validation error");
		}
		}
		else {
			return new	ResponseEntity<>("Candidate adding failed "
					,HttpStatus.FORBIDDEN);
		}
		
		
	}
	@PostMapping("/updatelocationbyid/{id}")
	public ResponseEntity updateLocationById(@RequestBody String str,@PathVariable int id)throws ValidationExceptioncheck {
		try {
		candidateService.updateLocation(id, str);
		}
		catch(ValidationException v) {
			throw new ValidationExceptioncheck("validation error");
		}
		
		return new	ResponseEntity<>("Candidate updated location ",HttpStatus.OK);


	}
	@PatchMapping("/updatecandidate")
	public ResponseEntity updateCandidate(@RequestBody Candidate cand) throws ValidationExceptioncheck {
		if(cand!=null)
		{ 
			try {
		candidateService.updateCandidate(cand);
			}
			catch(ValidationException v) {
				throw new ValidationExceptioncheck("validation error");
			}

		return new	ResponseEntity<>("Candidate updated successfully"
				,HttpStatus.ACCEPTED);
		}
		else {
			return new	ResponseEntity<>("Candidate updation failed, check id "
					,HttpStatus.FORBIDDEN);
		}

		
		
	}
	@PatchMapping("/addskillbyid/{id}")
	public ResponseEntity addskillbyid(@RequestBody Skill cs,@PathVariable int id) {

	candidateService.addSkillById(id, cs);
	
	return new	ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
	}
	

	@PostMapping("/feedbackRating/{interviewId}")
	public ResponseEntity<String> feedbackRating(@PathVariable("interviewId") String id, @RequestBody CandiadateRatingFeedbackDTO dto) {
	 
	    Interview i = interviewService.getInterviewById(Integer.parseInt(id));
	      interviewService.provideCandidateFeedback(i, dto);
	      
	      return new ResponseEntity<>("Feedback and rating by employer saved", HttpStatus.OK);
	  
	}

	
	@GetMapping("/getjobstatus")
	public List<Job> findjob(@RequestBody Candidate cand) {
		Candidate candi = candidateService.findById(cand.getCandidateId());
		return (List<Job>) candi.getJobSet();
	}
	                                                                      

	
	@DeleteMapping("/deletecandidate")
	public ResponseEntity deleteCandidate(@RequestBody Candidate cand) throws CandidateNotFoundException {
		if(cand!=null)
		{ 

		candidateService.deleteCandidate(cand);

		return new	ResponseEntity<>("Candidate delete successfully"
				,HttpStatus.ACCEPTED);
		}
		else {
			return new	ResponseEntity<>("Candidate delete failed, check id "
					,HttpStatus.FORBIDDEN);
		}
	
	}
	

	
	
	
	@DeleteMapping("/deletecandidate/{id}")
	public String deletebyid(@PathVariable int  id ) {

	candidateService.deletebyid(id);

	return "Candidate delete successfully";
	}
	
	
	
	
	

	
	
	
	
	
//	
//	@PatchMapping("/removeskillbyid/{id}")
//	public ResponseEntity removeskillbyid(@RequestBody CandidateSkill cs,@PathVariable int id) {
//
//	service.removeSkillbyId(id, cs);
//	
//	return new	ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
//	}
//	@PatchMapping("/addprojectbyid/{id}")
//	public ResponseEntity addProjectbyid(@RequestBody Project pr,@PathVariable int id) {
//
//	service.addProjectbyId(id, pr);
//	
//	return new	ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
//	}
//	@PatchMapping("/removeprojectbyid/{id}")
//	public ResponseEntity removeProjectbyid(@RequestBody Project pr,@PathVariable int id) {
//
//	service.removeProjectbyId(id, pr);
//	
//	return new	ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
//	}
	
	
//	@GetMapping("findByName")

	
	}
