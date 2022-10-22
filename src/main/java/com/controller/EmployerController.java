package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.EmployerDAO;
import com.dao.InterviewDAO;
import com.dao.JobDAO;
import com.dto.NewEmployerDTO;
import com.dto.RatingFeedbackDTO;
import com.enums.InterviewStatus;
import com.enums.JobPostStatus;
import com.enums.JobStatus;
import com.exceptions.AllInterviewsNotCompletedException;
import com.exceptions.JobAlreadyClosedWithCandidateSelectedException;
import com.exceptions.NoEmployersException;
import com.exceptions.NoSuchEmployerFoundException;
import com.exceptions.NoSuchInterviewFoundException;
import com.exceptions.NoSuchJobFoundException;
import com.model.Employer;
import com.model.Interview;
import com.model.Job;
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

	@ApiOperation(value = "add an employer", notes = "Adding a new employer", nickname = "add-employer")
	@PostMapping("/addEmployer")
	public ResponseEntity<String> addEmployer(@RequestBody NewEmployerDTO employerDTO) {
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
	public ResponseEntity<?> selectCandidateAndCloseJob(@RequestParam("candidate") String candidateId, @RequestParam("job") String jobId) {
	  try {
  	    // employer will execute this method, so employerId is available
  	    // the job for which a candidate is selected should be provided
  	    Employer e = employerService.getEmployerById(1);
  	    Job j = jobService.getJobById(Integer.parseInt(jobId));
  	    // the candidate who is selected should be provided
  	    String candidateIdStr = "C1";
  
  	    // find the interview conducted for THIS particular candidate for THIS particular job
  	    Interview interview = interviewDAO.findByCandidateAndEmployerAndJob(candidateIdStr, e, j);

	    // select the candidate
	    employerService.selectCandidateForJobAfterInterview(interview);
	    
	    // close the job
	    jobService.closeJob(j);

	    return new ResponseEntity<>("candidate selected successfully", HttpStatus.OK);
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
	public ResponseEntity<String> feedbackRating(@PathVariable("interviewId") String id, @RequestBody RatingFeedbackDTO dto) {
	  try {
	    Interview i = interviewService.getInterviewById(Integer.parseInt(id));
	      interviewService.provideEmployerFeedback(i, dto);
	      return new ResponseEntity<>("Feedback and rating by employer saved", HttpStatus.OK);
	  } catch (NoSuchInterviewFoundException e) {
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	  }
	}
	
	
	// to be removed from here
	@PostMapping("/candidateAppliesForJob")
	public ResponseEntity<String> candidateAppliesForJob(@RequestParam("candidateId") String candidateId, @RequestParam("jobId") String jobId) {
	  
	  try {	    
	    // assuming the candidate is already created,
	    // find the candidate, employer and job using the ids
	    // Candidate c = find(candidateId);
	    Job j = jobService.getJobById(Integer.parseInt(jobId));
	    Employer e = j.getCreatedBy();
	    
	    
	    // first, add the candidate to the candidatelist of that job object
	    //	  j.getCandidateList().add(candidateId);
	    if (j.getCandidateList() == null) j.setCandidateList(candidateId);
	    else j.setCandidateList(j.getCandidateList() + "," + candidateId);
	    // then, add the job to the appliedlist of that candidate object
	    // then, create a new interview object with that employer, candidate, job
	    Interview i = new Interview();
	    i.setCandidate(candidateId);
	    i.setJob(j);
	    i.setEmployer(j.getCreatedBy());
	    i.setJobStatus(JobStatus.INVALID);
	    i.setInterviewStatus(InterviewStatus.INVALID);
	    jobDAO.save(j);
	    interviewDAO.save(i);
	    
	    // now add the newly created interview to interviewlist of employer
	    e.getInterviews().add(i);
	    employerDAO.save(e);
	  } catch (Exception e) {
	    
	  }
	  
	  
	  return new ResponseEntity<>("Candidate successfully applied for this job", HttpStatus.OK);
	}
}
