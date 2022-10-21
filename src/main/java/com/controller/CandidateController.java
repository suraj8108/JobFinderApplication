package com.controller;

import java.util.List;
import java.util.Set;

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
import com.model.Candidate;
import com.model.Job;

@RestController
public class CandidateController {
	
	@Autowired
	CandidateDao dao;
	
	@GetMapping("/getallcandidates")
	public List<Candidate> getallCandidates() {
		return dao.findAll();
	}
	
	@GetMapping("/getjobstatus")
	public List<Job> findjob(@RequestBody Candidate cand) {
		Candidate candi = dao.findById(cand.getCandidateId()).get();
		return (List<Job>) candi.getJobSet();
	}
	
	@PostMapping("/addcand")                                                      
	public ResponseEntity addcand(@RequestBody Candidate cand) {                          
		dao.save(cand);                                                         
		return new ResponseEntity("candidate added successfuly", HttpStatus.ACCEPTED);  
	}                                                                             
	
	
	@PatchMapping("/updatecandidate")
	public ResponseEntity updateCandidate(@RequestBody Candidate cand) {
		if(cand!=null)
		{ 
		dao.save(cand);
		return new	ResponseEntity<>("Candidate updated successfully"
				,HttpStatus.ACCEPTED);
		}
		else {
			return new	ResponseEntity<>("Candidate updation failed, check id "
					,HttpStatus.FORBIDDEN);
		}
	}
	
	@DeleteMapping("/deletecandidate")
	public ResponseEntity deleteCandidate(@RequestBody Candidate cand) {
		if(cand!=null)
		{ 
		dao.deleteById(cand.getCandidateId());
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

	dao.deleteById(id);
	return "Candidate delete successfully";
	}
	
	
	}