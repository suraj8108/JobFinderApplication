package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;

import com.controller.AuthenticationController;
import com.controller.CandidateController;
import com.controller.EmployerController;
import com.controller.JobController;
import com.dao.CandidateDAO;
import com.dao.EmployerDAO;
import com.dao.InterviewDAO;
import com.dao.JobDAO;
import com.dto.EmployerDTO;
import com.dto.JobDTO;
import com.dto.RatingFeedbackDTO;
import com.model.Candidate;
import com.model.Employer;
import com.model.Interview;
import com.model.Job;
import com.model.JwtRequest;
import com.model.JwtResponse;

@SpringBootTest
public class EmployerControllerMethodTests {
	
	@Autowired
	CandidateDAO candidateDAO;
	
	@Autowired
	EmployerDAO employerDAO;
	
	@Autowired
	JobDAO jobDAO;
	
	@Autowired
	InterviewDAO interviewDAO;
	
	@Autowired
	AuthenticationController authController;
	
	@Autowired
	EmployerController employerController;
	
	@Autowired
	JobController jobController;
	
	@Autowired
	CandidateController candidateController;
	
	String commonToken;
	
	void setup() {
		// delete all
//		candidateDAO.deleteAll();
//		employerDAO.deleteAll();
//		jobDAO.deleteAll();
//		interviewDAO.deleteAll();
		
		// create candidate
		Candidate candidate = new Candidate();
		candidate.setAge(25);
		candidate.setCandidateName("name");
		candidate.setEducationQualification("qual");
		candidate.setExperience(5);
		candidate.setLocation("Mumbai");
		candidate.setEmailId("email");
	    candidate.setPassword("password");
	    ResponseEntity<String> response = authController.registerCandidate(candidate);
	    
	    // generate token
	    JwtRequest jwtRequest = new JwtRequest("email", "password");
	    try {
	    	JwtResponse jwtResponse = authController.authenticateCand(jwtRequest);
	    	commonToken = "Bearer " + jwtResponse.getJwtToken();
			System.out.println(commonToken);
	    } catch (Exception e) {
	    	
	    }
	    
	    
	}
	@Test
	void test1() {
		this.setup();
		System.out.println(commonToken);
		assertTrue(true);
	}
	
	@Test
	void testAddEmployer() {
		// generate token
	    JwtRequest jwtRequest = new JwtRequest("email", "password");
	    try {
	    	JwtResponse jwtResponse = authController.authenticateCand(jwtRequest);
	    	commonToken = "Bearer " + jwtResponse.getJwtToken();
			System.out.println(commonToken);
	    } catch (Exception e) {
	    	
	    }
		employerDAO.deleteAll();
		EmployerDTO employerDTO = new EmployerDTO();
		employerDTO.setEmployerName("Employer1");
		employerDTO.setLocation("Pune");
		
		ResponseEntity<String> response = employerController.addEmployer(employerDTO);
		assertEquals("Employer added successfully", response.getBody());
	}
	
	@Test
	void testGetAllEmployers() {
		// generate token
	    JwtRequest jwtRequest = new JwtRequest("email", "password");
	    try {
	    	JwtResponse jwtResponse = authController.authenticateCand(jwtRequest);
	    	commonToken = "Bearer " + jwtResponse.getJwtToken();
			System.out.println(commonToken);
	    } catch (Exception e) {
	    	
	    }
		employerDAO.deleteAll();
		EmployerDTO employerDTO1 = new EmployerDTO();
		employerDTO1.setEmployerName("Employer1");
		employerDTO1.setLocation("Mumbai");
		
		EmployerDTO employerDTO2 = new EmployerDTO();
		employerDTO2.setEmployerName("Employer2");
		employerDTO2.setLocation("Pune");
		
		employerController.addEmployer(employerDTO1);
		employerController.addEmployer(employerDTO2);
		System.out.println(commonToken);
		
		List<Employer> employerList = (List<Employer>) employerController.getAllEmployers().getBody();
		assertEquals(2, employerList.size());
	}
	
	@Test
	void setupApplyJob() {
		// generate token
	    JwtRequest jwtRequest = new JwtRequest("email", "password");
	    try {
	    	JwtResponse jwtResponse = authController.authenticateCand(jwtRequest);
	    	commonToken = "Bearer " + jwtResponse.getJwtToken();
			System.out.println(commonToken);
	    } catch (Exception e) {
	    	
	    }
		System.out.println(commonToken);
		Candidate can2 = new Candidate();
		can2.setAge(25);
		can2.setCandidateName("name2");
		can2.setEducationQualification("qual2");
		can2.setExperience(5);
		can2.setLocation("Mumbai");
		can2.setEmailId("email2");
		can2.setPassword("password2");
		
		Candidate can3 = new Candidate();
		can3.setAge(25);
		can3.setCandidateName("name3");
		can3.setEducationQualification("qual3");
		can3.setExperience(5);
		can3.setLocation("Mumbai");
		can3.setEmailId("email3");
		can3.setPassword("password3");
		
		authController.registerCandidate(can2);
		authController.registerCandidate(can3);
		
		JobDTO job1 = new JobDTO();
		job1.setEid(2);
		job1.setIndustry("Web");
		job1.setJobDescription("Desc1");
		job1.setLocation("Mum");
		job1.setSalaryPackage(100);
		
		JobDTO job2 = new JobDTO();
		job2.setEid(2);
		job2.setIndustry("App");
		job2.setJobDescription("Desc2");
		job2.setLocation("Mum");
		job2.setSalaryPackage(300);
		
//		jobController.addJobManually(job1);
//		jobController.addJobManually(job2);
		RestTemplate templateNewJob = new RestTemplate();
		HttpHeaders headersNewJob = new HttpHeaders();
		System.out.println(commonToken);
		headersNewJob.add("Authorization", commonToken);
		HttpEntity<JobDTO> entityJ1 = new HttpEntity<>(job1, headersNewJob);
		ResponseEntity<String> responseJ1 = templateNewJob.exchange("http://localhost:8081/employerAddjob", HttpMethod.POST, entityJ1, String.class);
		HttpEntity<JobDTO> entityJ2 = new HttpEntity<>(job2, headersNewJob);
		ResponseEntity<String> responseJ2 = templateNewJob.exchange("http://localhost:8081/employerAddjob", HttpMethod.POST, entityJ2, String.class);
		
		List<Employer> employerList = employerDAO.findAll();
		int e1 = employerList.get(0).getEmployerId();
		List<Candidate> candidateList = candidateDAO.findAll();
		List<Job> jobList = jobDAO.findAll();
		
		int c1 = candidateList.get(0).getCandidateId();
		int c2 = candidateList.get(1).getCandidateId();
		int c3 = candidateList.get(2).getCandidateId();
		int jb1 = jobList.get(0).getJobId();
		int jb2 = jobList.get(1).getJobId();
		System.out.println(jobList);
		RestTemplate templateApplyJob = new RestTemplate();
		HttpHeaders headersApplyJob = new HttpHeaders();
		headersApplyJob.add("Authorization", commonToken);
		HttpEntity<String> entityCJ = new HttpEntity<>(headersApplyJob);
		ResponseEntity<String> responseC1J1 = templateApplyJob.exchange("http://localhost:8081/candidateApplicationForJob?candidateId="+c1+"&jobId="+jb1, HttpMethod.POST, entityCJ, String.class);
		ResponseEntity<String> responseC2J1 = templateApplyJob.exchange("http://localhost:8081/candidateApplicationForJob?candidateId="+c2+"&jobId="+jb1, HttpMethod.POST, entityCJ, String.class);
		ResponseEntity<String> responseC3J1 = templateApplyJob.exchange("http://localhost:8081/candidateApplicationForJob?candidateId="+c3+"&jobId="+jb1, HttpMethod.POST, entityCJ, String.class);
		ResponseEntity<String> responseC1J2 = templateApplyJob.exchange("http://localhost:8081/candidateApplicationForJob?candidateId="+c1+"&jobId="+jb2, HttpMethod.POST, entityCJ, String.class);
		ResponseEntity<String> responseC3J2 = templateApplyJob.exchange("http://localhost:8081/candidateApplicationForJob?candidateId="+c3+"&jobId="+jb2, HttpMethod.POST, entityCJ, String.class);
		try {
//			candidateController.candidateApplicationForJob(candidateList.get(0).getCandidateId(), jobList.get(0).getJobId());
//			candidateController.candidateApplicationForJob(candidateList.get(1).getCandidateId(), jobList.get(0).getJobId());
//			candidateController.candidateApplicationForJob(candidateList.get(2).getCandidateId(), jobList.get(0).getJobId());
//			candidateController.candidateApplicationForJob(candidateList.get(0).getCandidateId(), jobList.get(1).getJobId());
//			candidateController.candidateApplicationForJob(candidateList.get(2).getCandidateId(), jobList.get(1).getJobId());
			
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview(Integer.toString(c1), Integer.toString(e1), Integer.toString(jb1)).getBody());
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview(Integer.toString(c2), Integer.toString(e1), Integer.toString(jb1)).getBody());
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview(Integer.toString(c3), Integer.toString(e1), Integer.toString(jb1)).getBody());
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview(Integer.toString(c1), Integer.toString(e1), Integer.toString(jb2)).getBody());
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview(Integer.toString(c3), Integer.toString(e1), Integer.toString(jb2)).getBody());
			
			
			// waiting
			MockHttpServletRequest request = new MockHttpServletRequest();
			//request.addHeader("Authorization", commonToken);
			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview(request ,Integer.toString(c1), Integer.toString(jb1)).getBody());
			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview(request, Integer.toString(c2), Integer.toString(jb1)).getBody());
//			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview(Integer.toString(c2), Integer.toString(e1), Integer.toString(jb1)));
//			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview(Integer.toString(c3), Integer.toString(e1), Integer.toString(jb1)));
//			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview(Integer.toString(c1), Integer.toString(e1), Integer.toString(jb1)));
//			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview(Integer.toString(c3), Integer.toString(e1), Integer.toString(jb1)));

			// rejecting
			assertEquals("Successfully Updated Rejected candidate", employerController.updateSelectedInterview1(Integer.toString(c3), Integer.toString(e1), Integer.toString(jb1)).getBody());
			
			// selecting
			
			assertEquals("Candidate selected successfully", employerController.selectCandidateAndCloseJob(Integer.toString(c1), Integer.toString(e1), Integer.toString(jb1)).getBody());

			// feedback
			RatingFeedbackDTO rfbDTO = new RatingFeedbackDTO();
			rfbDTO.setFeedback("good");
			rfbDTO.setRating(3);
			assertEquals("Feedback and rating by employer saved", employerController.feedbackRating("1", rfbDTO));
			Interview newInterview = interviewDAO.getById(1);
			System.out.println(newInterview);
			assertEquals("good", newInterview.getEmployerFeedback());
			assertEquals(3, newInterview.getEmployerRating());
			
			employerController.closeJobPosting(Integer.toString(jb2));
		} catch (Exception e) {
		}
		
		
		
		assertTrue(true);
	}
	
	@Test
	void testEmployerFeedback() {
	}
}
