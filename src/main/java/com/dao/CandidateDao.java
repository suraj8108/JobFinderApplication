package com.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Candidate;
@Repository
public interface CandidateDao extends JpaRepository<Candidate, Integer>{

	public Candidate findByCandidateName(String candidateName);
	Candidate findByEmailId(String emailId);
	
}
