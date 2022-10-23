package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.SkillDAO;
import com.model.Skill;

@Service
public class SkillService {
	@Autowired
	SkillDAO skillDao;
	

	public void addSkill(Skill s) {
		s.setSkillName(s.getSkillName().toUpperCase());
		skillDao.save(s);
	}

}
