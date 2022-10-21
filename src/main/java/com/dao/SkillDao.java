package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.model.Skill;
@Repository
public interface SkillDao extends JpaRepository<Skill, Integer> {

	
	//public List<Skill> findBySkillName(String skillName );
	public Skill findBySkillName(String skillName );
}
