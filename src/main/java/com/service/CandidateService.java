package com.service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.CandidateDAO;
import com.dao.ProjectDAO;
import com.dao.SkillDAO;
import com.dto.ProfileDTO;
import com.enums.PostInterviewStatus;
import com.enums.PreInterviewStatus;
import com.exceptions.NoSuchEmployerFoundException;
import com.model.Candidate;
import com.model.Employer;
import com.model.Interview;
import com.model.Job;
import com.model.Project;
import com.model.Skill;

@Service
public class CandidateService {
	@Autowired
	CandidateDAO candao;
	@Autowired
	SkillDAO skilldao;
	@Autowired
	ProjectDAO projectDao;

	
	//for addwhilecheckingskill
	public void addAndCheckSkill(Candidate c) {
        
        //Stream<Skill> stream = alreadyExits.stream();
        if(!c.getSkillSet().isEmpty()) {
        Set<Skill> temp =c.getSkillSet();
        for(Skill s : temp) {
            s.setSkillName(s.getSkillName().toUpperCase());
            Skill alreadyExits= skilldao.findBySkillNameIgnoreCase(s.getSkillName().toUpperCase());
            if(alreadyExits!=null && alreadyExits.getSkillName().equalsIgnoreCase(s.getSkillName())) {
                
                c.getSkillSet().remove(s);
                c.getSkillSet().add(alreadyExits);
            }
        }
        }
        
        candao.save(c);
    
    }
	//dealing with profile userstory
	public void addProfile(ProfileDTO profile) {
	       Candidate c = new Candidate();
	       c.setCandidateName(profile.getCandidateName());
	       c.setAge(profile.getAge());
	       c.setEducationQualification(profile.getEducationQualification());
	       c.setLocation(profile.getLocation());
	       c.setSkillSet(profile.getSkillSet());
	       
	       c.setProjectList(profile.getProjectList());
	       
	       for(Project pro : profile.getProjectList() ) {
	    	   
	    	   pro.setCandidate(c);
	    	   
	       }
	       
	       addAndCheckSkill(c);
	       
	        
	    }
	  //just an extra thing adding project id
	public void addProjectbyId(int id, List<Project> pr ) throws NoSuchElementException{
	    
	      Candidate c = candao.findById(id).get();
	      
	      for(Project pro : pr) {
	    	   
	    	   pro.setCandidate(c);
	    	   
	       }
	      
	      c.getProjectList().addAll(pr);
	      candao.save(c);
    
	}
	
//	 public void removePojectbyName(int candidateId, int projectId, Project project)throws NoSuchElementException  {
//         Candidate c = candao.findById(candidateId).get();
//         List<Project> temp = c.getProjectList();
//         if(temp.isEmpty()) {
//             throw new NoSuchElementException("Your project list is empty");
//         }
//         Stream<Project> stream = temp.stream();
//         Project p = stream.findFirst((a)->a.);
//         projectDao.delete(project);
//         
//           
//       }
   
	//just an extra thing updating location 
	 public void updateLocation( int id,String loc) throws NoSuchElementException {
	        Candidate c = candao.findById(id).get();

	        
	        c.setLocation(loc);
	        candao.save(c);
	        
	    }
	    
	

	//in case need a exception 
	 public Candidate getCandidateById(int id) throws  NoSuchElementException {
	        
	       Candidate candidate = candao.getById(id);
	       return candidate;
	    
	   }

	 
	   
     //get all candidates
	    public List<Candidate> getAllCandidates() {
	        return candao.findAll();
	        
	    }
	 
	    //adding skil by id
	    public void addSkillById(int id,Skill cs) throws NoSuchElementException {
	    	 Candidate c = candao.findById(id).get();

	            if(!c.getSkillSet().isEmpty()) {
	                Set<Skill> temp =c.getSkillSet();
	            
	                    cs.setSkillName(cs.getSkillName().toUpperCase());
	                    Skill alreadyExits= skilldao.findBySkillNameIgnoreCase(cs.getSkillName().toUpperCase());
	                 
	                    if(alreadyExits!=null && alreadyExits.getSkillName().equalsIgnoreCase(cs.getSkillName())) {
	     
	                        c.getSkillSet().add(alreadyExits);
	                    
	                }
	                    
	                    else {
	                        c.getSkillSet().add(cs);
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
	
	
	
	

	//rating  by id and interview id
	
	public void ratetheinterview(float rate,String id) {
		
		
	}

  

	

	
	
	
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
