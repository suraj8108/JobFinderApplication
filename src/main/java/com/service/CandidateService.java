package com.service;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.validation.ValidationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import com.dao.CandidateDAO;

import com.dao.ProjectDAO;
import com.dao.SkillDAO;
import com.dto.ProfileDTO;
import com.dto.ProjectDTO;
import com.dto.SkillDTO;
import com.exception.CandidateNotFoundException;
import com.exception.CandidateValidationExceptioncheck;
import com.exception.skillNotFoundException;
import com.enums.PostInterviewStatus;
import com.exception.AlreadySelectedBySameEmployerException;
import com.exception.MoonLightingException;
import com.model.Candidate;
import com.model.Job;
import com.model.Employer;
import com.model.Interview;
import com.model.Project;
import com.model.Skill;

import io.jsonwebtoken.lang.Collections;

@Service
public class CandidateService {
	@Autowired
	CandidateDAO candao;
	
	@Autowired
	SkillDAO skilldao;
	@Autowired
	ProjectDAO projectDao;
	
	@Autowired
	SkillService skillService;
	
	@Autowired
	ProjectService projectService;
	
	
	public Skill convertSkillDtoToSkill(SkillDTO dto) {
	    Skill skill = new Skill();
	    skill.setSkillName(dto.getSkillName());
	    return skill;
	}
	
	public  Project convertProjectDtoToProject(ProjectDTO dto) {
	    Project project= new Project();
	    project.setProjectName(dto.getProjectName());
	    project.setProjectDescription(dto.getProjectDescription());
	    
	    
	    return project;
	}

	public Candidate findCandidateByName(String name) {
        return candao.findByCandidateName(name);
	    
	}
	
	public Candidate findCandidateByEmailId(String emailId) {
		return candao.findByEmailId(emailId);
	}
	
	
	//for addwhilecheckingskill

	//dealing with profile userstory
	public void addProfile(ProfileDTO profile) {
	       Candidate c = new Candidate();
	       
	       c.setCandidateName(profile.getCandidateName());
	       c.setAge(profile.getAge());
	       c.setEducationQualification(profile.getEducationQualification());
	       c.setLocation(profile.getLocation());
	       
	       c.setEmailId(profile.getEmailId());
	       c.setPassword(profile.getPassword());
	       c.setExperience(profile.getExperience());
	       
	       List<Project> projectList = new ArrayList<>();
	       
	      for (ProjectDTO pdt : profile.getProjectDTOList()) {
	          projectList.add(convertProjectDtoToProject(pdt));
	      }
	      
//	       System.out.println(c);
	      c.setProjectList(projectList);
	       
	       for(Project pro : projectList ) {
	    	   
	    	   pro.setCandidate(c);
	    	   
	       }
	      
	       Set<Skill> skillSet = new HashSet<>();
	       
	       for (SkillDTO pdt : profile.getSkillDTOSet()) {
	           skillSet.add(convertSkillDtoToSkill(pdt));
	          }

	       
	       
	       
	       Set<Skill> noRepSkills = skillService.addingSkillsWithNoRepetation(skillSet);
	      
	       
	      c.setSkillSet(noRepSkills);
	      try {
	       candao.save(c);
	      }
	      catch(ValidationException v)
	      {
	          throw new CandidateValidationExceptioncheck("hey there check the docs for validation errors");
	      }
	        
	    }
	
	
	
	
	
	
	
	  //just an extra thing adding project id
	public void addProjectbyId(int id, List<ProjectDTO> project ) throws CandidateNotFoundException  {
	    
	    try {
	        
	      Candidate c = candao.findById(id).get();
	      List<Project> listPro = new ArrayList<>();
          for(ProjectDTO proDTO : project) {
              Project p1 = new Project();
              p1.setProjectName(proDTO.getProjectName());
              p1.setProjectDescription(proDTO.getProjectDescription());
              p1.setCandidate(c);
              listPro.add(p1);
              
          }
	      c.getProjectList().addAll(listPro);
	     candao.save(c);
	    }
	    catch(NoSuchElementException n) {
	        throw new CandidateNotFoundException("Check the id entered");
	        
	    }
	      
	     
	}

  
	

   
	//just an extra thing updating location by candidate id 
	 public void updateLocationByCandidateId( int CandidateId,String loc) throws NoSuchElementException {
	        Candidate c = candao.findById(CandidateId).get();

	        
	        c.setLocation(loc);
	        candao.save(c);
	        
	    }
	 
	 
	 
	 
	 
	  //adding skill by id
	    public void addSkillDTOByCandidateId(int id, SkillDTO cs) throws CandidateNotFoundException {
	        try {
	        Skill skill = convertSkillDtoToSkill(cs);
	        
	         Candidate c = candao.findById(id).get();
	         
	   
	         
	         
	         
	         Set<Skill> skillSet = new HashSet<>();
	         skillSet.add(skill);

	         Set<Skill> noRepSkills = skillService.addingSkillsWithNoRepetation(skillSet);
	        c.getSkillSet().addAll(noRepSkills);
	            
	                candao.save(c);
	        }
	        catch(NoSuchElementException n) {
	            throw new CandidateNotFoundException("Candidate User Not Found");
	            
	        }

	    }   
	    
	    
	  public void removeSkillbyCanidateIdAndSkillName(int candidateId,String skillName) throws skillNotFoundException, CandidateNotFoundException {
	      try {
	      Candidate c = candao.findById(candidateId).get();
      
      Skill skill = skilldao.findBySkillNameIgnoreCase(skillName);
      if(skill==null) {
          throw new skillNotFoundException("No such skill is linked with candidate");
      }
      c.getSkillSet().remove(skill);
      candao.save(c);
	      }
	      catch(NoSuchElementException e) {
	          throw new CandidateNotFoundException("No candidate is found while checking skill");
	      }
  }
	    
	

	//in case need a exception 
	 public Candidate getCandidateById(int id) throws  NoSuchElementException {
	        
	       Candidate candidate = candao.getById(id);
	       return candidate;
	    
	   }

	 
	   
     //get all candidates
	    public List<Candidate> getAllCandidates() throws CandidateNotFoundException {
	        List<Candidate> temp =candao.findAll();
	        if (temp.isEmpty()) {
	            throw new CandidateNotFoundException("Data Base is Empty");
	        }
	        return temp;
	        
	    }
	 
	    public void updateProfle(ProfileDTO profile) {
	        Candidate c = new Candidate();
	           
	           c.setCandidateName(profile.getCandidateName());
	           c.setAge(profile.getAge());
	           c.setEducationQualification(profile.getEducationQualification());
	           c.setLocation(profile.getLocation());
	           
	           c.setEmailId(profile.getEmailId());
	           c.setPassword(profile.getPassword());
	           
	           List<Project> projectList = new ArrayList<>();
	           
	          for (ProjectDTO pdt : profile.getProjectDTOList()) {
	              projectList.add(convertProjectDtoToProject(pdt));
	          }
	          
//	         System.out.println(c);
	          c.setProjectList(projectList);
	           
	           for(Project pro : projectList ) {
	               
	               pro.setCandidate(c);
	               
	           }
	          
	           Set<Skill> skillSet = new HashSet<>();
	           
	           for (SkillDTO pdt : profile.getSkillDTOSet()) {
	               skillSet.add(convertSkillDtoToSkill(pdt));
	              }
	           
	           
	           
	           Set<Skill> noRepSkills = skillService.addingSkillsWithNoRepetation(skillSet);
	          
	           
	          c.setSkillSet(noRepSkills);
	          try {
	           candao.save(c);
	          }
	          catch(ValidationException v)
	          {
	              throw new CandidateValidationExceptioncheck("hey there check the docs for validation errors");
	          }
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
	
	
	public String deleteAllCandidate() {
		candao.deleteAll();
		return "Successfully deleted all the Candidate";
	}
	

	//rating  by id and interview id
	
	public void ratetheinterview(float rate,String id) {
		
		
	}
	
	public void checkIfAlreadySelectedByEmployer(Candidate candidate, Employer employer) throws AlreadySelectedBySameEmployerException, MoonLightingException {
	  List<Interview> interviewList = candidate.getInterviewList();
	  for (Interview i: interviewList) {
	    if (i.getPostInterviewStatus().equals(PostInterviewStatus.SELECTED)) {
	      Employer e = i.getEmployer();
	      if (e.getEmployerId() == employer.getEmployerId())
	        throw new AlreadySelectedBySameEmployerException(i.getEmployer().getEmployerName());
	      else
	        throw new MoonLightingException();
	    }
	  }
	}



    public void save(Candidate cand2) {
       candao.save(cand2);
    }

   

   

  

  
	public List<Candidate> getAllCandidatesByExperience(int experience) {
		return candao.findAllByExperience(experience);
	}
	
	
	public List<Candidate> getAllCandidatesByQualification(String qualification) {
		return candao.findAllByEducationQualification(qualification);
	}
	
	public List<Candidate> getAllCandidatesBySkillSet(String skills){
		List<Candidate> result = new ArrayList<>();
		
		List<Candidate> candidates= candao.findAll();
		
		String [] skillsRequired = skills.split(",");

		System.out.println(Arrays.toString(skillsRequired));
		System.out.println(skills);
		
		
		for(Candidate c: candidates) {
			
			Set<String> candSkills = new HashSet<>();
			for (Skill skill : c.getSkillSet()) {
				candSkills.add(skill.getSkillName());
			}
			
			for(String skill : skillsRequired) {	
				if( candSkills.contains(skill)) {
					result.add(c);
					break;	
				}	
			}	
		}
		return result;
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
