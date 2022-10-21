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

import com.dao.CandidateDao;
import com.model.Candidate;

import com.model.Project;
import com.service.CandidateService;

@RestController
public class CandidateController {
	
	@Autowired
	CandidateService service;
	
	@GetMapping("/getallcandidates")
	public List<Candidate> getallCandidates() {
		return service.getAllCandidates();
	}
	@GetMapping("/findcandidatebyid/{id}")
	public ResponseEntity getbyid(@PathVariable int  id  ) {
		if(id!=0 || id>0)
		{ 
		return new ResponseEntity<>(service.findById(id),HttpStatus.FOUND);
		}
		else {
			return new	ResponseEntity<>("Candidate delete failed, check id "
					,HttpStatus.FORBIDDEN);
		}
		
	}
	
	@PostMapping("/addprofile")
	public ResponseEntity addCandidate(@RequestBody Candidate cand) {
		if(cand!=null)
		{ 
		service.addCandidate(cand);
		return new	ResponseEntity<>("Candidate added successfully"
				,HttpStatus.ACCEPTED);
		}
		else {
			return new	ResponseEntity<>("Candidate adding failed "
					,HttpStatus.FORBIDDEN);
		}
		
		
	}
	@PostMapping("/updatelocationbyid/{id}")
	public ResponseEntity updateLocationById(@RequestBody String str,@PathVariable int id) {
		service.updateLocation(id, str);
		
		return new	ResponseEntity<>("Candidate updated location ",HttpStatus.OK);


	}
	
	@PatchMapping("/updatecandidate")
	public ResponseEntity updateCandidate(@RequestBody Candidate cand) {
		if(cand!=null)
		{ 
		service.updateCandidate(cand);
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
		service.deleteCandidate(cand);
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

	service.deletebyid(id);
	return "Candidate delete successfully";
	}
	
	
	
	
	
	
//	@PatchMapping("/addskillbyid/{id}")
//	public ResponseEntity addskillbyid(@RequestBody CandidateSkill cs,@PathVariable int id) {
//
//	service.addSkillById(id, cs);
//	
//	return new	ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
//	}
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
