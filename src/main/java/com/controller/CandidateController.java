package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.EmployerDAO;
import com.dao.InterviewDAO;
import com.dao.JobDAO;
import com.dto.ProfileDTO;
import com.dto.ProjectDTO;
import com.dto.RatingFeedbackDTO;
import com.enums.PostInterviewStatus;
import com.enums.PreInterviewStatus;
import com.exception.CandidateNotFoundException;
import com.exception.CandidateValidationExceptioncheck;
import com.exception.FormatException;
import com.exception.NoSuchInterviewFoundException;
import com.exception.ProjectNotFoundException;
import com.dao.CandidateDAO;

import com.model.Candidate;
import com.model.Employer;
import com.model.Interview;

import com.model.Skill;
import com.service.CandidateService;
import com.service.InterviewService;
import com.service.JobService;
import com.service.ProjectService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.model.Job;
import com.model.Project;


@RestController
public class CandidateController {
	
	@Autowired
	CandidateService candidateService;
	@Autowired
	InterviewService interviewService;
	@Autowired
	ProjectService projectService;
	
	@Autowired
	EmployerDAO employerDAO;

	@Autowired
	JobDAO jobDAO;

	@Autowired
	InterviewDAO interviewDAO;
	
	@Autowired
    JobService jobService;
	
	
	//user Story 2 -able to add profile 
    // - should be linked to user register
	@ApiOperation(value = "Add",notes="Add profile",nickname = "addprofile" )
    @ApiResponses(value= {@ApiResponse(code=200, message="all jobs")})
	@PostMapping("/addProfile")
    public ResponseEntity addCandidate(@RequestBody ProfileDTO profile)throws CandidateValidationExceptioncheck {
		
        try {
        candidateService.addProfile(profile);
        return new  ResponseEntity<>("Candidate added successfully"
                ,HttpStatus.ACCEPTED);
        }
        catch(ValidationException v) {
            throw new CandidateValidationExceptioncheck("Check validation error");
        }
   
        
    }
	 
	//user Story 2 -able to add Project by Id
	@PostMapping("/addProjectById/{candidateId}")
    public ResponseEntity addProject(@RequestBody List<ProjectDTO> project,@PathVariable(name = "candidateId") String id)throws Exception {
       
        try {
        	
        	List<Project> listPro = new ArrayList<>();
        	for(ProjectDTO proDTO : project) {
        		Project p1 = new Project();
        		p1.setProjectName(proDTO.getProjectName());
            	p1.setProjectDescription(proDTO.getProjectDescription());
                
            	listPro.add(p1);
        	}
        	candidateService.addProjectbyId(Integer.parseInt(id),listPro);
        	
        return new  ResponseEntity<>("Candidate added successfully"
                ,HttpStatus.ACCEPTED);
        }
        catch(NoSuchElementException v) {
            throw new CandidateNotFoundException("Check the id entered");
        }
        catch(ValidationException v) {
            throw new CandidateValidationExceptioncheck("Check validation error");
        }
        catch (NumberFormatException e) {
            throw new FormatException("Check the format of the input or wrong user id");
        }
        catch(Exception e) {
        	throw new Exception(e.getMessage());
        }
       
    }
	
	
	//user Story 2 -able to add profile 
	@DeleteMapping("/removeProject")
	public ResponseEntity removeProject
	(@RequestBody Project project) throws ProjectNotFoundException {
	    try {
	        projectService.remove(project);
	        return new  ResponseEntity<>("Candidate project removed successfully"
	                ,HttpStatus.ACCEPTED);
	    }
	    catch(NoSuchElementException v){
	        throw new ProjectNotFoundException("Check the project details entered entered");
	        
	    }
      
	}
	
	
	    
	//user Story 2 -able to add profile 
	   @PostMapping("/updateLocationById/{id}")
	   public ResponseEntity updateLocationById(@RequestBody String str,@PathVariable int id)throws CandidateValidationExceptioncheck {
	        try {
	        candidateService.updateLocation(id, str);
	        }
	        catch(ValidationException v) {
	            throw new CandidateValidationExceptioncheck("validation error");
	        }
	        
	        return new  ResponseEntity<>("Candidate updated location ",HttpStatus.OK);


	    }
	
	 //user Story 2 -able to add profile 
	   @GetMapping("/getAllCandidates")
	    public List<Candidate> getallCandidates() throws CandidateValidationExceptioncheck {
	        try {
	        return candidateService.getAllCandidates();
	        }
	        catch(ValidationException v) {
	            throw new CandidateValidationExceptioncheck("Validation error");
	        }
	        
	    }
	
	
	   @GetMapping("/findCandidateById/{id}")
	    public ResponseEntity getbyid(@PathVariable int  id  ) throws CandidateNotFoundException{
	     
	            try {
	            	return new ResponseEntity<>(candidateService.findById(id),HttpStatus.FOUND);
	            }
	            catch (NoSuchElementException c) {
	                
	            	throw new CandidateNotFoundException("Not found");
	            
	            }
	        }

	   //add skill to a particular candidate
	   @ApiOperation(value = "Add",notes="Add Candidate Skill By Candidate Id",nickname = "Add Skill to Candidate" )
	   @PatchMapping("/addSkillById/{id}")
	    public ResponseEntity addSkillByCandidateId(@RequestBody Skill cs,@PathVariable String id) throws CandidateNotFoundException {
	       try {
	    	   
	    	   System.out.println(cs);
	    	   	candidateService.addSkillById(Integer.parseInt(id), cs);
	    
	    	   return new  ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
	    }
	       catch(NoSuchElementException ex){
	           throw new CandidateNotFoundException("check id");
	       }
	       
	   }
	    
	   @PostMapping("/candidateFeedbackRating/{interviewId}")
	    public ResponseEntity<String> feedbackRating(@PathVariable("interviewId") String id, @RequestBody RatingFeedbackDTO dto) throws NumberFormatException, NoSuchInterviewFoundException {
	     
	        Interview i = interviewService.getInterviewById(Integer.parseInt(id));
	          interviewService.provideCandidateFeedback(i, dto);
	          
	          return new ResponseEntity<>("Feedback and rating by employer saved", HttpStatus.OK);
	      
	    }

	
	
	@PatchMapping("/updatecandidate")
	public ResponseEntity updateCandidate(@RequestBody ProfileDTO candDto) throws CandidateValidationExceptioncheck {
		if(candDto!=null)
		{ 
			try {
				
				Candidate cand = new Candidate();
				
				cand.setAge(candDto.getAge());
				cand.setCandidateName(candDto.getCandidateName());
				cand.setEducationQualification(candDto.getEducationQualification());
				cand.setExperience(candDto.getExperience());
				cand.setProjectList(candDto.getProjectList());
				cand.setSkillSet(candDto.getSkillSet());
				cand.setLocation(candDto.getLocation());
				
				candidateService.updateCandidate(cand);
			
			}
			catch(ValidationException v) {
				throw new CandidateValidationExceptioncheck("validation error");
			}

			return new	ResponseEntity<>("Candidate updated successfully"
					,HttpStatus.ACCEPTED);
		}
		else {
			return new	ResponseEntity<>("Candidate updation failed, check id "
					,HttpStatus.FORBIDDEN);
		}

		
	}
	

	@GetMapping("/getjobstatus/{id}")
	public List<Interview> findjob(@PathVariable  int id) {
		Candidate candidate = candidateService.findById(id);
		List<Interview> interviewStatus = candidate.getInterviewList();
		return  interviewStatus ;
	}
	
	
	@DeleteMapping("/deletecandidate")
	public ResponseEntity deleteCandidate(@RequestBody Candidate cand) throws CandidateNotFoundException {
		if(cand!=null)
		{ 

			candidateService.deleteCandidate(cand);
	
			return new	ResponseEntity<>("Candidate delete successfully"
					,HttpStatus.ACCEPTED);
		}
		else {
			return new	ResponseEntity<>("Candidate delete failed, check id "
					,HttpStatus.FORBIDDEN);
		}
	
	}
	
	
	@DeleteMapping("/deletecandidate/{id}")
	public String deletebyid(@PathVariable int  id ) {

	candidateService.deletebyid(id);

	return "Candidate delete successfully";
	}


	//Naman
	// OM check this method and sync with ur user story to be removed from here  
	@PostMapping("/candidateAppliesForJob")
	public ResponseEntity<String> candidateAppliesForJob(@RequestParam("candidateId") String candidateId, @RequestParam("jobId") String jobId) {
  
			  try {	    
			    // assuming the candidate is already created,
			    // find the candidate, employer and job using the ids
			    Candidate c = candidateService.getCandidateById(Integer.parseInt(candidateId));
			    Job j = jobService.getJobById(Integer.parseInt(jobId));
			    Employer e = j.getCreatedBy();
			    
			    
			    j.getCandidateSet().add(c);
			    
			    Interview i = new Interview();
			    i.setCandidate(c);
			    i.setJob(j);
			    i.setEmployer(j.getCreatedBy());
			    i.setPreInterviewStatus(PreInterviewStatus.INVALID);
			    i.setPostInterviewStatus(PostInterviewStatus.INVALID);
			    
			    jobDAO.save(j);
			    interviewDAO.save(i);
			    
			    // now add the newly created interview to interviewlist of employer
			    e.getInterviewList().add(i);
			    employerDAO.save(e);
			  } catch (Exception e) {
			    System.out.println(e.getMessage());
			  }
			  
			  return new ResponseEntity<>("Candidate successfully applied for this job", HttpStatus.OK);
		}
	
//	@PatchMapping("/removeskillbyid/{id}")
//	public ResponseEntity removeskillbyid(@RequestBody CandidateSkill cs,@PathVariable int id) {
//
//	service.removeSkillbyId(id, cs);
//	
//	return new	ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
//	}
//	@PatchMapping("/addprojectbyid/{id}")
//	public ResponseEntity addProjectbyid(@RequestBody Project pr,@PathVariable int id) {
//
//	service.addProjectbyId(id, pr);
//	
//	return new	ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
//	}

	
	
//	@GetMapping("findByName")

	
	}
