package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.model.Job;

@Repository
public interface JobDao extends JpaRepositoryImplementation<Job, Integer> {

	List<Job> findByJobSkillSetSkillName(String skillName);
}
