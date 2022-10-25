package com.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

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
import com.exception.JobNotFoundException;
import com.exception.NoSuchInterviewFoundException;
import com.exception.NoSuchJobFoundException;
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
	CandidateDAO candidateDAO;
	
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
	
	@PatchMapping("/updateCandidate2")
	public void updateCandidate(@RequestBody Candidate cand ){
		candidateService.updateCandidate(cand);
	}
	
//	OM
	@PostMapping("/candidateApplication")
	public ResponseEntity<String> candidateApplication(@RequestParam("candidateId") String candidateId, @RequestParam("jobId") String jobId){
		try {
		    Candidate c = candidateService.getCandidateById(Integer.parseInt(candidateId));
		    Job j = jobService.getJobById(Integer.parseInt(jobId));		
		    Employer e = j.getCreatedBy();
		    
		    Set<Candidate> updatedCandSet = j.getCandidateSet();
		    updatedCandSet.add(c);
		    j.setCandidateSet(updatedCandSet);
		    
		    Interview i = new Interview();
		    i.setCandidate(c);
		    i.setJob(j);
		    i.setEmployer(j.getCreatedBy());
		    i.setPreInterviewStatus(PreInterviewStatus.INVALID);
		    i.setPostInterviewStatus(PostInterviewStatus.INVALID);
		    
		    jobDAO.save(j);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<>("Candidate successfully applied for this job", HttpStatus.OK);

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
			    
			    candidateService.checkIfAlreadySelectedByEmployer(c, e);
			    
			    // add the candidate to the candidate set of the job
			    j.getCandidateSet().add(c);
			    jobDAO.save(j);
			    
			    // create a new interview object unique to the employer, candidate, and job
			    Interview i = new Interview();
			    i.setCandidate(c);
			    i.setJob(j);
			    i.setEmployer(e);
			    i.setPreInterviewStatus(PreInterviewStatus.INVALID);
			    i.setPostInterviewStatus(PostInterviewStatus.INVALID);
			    interviewDAO.save(i);
			    
			    
			    // now add the newly created interview to the interview list of employer
			    e.getInterviewList().add(i);
			    employerDAO.save(e);
			    
			    // also add this interview to the interview list of the employer
			    c.getInterviewList().add(i);
                candidateDAO.save(c);
                
                // and add it to interview list of the job
                j.getInterviewList().add(i);
                jobDAO.save(j);
			    
			    return new ResponseEntity<>("Candidate successfully applied for this job", HttpStatus.OK);
			  } catch (Exception e) {
			    return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
			  }
			  
			  
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
	
	@GetMapping("/getAllCandidatesByExperience/{experience}")
	    public ResponseEntity<List<Candidate>> getAllCandidatesByExperience(@PathVariable Integer experience){
	    	return new ResponseEntity<>(candidateService.getAllCandidatesByExperience(experience),HttpStatus.OK);
		}
	    
	
    @GetMapping("/getAllCandidatesByQualification/{qualification}")
	    public ResponseEntity<List<Candidate>> getAllCandidatesByQualification(@PathVariable String qualification){
	    	return new ResponseEntity<>(candidateService.getAllCandidatesByQualification(qualification),HttpStatus.OK);
		}
	    
    @PostMapping("/getAllCandidatesBySkillSet")
	    public ResponseEntity<List<Candidate>> getAllCandidatesBySkillSet(@RequestBody String skills){
	    	return new ResponseEntity<>(candidateService.getAllCandidatesBySkillSet(skills), HttpStatus.OK);
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
			    i.setPreInterviewStatus(PreInterviewStatus.INVALID);
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