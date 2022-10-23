package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.SkillDAO;
import com.model.Skill;
import com.service.SkillService;

@RestController
public class SkillController {

	@Autowired
	SkillService service;
	@Autowired
	SkillDAO dao;
	
	
	/*
	 * Candidate can add the skills
	 * Job can add the Skills
	 * 
	 */
	@PostMapping("/addSkill")
	public ResponseEntity<String> addSill(@RequestBody Skill sk) {
		
		dao.save(sk);
		
		return new ResponseEntity("Added Successfully", HttpStatus.OK);
	
	}
	
}
