package com.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CandidateDAO;
import com.dao.EmployerDAO;
import com.dao.InterviewDAO;
import com.dao.JobDAO;
import com.dto.ProfileDTO;
import com.dto.ProjectDTO;
import com.dto.RatingFeedbackDTO;
import com.dto.SkillDTO;
import com.enums.PostInterviewStatus;
import com.enums.PreInterviewStatus;
import com.exception.CandidateNotFoundException;
import com.exception.CandidateValidationExceptioncheck;
import com.exception.FormatException;
import com.exception.JobNotFoundException;
import com.exception.NoSuchInterviewFoundException;
import com.exception.NoSuchJobFoundException;
import com.exception.ProjectNotFoundException;
import com.exception.exceptions;
import com.exception.feedbackException;
import com.exception.skillNotFoundException;
import com.helper.DecryptUserDetails;
import com.helper.JwtUtil;
import com.model.Candidate;
import com.model.Employer;
import com.model.Interview;

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
    CandidateDAO candao;
	@Autowired
	InterviewService interviewService;
	@Autowired
	ProjectService projectService;
	 
	@Autowired
	CandidateDAO candidateDAO;
	
	@Autowired
	EmployerDAO employerDAO;

	@Autowired
	JobDAO jobDAO;

	@Autowired
	InterviewDAO interviewDAO;
	
	@Autowired
    JobService jobService;
	
	@Autowired
	DecryptUserDetails decryptUser;
	
	//user Story 2 -able to add profile 
    // - should be linked to user register
	@ApiOperation(value = "Add",notes="Add profile",nickname = "addprofile" )
    @ApiResponses(value= {@ApiResponse(code=200, message="all jobs")})
	@PostMapping("/addProfile")
    public ResponseEntity addProfile(@RequestBody ProfileDTO profile)throws CandidateValidationExceptioncheck {
		
        try {
        candidateService.addProfile(profile);
        return new  ResponseEntity<>("Candidate added successfully"
                ,HttpStatus.ACCEPTED);
        }
        catch(CandidateValidationExceptioncheck c) {
            throw new CandidateValidationExceptioncheck(c.getMessage());
        }
        catch(ValidationException v) {
            throw new CandidateValidationExceptioncheck("Check validation error");
        }
       
   
        
    }
	 
	//user Story 2 -able to add Project by Id
	@ApiOperation(value = "Adding Project",notes="Candidate Id fetched from token",nickname = "Add Project By Candidate Id")
	@PostMapping("/addProjectById")
    public ResponseEntity addProject(HttpServletRequest request, @RequestBody List<ProjectDTO> project) throws CandidateNotFoundException, FormatException{
       
       try {
    	   
    	   String emailId = decryptUser.decryptEmailId(request);
    	   
    	   int candidateId = candidateService.findCandidateByEmailId(emailId).getCandidateId();
        	
    	   candidateService.addProjectbyId(candidateId, project);
        	
        return new  ResponseEntity<>("Project added successfully"
                ,HttpStatus.ACCEPTED);
        }
       
        catch(ValidationException v) {
            throw new CandidateValidationExceptioncheck("Check validation error");
        }
        catch (NumberFormatException e) {
            throw new FormatException("Check the format of the input or wrong user id");
        }
      
       
    }
	
	
	//user Story 2 -able to remove project 
	@DeleteMapping("/removeProject/{projectid}")
	public ResponseEntity removeProjectByProjectId
	(@PathVariable("projectid") String projectid ) throws ProjectNotFoundException, FormatException {
	    try {
	        projectService.removeById(Integer.parseInt(projectid));
	        return new  ResponseEntity<>("Candidate project removed successfully"
	                ,HttpStatus.ACCEPTED);
	    }
	    catch(NoSuchElementException v){
	        throw new ProjectNotFoundException("Check the project details entered entered");
	        
	    }
	    catch(ProjectNotFoundException p) {
	    throw new ProjectNotFoundException(p.getMessage());
	    }
	    catch(NumberFormatException n) {
            throw new FormatException("Check the format of the input or wrong user id");

	    }
      
	}
	
	
	    
	//user Story 2 -able to add profile 
	   @PostMapping("/updateLocationById/{id}")
	   public ResponseEntity updateLocationById(@RequestBody String str,@PathVariable int id)throws CandidateValidationExceptioncheck, CandidateNotFoundException {
	        try {
	        candidateService.updateLocationByCandidateId(id, str);

            return new  ResponseEntity<>("Candidate updated location",HttpStatus.OK);
	        }
	        catch(ValidationException v) {
	            throw new CandidateValidationExceptioncheck("validation error");
	        }
	        catch(NoSuchElementException e)
	        {
	            throw new CandidateNotFoundException("Ek bar id tho dheko ");
	        }


	    }
	   
     //add skill to a particular candidate
     @ApiOperation(value = "Add",notes="Add Candidate Skill By Candidate Id",nickname = "Add Skill to Candidate" )
     @PatchMapping("/addSkillById/{candidateId}")
      public ResponseEntity addSkillByCandidateId(@RequestBody SkillDTO cs,@PathVariable String candidateId) throws CandidateNotFoundException {
         try {
             
             System.out.println(cs);
              candidateService.addSkillDTOByCandidateId(Integer.parseInt(candidateId), cs);
      
             return new  ResponseEntity<>("Candidate skill added succefully ",HttpStatus.OK);
      }
         catch(NoSuchElementException ex){
             throw new CandidateNotFoundException("check id");
         }
         
     }
     
     @PatchMapping("/removeskillbyCanidateIdAndSkillName/{candidateId}/{skillName}")
     public ResponseEntity removeSkillbyCanidateIdAndSkillName(@PathVariable("candidateId") String candidateId,@PathVariable("skillName") String skillName) throws NumberFormatException, skillNotFoundException, CandidateNotFoundException {
         try {
         candidateService.removeSkillbyCanidateIdAndSkillName(Integer.parseInt(candidateId),skillName);
 
         return new  ResponseEntity<>("Candidate skill removed succefully ",HttpStatus.OK);
         }
         catch(CandidateNotFoundException c) {
             throw new  CandidateNotFoundException(c.getMessage());
             
         }
         catch(NumberFormatException n) {
             throw new  CandidateNotFoundException("Check the id entered");
             
         }
         catch(skillNotFoundException s) {
             throw new  skillNotFoundException(s.getMessage());
             
         }
 }
     
     //user Story 2 -able to add profile 
     @GetMapping("/getAllCandidates")
      public List<Candidate> getallCandidates() throws CandidateNotFoundException {
          try {
              
          return candidateService.getAllCandidates();
          }
          catch(CandidateNotFoundException v) {
              throw new CandidateNotFoundException(v.getMessage());
          }
          
      }
     

     @GetMapping("/findCandidateById/{id}")
   public ResponseEntity getCandidatebyid(@PathVariable int  id  ) throws CandidateNotFoundException{
    
           try {
        	   
               return new ResponseEntity<>(candidateService.findById(id),HttpStatus.FOUND);
           }
           catch (NoSuchElementException c) {
               
               throw new CandidateNotFoundException("User Not found");
           
           }
       }
  
     
     @PostMapping("/candidateFeedbackRating/{interviewId}")
      public ResponseEntity<String> feedbackRating(@PathVariable("interviewId") String id, @RequestBody RatingFeedbackDTO dto) throws NumberFormatException, feedbackException {
         try {
          
            
                interviewService.provideCandidateFeedback(Integer.parseInt(id), dto);
            
                // TODO Auto-generated catch block
               
          
            
          return new ResponseEntity<>("Feedback and rating by candidate saved", HttpStatus.OK);
     }
     catch (feedbackException e) {
     
         throw new feedbackException(e.getMessage());
     }
      
         
     }
     
     
     
     
     @DeleteMapping("/deletecandidate/{id}")
   public ResponseEntity deleteCandidatebyid(@PathVariable int  id ) {
 
   candidateService.deletebyid(id);
 
   return new ResponseEntity("Candidate delete successfully",HttpStatus.FOUND);
   }
 
	   
   
   // OM check this method and sync with ur user story to be removed from here  
//   @PostMapping("/candidateAppliesForJob")
//   public ResponseEntity<String> candidateAppliesForJob(@RequestParam("candidateId") String candidateId, @RequestParam("jobId") String jobId) throws exceptions {
//   
//	   try {	    
//		    // assuming the candidate is already created,
//		    // find the candidate, employer and job using the ids
//		    Candidate c = candidateService.getCandidateById(Integer.parseInt(candidateId));
//		    Job j = jobService.getJobById(Integer.parseInt(jobId));
//		    Employer e = j.getCreatedBy();
//		    
//		    candidateService.checkIfAlreadySelectedByEmployer(c, e);
//		    
//		    // add the candidate to the candidate set of the job
//		    j.getCandidateSet().add(c);
//		    jobDAO.save(j);
//		    
//		    // create a new interview object unique to the employer, candidate, and job
//		    Interview i = new Interview();
//		    i.setCandidate(c);
//		    i.setJob(j);
//		    i.setEmployer(e);
//		    i.setPreInterviewStatus(PreInterviewStatus.INVALID);
//		    i.setPostInterviewStatus(PostInterviewStatus.INVALID);
//		    interviewDAO.save(i);
//		    
//		    
//		    // now add the newly created interview to the interview list of employer
//		    e.getInterviewList().add(i);
//		    employerDAO.save(e);
//		    
//		    // also add this interview to the interview list of the employer
//		    c.getInterviewList().add(i);
//           candidateDAO.save(c);
//           
//           // and add it to interview list of the job
//           j.getInterviewList().add(i);
//           jobDAO.save(j);
//		    
//		    return new ResponseEntity<>("Candidate successfully applied for this job", HttpStatus.OK);
//		  } catch (Exception e) {
//		    return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
//		  }
//             
//       }
	
   
   //no service tests from here
 @PatchMapping("/updatecandidate")
public ResponseEntity updateCandidate(@RequestBody ProfileDTO candDto) throws CandidateValidationExceptioncheck {
  
   
       try {
           
           
           candidateService.addProfile(candDto);
           return new  ResponseEntity<>("Candidate updation failed, check id "
                   ,HttpStatus.FORBIDDEN);
       }
       catch(ValidationException v) {
           throw new CandidateValidationExceptioncheck("validation error");
       }
 }
	
	
	@PatchMapping("/updateCandidate2")
	public void updateCandidate(@RequestBody Candidate cand ){
		candidateService.updateCandidate(cand);
	}
	
//	OM
	
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

 
 @GetMapping("/getjobstatus/{id}")
public List<Interview> findjob(@PathVariable  int id) {
   Candidate candidate = candidateService.findById(id);
   List<Interview> interviewStatus = candidate.getInterviewList();
   return  interviewStatus ;
}
  
	   

/*
 * *************OM start******************
 */
	
	@GetMapping("/getCandidateApplicationStatus/{id}")
	public List<Interview> getCandidateInterviews(@PathVariable  int id) throws CandidateNotFoundException {
		Candidate candidate = candidateService.findById(id);
		if (candidate==null) {
			throw new CandidateNotFoundException("id: "+id +"not found");
		}
		List<Interview> interviewStatus = candidate.getInterviewList();
		return  interviewStatus;
	}
	
	
	
	@GetMapping("/getAllCandidatesByJobId/{jobId}")
	public ResponseEntity<Set<Candidate>> getAllCandidatesByJobId(@PathVariable Integer jobId) throws NoSuchJobFoundException{
		Job job= null;
		try {
			job = jobService.findJobById(jobId);
			return new ResponseEntity<>(job.getCandidateSet(), HttpStatus.OK);
		} catch (NoSuchJobFoundException e) {
			throw e;
		}
	}
	
	@PostMapping("/candidateApplicationForJob")
	public ResponseEntity<String> candidateApplicationForJob(@RequestParam("candidateId") int candidateId, @RequestParam("jobId") int jobId) throws CandidateNotFoundException, NoSuchJobFoundException{
			  Candidate c = null;
			  Job j = null;
			  
			  c = candidateService.getCandidateById(candidateId);
			  if (c==null) {
				  throw new CandidateNotFoundException("Candidate with id : "+candidateId+" not found");
			  }
			  
			  j = jobService.getJobById(jobId);
			  if (j==null) {
				  throw new NoSuchJobFoundException(jobId);
			  }
			  
			  try {
			    Employer e = j.getCreatedBy();
			    
//			    j.getCandidateSet().add(c);
			    
			    Interview i = new Interview();
			    i.setCandidate(c);
			    i.setJob(j);
			    i.setEmployer(j.getCreatedBy());
			    i.setPreInterviewStatus(PreInterviewStatus.APPLIED);
			    i.setPostInterviewStatus(PostInterviewStatus.INVALID);
			    
			    c.getInterviewList().add(i);
			    c.getJobSet().add(j);
			    candidateService.updateCandidate(c);
			    
//			    jobDAO.save(j);
//			    interviewDAO.save(i);
			    
			    // now add the newly created interview to interviewlist of employer
//			    e.getInterviewList().add(i);
//			    employerDAO.save(e);
			  } catch (Exception e) {
			    
				System.out.println(e.getMessage());
				return new ResponseEntity<>("Candidate could not apply for this job", HttpStatus.OK);

			  }
			  
			  return new ResponseEntity<>("Candidate successfully applied for this job", HttpStatus.OK);
	}
	/*
	 * ********OM end***********
	 */
}