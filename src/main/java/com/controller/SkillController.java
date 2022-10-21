package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.SkillDao;
import com.model.Skill;
import com.service.SkillService;

@RestController
public class SkillController {

	@Autowired
	SkillService service;
	@Autowired
	SkillDao dao;
	
	
	@PostMapping("/addSkill")
	public String addSill(@RequestBody Skill sk) {
		
		dao.save(sk);
		return "added";
	}
	
}
