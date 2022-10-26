package com.controller;

import java.util.List;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.dto.EmployerDTO;
import com.dto.RatingFeedbackDTO;



import com.enums.PostInterviewStatus;
import com.enums.PreInterviewStatus;
import com.exception.AllInterviewsNotCompletedException;
import com.exception.JobAlreadyClosedWithCandidateSelectedException;
import com.exception.NoEmployersException;
import com.exception.NoSuchEmployerFoundException;
import com.exception.NoSuchInterviewFoundException;
import com.exception.NoSuchJobFoundException;
import com.exception.feedbackException;
import com.exception.NotShortlistedException;
import com.model.Candidate;
import com.model.Employer;
import com.model.Interview;
import com.model.Job;
import com.model.Skill;
import com.service.CandidateService;
import com.service.EmployerService;
import com.service.InterviewService;
import com.service.JobService;

import io.swagger.annotations.ApiOperation;

@RestController
public class EmployerController {

    @Autowired
    EmployerService employerService;
    
    @Autowired
    JobService jobService;
    
    @Autowired
    InterviewService interviewService;
  
	@Autowired
	EmployerDAO employerDAO;

	@Autowired
	JobDAO jobDAO;

	@Autowired
	InterviewDAO interviewDAO;
	
	@Autowired
	CandidateService candidateService;
	
	

	@ApiOperation(value = "add an employer", notes = "Adding a new employer", nickname = "add-employer")
	@PostMapping("/addEmployer")
	public ResponseEntity<String> addEmployer(@RequestBody EmployerDTO employerDTO) {
		
	    employerService.addEmployer(employerDTO);
		return new ResponseEntity<>("Employer added successfully", HttpStatus.ACCEPTED);
	
	}

	@GetMapping("/getAllEmployers")
	public ResponseEntity<?> getAllEmployers() {
	  try {
	    return new ResponseEntity<>(employerService.findAllEmployers(), HttpStatus.OK);	    
	  } catch (NoEmployersException e) {
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	  }
	}

	@GetMapping("/closeJob")
	public ResponseEntity<?> selectCandidateAndCloseJob(
	  @RequestParam("candidateId") String selectedCandidateId,
	  @RequestParam("employerId") String employerId,
	  @RequestParam("jobId") String jobId
	) {
	  try {
  	    // employer will execute this method, so employerId is available
  	    // the job for which a candidate is selected should be provided
		  
  	    Employer employer = employerService.getEmployerById(Integer.parseInt(employerId));
  	    
  	    // the job corresponding to the interview
  	    Job job = jobService.getJobById(Integer.parseInt(jobId));
  	    
  	    // the candidate who is selected should be provided
  	    Candidate candidate = candidateService.getCandidateById(Integer.parseInt(selectedCandidateId));
  
  	    // find the interview conducted for THIS particular candidate for THIS particular job and employer
  	    Interview interview = interviewDAO.findByCandidateAndEmployerAndJob(candidate, employer, job);

	    // select the candidate
	    employerService.selectCandidateForJobAfterInterview(interview);
	    
	    // close the job
	    jobService.closeJob(job);

	    return new ResponseEntity<>("Candidate selected successfully", HttpStatus.OK);
	  } catch (NoSuchEmployerFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
      } catch (NoSuchJobFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
      } catch (AllInterviewsNotCompletedException exception) {
	    return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
	  } catch (JobAlreadyClosedWithCandidateSelectedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
      }
	}


	
	
	@PostMapping("/feedbackRating/{interviewId}")
	public ResponseEntity<String> feedbackRating(@PathVariable("interviewId") String id, @RequestBody RatingFeedbackDTO dto) throws feedbackException {
	  try {
	    Interview i = interviewService.getInterviewById(Integer.parseInt(id));
	      interviewService.provideEmployerFeedback(i, dto);
	      return new ResponseEntity<>("Feedback and rating by employer saved", HttpStatus.OK);
	  } catch (NoSuchInterviewFoundException e) {
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	  }
	}
	
	
	//Pawanesh
	
	@PatchMapping("/shortlistCandidate/{candidateId}/{employerId}/{jobId}")
	public ResponseEntity<String> updateShortlistedInterview(@PathVariable String candidateId, @PathVariable String employerId,@PathVariable String jobId) throws NumberFormatException, NoSuchJobFoundException, NoSuchEmployerFoundException
	{
		
		Candidate candidate = candidateService.getCandidateById(Integer.parseInt(candidateId));
		Job job = jobService.getJobById(Integer.parseInt(jobId));
		Employer employer = employerService.getEmployerById(Integer.parseInt(jobId));
		
		Interview interview = interviewDAO.findByCandidateAndEmployerAndJob(candidate, employer, job);
		
		interview.setPreInterviewStatus(PreInterviewStatus.SHORTLISTED);
		
		interviewDAO.save(interview);
		
		return new ResponseEntity<>("Successfully Updated Shortlisted candidate", HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("getAllShortListedCandidate/{jobId}")
	public ResponseEntity<List> notificationforShortlisted(@PathVariable String jobId) throws NumberFormatException, NoSuchJobFoundException 
	{
		Job job = jobService.findJobById(Integer.parseInt(jobId));
		List<Interview> interviews = interviewService.getAllShorttlistedCandidate(PreInterviewStatus.SHORTLISTED, job);
		
		List<Candidate> shortListCandidate = new ArrayList();
		for(Interview interview : interviews) {
			shortListCandidate.add(interview.getCandidate());
		}
		
		return new ResponseEntity<> (shortListCandidate, HttpStatus.OK);
		
	}
	
	@PatchMapping("/waitingCandidate/{candidateId}/{employerId}/{jobId}")
	public ResponseEntity<String> updateSelectedInterview(@PathVariable String candidateId, @PathVariable String employerId, @PathVariable String jobId)
			throws NumberFormatException, NoSuchJobFoundException, NoSuchEmployerFoundException, NotShortlistedException
	{
		
		Candidate candidate = candidateService.getCandidateById(Integer.parseInt(candidateId));
		Job job = jobService.getJobById(Integer.parseInt(jobId));
		Employer employer= employerService.getEmployerById(Integer.parseInt(employerId));
		
		Interview interview = interviewDAO.findByCandidateAndEmployerAndJob(candidate, employer, job);
		
		if(interview.getPreInterviewStatus() == PreInterviewStatus.INVALID) {
			
			throw new NotShortlistedException(candidate.getCandidateId());
		
		}
		if(interview.getPreInterviewStatus() == PreInterviewStatus.NOT_SHORTLISTED) {
			throw new NotShortlistedException("Candidate is not Shortlisted for this Job ");
		}
		
		interview.setPostInterviewStatus(PostInterviewStatus.WAITING);
		
		interviewDAO.save(interview);
		
		return new ResponseEntity<> ("Successfully Updated Waiting candidate",HttpStatus.OK);
		
	}
	
	
	@PatchMapping("/rejectedCandidate/{candidateId}/{employerId}/{jobId}")
	public ResponseEntity<String> updateSelectedInterview1(@PathVariable String candidateId, @PathVariable String employerId, @PathVariable String jobId) throws NumberFormatException, NoSuchJobFoundException, NoSuchEmployerFoundException
	{
		Candidate candidate = candidateService.getCandidateById(Integer.parseInt(candidateId));
		Job job = jobService.getJobById(Integer.parseInt(jobId));
		Employer employer= employerService.getEmployerById(Integer.parseInt(employerId));
		
		Interview interview = interviewDAO.findByCandidateAndEmployerAndJob(candidate, employer, job);
		
		interview.setPreInterviewStatus(PreInterviewStatus.NOT_SHORTLISTED);
		interview.setPostInterviewStatus(PostInterviewStatus.REJECTED);
		
		interviewDAO.save(interview);
		
		return new ResponseEntity<> ("Successfully Updated Rejected candidate",HttpStatus.OK);
		
	}
	
	
	
//	@GetMapping("getAllNotShortListedCandidate/{jobId}")
//	public ResponseEntity<List> notificationforNotShortListed(@PathVariable String jobId) throws NumberFormatException, NoSuchJobFoundException
//	{
//		Job job =jobService.findJobById(Integer.parseInt(jobId));
//		List<Interview> interviews = interviewService.getAllNotShortListedCandidate(PreInterviewStatus.NOT_SHORTLISTED,job);
//		
//		List<Candidate> notShortListed = new ArrayList();
//		for(Interview interview : interviews)
//		{
//			notShortListed.add(interview.getCandidate());
//		}
//		
//		return new ResponseEntity<> (notShortListed, HttpStatus.OK);
//	}
	
	//Pawanesh
	
	
	// the method for conducting interview should handle exceptions such that
	// if the candidate has not been short-listed, and even then if the employer
	// tries to conduct an interview with that candidate, it should throw error
	
	
	// before conducting even one interview for a particular job, there has to be
	// a check for the list of candidates who applied for that job. if even one
	// of them has pre-interview status as 'invalid', it means that the employer
	// has not yet decided whether to short-list him/her or not. In such case,
	// commencing interviews is not allowed and doing so should throw error
	
	
	// for both an employer and a candidate, participating in an interview
	// should happen only if they have provided rating and feedback to all
	// their previous interviews. If even one interview corresponding to
	// the employer or candidate is missing their rating or feedback,
	// conducting a new interview should throw error
	
	@GetMapping("/closeJobPostingForcefully")
	public ResponseEntity<?> closeJobPosting(@RequestParam("jobId") String jobId) {
	  // employer doesn't wish to interview candidates any longer, nor does he/she wish to hire anyone
	  // so the job should be closed without selecting any candidates, and they all are rejected
	  
	  try {
	    // find the job object
	    Job job = jobService.getJobById(Integer.parseInt(jobId));
	    
	    // close the job
	    jobService.closeJob(job);
	    
	    // reject all the candidates for this job
	    interviewService.rejectAllInterviewsForJob(job);
	    
	    return new ResponseEntity<>("Job posting forcefully closed", HttpStatus.OK);
	    
	  } catch (NoSuchJobFoundException e) {
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	  }
	}
		
}
