package com.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.dao.CandidateDao;
import com.dao.SkillDao;
import com.model.Candidate;

import com.model.Project;
import com.model.Skill;

@Service
public class CandidateService {
	@Autowired
	CandidateDao candao;
	@Autowired
	SkillDao skilldao;
	
	public List<Candidate> getAllCandidates() {
		return candao.findAll();
		
	}
	
	public void addCandidate(Candidate c) {
		
		//Stream<Skill> stream = alreadyExits.stream();
		if(!c.getSkillSet().isEmpty()) {
		Set<Skill> temp =c.getSkillSet();
		for(Skill s : temp) {
			s.setSkillName(s.getSkillName().toUpperCase());
			Skill alreadyExits=	skilldao.findBySkillNameIgnoreCase(s.getSkillName().toUpperCase());
			if(alreadyExits!=null && alreadyExits.getSkillName().equalsIgnoreCase(s.getSkillName())) {
				
				c.getSkillSet().remove(s);
				c.getSkillSet().add(alreadyExits);
			}
		}
		}
		
		candao.save(c);
	
	}
	public Candidate findById(int id) {
		if(candao.existsById(id)) {
		return candao.findById(id).get();
		}
		{
			return null;
		}
	}
	
	public void updateCandidate(Candidate c) {
		if(candao.existsById(c.getCandidateId()))
		{
//		Candidate temp = candao.findById(c.getCandidateId()).get();
//		if(c.getAge()!=0)
//			temp.setAge(c.getAge());
//		if(c.getCandidateName()!=null)
//			temp.setCandidateName(c.getCandidateName());
//		if(c.getCanditationSkillSet()!=null)
//			temp.setCanditationSkillSet(c.getCanditationSkillSet());
//		if(c.getEducationQualification()!=null)
//			temp.setEducationQualification(c.getEducationQualification());
//		if(c.getLocation()!=null)
//			temp.setLocation(c.getLocation());
//		if(c.getProjectList()!=null)
//			temp.setProjectList(c.getProjectList());
		candao.save(c);
		
		
		}
	}
	public boolean deleteCandidate(Candidate c) {
		Example<Candidate> ex = Example.of(c);
		
		if(candao.exists(ex)) {
			
			candao.delete(c);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public void deletebyid(int id) {
		candao.deleteById(id);
	}
	
	
	public void updateLocation( int id,String loc) {
		Optional<Candidate> c = candao.findById(id);

		if(c.isPresent()) {
		c.get().setLocation(loc);
		candao.save(c.get());
		}
	}
//	public void addSkillById(int id,CandidateSkill cs) {
//		Candidate c = candao.findById(id).get();
//		c.getCanditationSkillSet().add(cs);
//		candao.save(c);
//	}
//	public void removeSkillbyId(int id, CandidateSkill cs) {
//		Candidate c = candao.findById(id).get();
//		c.getCanditationSkillSet().remove(cs);
//		candao.save(c);
//	}
//	public void addProjectbyId(int id, Project pr ) {
//		Candidate c = candao.findById(id).get();
//		c.getProjectList().add(pr);
//		candao.save(c);
//		
//	}
//	public void removeProjectbyId(int id, Project pr ) {
//		Candidate c = candao.findById(id).get();
//		c.getProjectList().remove(pr);
//		candao.save(c);
//		
//	}

//	public Candidate findByName(String candidateName) {
//		
//		Candidate c = candao.findByCandidateName(candidateName);
//		return c;
//	}
	
//	public void query() {
//		
//	}
	
	
}
