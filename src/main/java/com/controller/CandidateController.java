package com.controller;

import java.util.List;
import java.util.NoSuchElementException;
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
import org.springframework.web.bind.annotation.RestController;

import com.dto.ProfileDTO;
import com.dto.RatingFeedbackDTO;
import com.exception.CandidateNotFoundException;
import com.exception.CandidateValidationExceptioncheck;
import com.exception.FormatException;
import com.exception.ProjectNotFoundException;
import com.exceptions.NoSuchInterviewFoundException;
import com.model.Candidate;
import com.model.Interview;

import com.model.Skill;
import com.service.CandidateService;
import com.service.InterviewService;
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
	 
	//user Story 2 -able to add profile 
	@PostMapping("/addProjectById/{candidateId}")
    public ResponseEntity addProject(@RequestBody List<Project> project,@PathVariable String id)throws CandidateValidationExceptioncheck,CandidateNotFoundException, FormatException {
       
        try {
        candidateService.addProjectbyId(Integer.parseInt(id),project);
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
	   @PostMapping("/updatelocationbyid/{id}")
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
	   @GetMapping("/getallcandidates")
	    public List<Candidate> getallCandidates() throws CandidateValidationExceptioncheck {
	        try {
	        return candidateService.getAllCandidates();
	        }
	        catch(ValidationException v) {
	            throw new CandidateValidationExceptioncheck("Validation error");
	        }
	        
	    }
	
	
	   @GetMapping("/findcandidatebyid/{id}")
	    public ResponseEntity getbyid(@PathVariable int  id  ) throws CandidateNotFoundException{
	     
	            try {
	        return new ResponseEntity<>(candidateService.findById(id),HttpStatus.FOUND);
	            }
	            catch (NoSuchElementException c) {
	                throw new CandidateNotFoundException("not found");
	            
	            }
	        }

	   //add skill
	   @PatchMapping("/addskillbyid/{id}")
	    public ResponseEntity addskillbyid(@RequestBody Skill cs,@PathVariable int id) throws CandidateNotFoundException {
	       try {
	    candidateService.addSkillById(id, cs);
	    
	    return new  ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
	    }
	       catch(NoSuchElementException ex){
	           throw new CandidateNotFoundException("check id");
	       }
	       
	   }
	    
	   
	   @PostMapping("/CandidatefeedbackRating/{interviewId}")
	    public ResponseEntity<String> feedbackRating(@PathVariable("interviewId") String id, @RequestBody RatingFeedbackDTO dto) throws NumberFormatException, NoSuchInterviewFoundException {
	     
	        Interview i = interviewService.getInterviewById(Integer.parseInt(id));
	          interviewService.provideCandidateFeedback(i, dto);
	          
	          return new ResponseEntity<>("Feedback and rating by employer saved", HttpStatus.OK);
	      
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/addwholeProject")
	    public ResponseEntity addProjectList(@RequestBody List<Project> project )throws CandidateValidationExceptioncheck {
	       
	        try {
	         
	       // candidateService.addProfile(profile);
	        return new  ResponseEntity<>("Candidate added successfully"
	                ,HttpStatus.ACCEPTED);
	        }
	        catch(ValidationException v) {
	            throw new CandidateValidationExceptioncheck("validation error");
	        }
	   
	        
	    }
	    
	
	

	
	
	
	@PatchMapping("/updatecandidate")
	public ResponseEntity updateCandidate(@RequestBody Candidate cand) throws CandidateValidationExceptioncheck {
		if(cand!=null)
		{ 
			try {
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
	

	

	// need to be eddited
	@GetMapping("/getjobstatus")
	public List<Job> findjob(@RequestBody Candidate cand) {
		Candidate candi = candidateService.findById(cand.getCandidateId());
		return (List<Job>) candi.getJobSet();
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
	
	
	
	
	

	
	
	
	
	
//	
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
