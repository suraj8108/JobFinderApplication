package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Job;
@Repository
public interface JobDao extends JpaRepository<Job, Integer>{
	
	List<Job> findAllByJobDescription(String desc);
	List<Job> findAllByStatus(String status);

}

