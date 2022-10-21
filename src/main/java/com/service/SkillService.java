package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.SkillDao;
import com.model.Skill;

@Service
public class SkillService {
	@Autowired
	SkillDao cdao;
	

	public void addSkill(Skill s) {
		s.setSkillName(s.getSkillName().toUpperCase());
		cdao.save(s);
	}

}
