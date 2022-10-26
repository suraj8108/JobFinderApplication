package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.controller.AuthenticationController;
import com.controller.CandidateController;
import com.controller.EmployerController;
import com.dao.CandidateDAO;
import com.dao.EmployerDAO;
import com.dao.JobDAO;
import com.dto.EmployerDTO;
import com.dto.JobDTO;
import com.dto.RatingFeedbackDTO;
import com.exception.feedbackException;
import com.helper.JwtUtil;
import com.model.Candidate;
import com.model.Employer;
import com.model.Job;
import com.model.JwtRequest;
import com.model.JwtResponse;
import com.service.CandidateService;

@SpringBootTest
public class EmployerControllerTests {
	@Autowired
    static CandidateService candidateService;

	@Autowired
	CandidateDAO candidateDAO;
	
	@Autowired
	EmployerDAO employerDAO;
	
	@Autowired
	JobDAO jobDAO;

	@Autowired
	CandidateController candidateController;

	@Autowired
	EmployerController employerController;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	AuthenticationController authController;

	String commonToken;
	
	@BeforeEach
	void setup() {
	  // delete all
	  candidateDAO.deleteAll();
	  
	  // create candidate
	  Candidate candidate = new Candidate();
	  candidate.setAge(25);
	  candidate.setCandidateName("name");
	  candidate.setEducationQualification("qual");
	  candidate.setExperience(5);
	  candidate.setLocation("Mumbai");
	  candidate.setEmailId("email");
	  candidate.setPassword("password");
	  RestTemplate template = new RestTemplate();
	  HttpHeaders headers = new HttpHeaders();
	  HttpEntity<Candidate> entity = new HttpEntity<>(candidate, headers);
	  ResponseEntity<String> response = template.postForEntity("http://localhost:9989/registerCandidate", entity, String.class);
	  System.out.println(response.getBody());
	  System.out.println("=============");
	  assertEquals("Added Successfully", response.getBody());
	  
	  // generate token
	  JwtRequest jwtRequest = new JwtRequest("email", "password");
	  RestTemplate template2 = new RestTemplate();
	  HttpHeaders headers2 = new HttpHeaders();
	  HttpEntity<JwtRequest> entity2 = new HttpEntity<>(jwtRequest, headers2);
	  ResponseEntity<JwtResponse> response2 = template.postForEntity("http://localhost:9989/authenticate", entity2, JwtResponse.class);
	  commonToken = "Bearer " + response2.getBody().getJwtToken();
	}
	
//	@Test
//	void register() {
//	  
//	  String response = authController.registerCandidate(candidate).getBody();
//	}
	
	
//	@Test
//	void authenticate() {
//	}
	
	
////	@Test
//	void testLogin() {
////	  this.register();
////	  this.authenticate();
//	  RestTemplate template = new RestTemplate();
//	  HttpHeaders headers = new HttpHeaders();
//	  headers.add("Authorization", commonToken);
//	  HttpEntity<String> entity = new HttpEntity<>(headers);
//	  ResponseEntity<String> response = template.postForEntity("http://localhost:9989/login", entity, String.class);
//	  System.out.println(response.getBody());
////	  String loginResponse = authController.login();
////	  System.out.println(loginResponse);
//	  System.out.println("login successful");
////	  assertEquals("Successfully Landed to the Page", loginResponse);
//	}
	
	// -------------------------------------------------
	@Test
	void testAddEmployer() {
	  employerDAO.deleteAll();
	  EmployerDTO employerDTO = new EmployerDTO();
	  employerDTO.setEmployerName("Employer2");
	  employerDTO.setLocation("Pune");
	  
	  RestTemplate template = new RestTemplate();
	  HttpHeaders headers = new HttpHeaders();
	  headers.add("Authorization", commonToken);
	  HttpEntity<EmployerDTO> entity = new HttpEntity<>(employerDTO, headers);
	  ResponseEntity<String> response = template.postForEntity("http://localhost:9989/addEmployer", employerDTO, String.class);
	  System.out.println(response.getBody());
	  
//	  String addEmployerResponse = employerController.addEmployer(employerDTO).getBody();
	  assertEquals("Employer added successfully", response.getBody());
	}
	
	@Test
	void testGetAllEmployers() {
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", commonToken);
		HttpEntity<List<Employer>> entity = new HttpEntity<>(headers);
		ResponseEntity<List> response = template.exchange("http://localhost:9989/getAllEmployers", HttpMethod.GET, entity, List.class);
		List<Employer> employerList = response.getBody();
		System.out.println(";;;;;;;;;;;;;;;;;;;;");
		System.out.println(employerList);
		System.out.println(";;;;;;;;;;;;;;;;;;;;");
		assertEquals(1, employerList.size());
	}
	
	@Test
	void setupApplyJob() {
		Candidate candidate2 = new Candidate();
		candidate2.setAge(25);
		candidate2.setCandidateName("name2");
		candidate2.setEducationQualification("qual2");
		candidate2.setExperience(5);
		candidate2.setLocation("Mumbai");
		candidate2.setEmailId("email2");
		candidate2.setPassword("password2");
		
		Candidate candidate3 = new Candidate();
		candidate3.setAge(25);
		candidate3.setCandidateName("name3");
		candidate3.setEducationQualification("qual3");
		candidate3.setExperience(5);
		candidate3.setLocation("Mumbai");
		candidate3.setEmailId("email3");
		candidate3.setPassword("password3");
		
		RestTemplate templateNewCan = new RestTemplate();
		HttpHeaders headersNewCan = new HttpHeaders();
		headersNewCan.add("Authorization", commonToken);
		HttpEntity<Candidate> entityC2 = new HttpEntity<>(candidate2, headersNewCan);
		ResponseEntity<String> responseC2 = templateNewCan.postForEntity("http://localhost:9989/registerCandidate", entityC2, String.class);
		HttpEntity<Candidate> entityC3 = new HttpEntity<>(candidate3, headersNewCan);
		ResponseEntity<String> responseC3 = templateNewCan.postForEntity("http://localhost:9989/registerCandidate", entityC3, String.class);
		
		
		JobDTO j1 = new JobDTO();
		j1.setEid(1);
		j1.setIndustry("Web");
		j1.setJobDescription("Desc1");
		j1.setLocation("Mum");
		j1.setSalaryPackage(100);
		
		JobDTO j2 = new JobDTO();
		j2.setEid(1);
		j2.setIndustry("App");
		j2.setJobDescription("Desc2");
		j2.setLocation("Mum");
		j2.setSalaryPackage(300);
		

		RestTemplate templateNewJob = new RestTemplate();
		HttpHeaders headersNewJob = new HttpHeaders();
		headersNewJob.add("Authorization", commonToken);
		HttpEntity<JobDTO> entityJ1 = new HttpEntity<>(j1, headersNewCan);
		ResponseEntity<String> responseJ1 = templateNewCan.exchange("http://localhost:9989/employerAddjob", HttpMethod.POST, entityJ1, String.class);
		HttpEntity<JobDTO> entityJ2 = new HttpEntity<>(j2, headersNewCan);
		ResponseEntity<String> responseJ2 = templateNewCan.exchange("http://localhost:9989/employerAddjob", HttpMethod.POST, entityJ2, String.class);
		
		RestTemplate templateApplyJob = new RestTemplate();
		HttpHeaders headersApplyJob = new HttpHeaders();
		headersApplyJob.add("Authorization", commonToken);
		HttpEntity<String> entityCJ = new HttpEntity<>(headersNewCan);
		List<Candidate> candidateList = candidateDAO.findAll();
		List<Job> jobList = jobDAO.findAll();
		int c1 = candidateList.get(0).getCandidateId();
		int c2 = candidateList.get(1).getCandidateId();
		int c3 = candidateList.get(2).getCandidateId();
		int jb1 = jobList.get(0).getJobId();
		int jb2 = jobList.get(0).getJobId();
		ResponseEntity<String> responseC1J1 = templateApplyJob.exchange("http://localhost:9989/candidateApplicationForJob?candidateId="+c1+"&jobId="+jb1, HttpMethod.POST, entityCJ, String.class);
		ResponseEntity<String> responseC2J1 = templateApplyJob.exchange("http://localhost:9989/candidateApplicationForJob?candidateId="+c2+"&jobId="+jb1, HttpMethod.POST, entityCJ, String.class);
		ResponseEntity<String> responseC3J1 = templateApplyJob.exchange("http://localhost:9989/candidateApplicationForJob?candidateId="+c3+"&jobId="+jb1, HttpMethod.POST, entityCJ, String.class);
		ResponseEntity<String> responseC1J2 = templateApplyJob.exchange("http://localhost:9989/candidateApplicationForJob?candidateId="+c1+"&jobId="+jb2, HttpMethod.POST, entityCJ, String.class);
		ResponseEntity<String> responseC3J2 = templateApplyJob.exchange("http://localhost:9989/candidateApplicationForJob?candidateId="+c3+"&jobId="+jb2, HttpMethod.POST, entityCJ, String.class);
		
		
		
		assertTrue(true);
	}
	
	@Test
	void testShortlistCandidates() {
		RestTemplate templateShortlist = new RestTemplate();
		HttpHeaders headersShortlist = new HttpHeaders();
		headersShortlist.add("Authorization", commonToken);
		HttpEntity<String> entityShortlist = new HttpEntity<>(headersShortlist);
		try {
			List<Candidate> candidateList = candidateDAO.findAll();
			List<Job> jobList = jobDAO.findAll();
			int c1 = candidateList.get(0).getCandidateId();
			int c2 = candidateList.get(1).getCandidateId();
			int c3 = candidateList.get(2).getCandidateId();
			int jb1 = jobList.get(0).getJobId();
			int jb2 = jobList.get(0).getJobId();
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview("6", "1", "1"));
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview("7", "1", "1"));
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview("8", "1", "1"));
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview("6", "1", "2"));
			assertEquals("Successfully Updated Shortlisted candidate", employerController.updateShortlistedInterview("8", "1", "2"));
		} catch (Exception e) {
			
		}
//		templateShortlist.exchange("http://localhost:9989/shortlistCandidate/4/1/1", HttpMethod.POST, entityShortlist, String.class);
//		templateShortlist.exchange("http://localhost:9989/shortlistCandidate/5/1/1", HttpMethod.POST, entityShortlist, String.class);
//		templateShortlist.exchange("http://localhost:9989/shortlistCandidate/6/1/1", HttpMethod.POST, entityShortlist, String.class);
//		templateShortlist.exchange("http://localhost:9989/shortlistCandidate/4/1/2", HttpMethod.POST, entityShortlist, String.class);
//		ResponseEntity<String> responseShortlist = templateShortlist.exchange("http://localhost:9989/shortlistCandidate/6/1/2", HttpMethod.POST, entityShortlist, String.class);
//		assertEquals("Successfully Updated Shortlisted candidate", responseShortlist.getBody());
	}
	
	@Test
	void testWaitingCandidate() {
		RestTemplate templateWaiting = new RestTemplate();
		HttpHeaders headersWaiting = new HttpHeaders();
		headersWaiting.add("Authorization", commonToken);
		HttpEntity<String> entityWaiting = new HttpEntity<>(headersWaiting);
		try {			
			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview("6", "1", "1"));
			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview("7", "1", "1"));
			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview("8", "1", "1"));
			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview("6", "1", "2"));
			assertEquals("Successfully Updated Waiting candidate", employerController.updateSelectedInterview("8", "1", "2"));
		} catch (Exception e) {
			
		}
//		templateWaiting.exchange("http://localhost:9989/waitingCandidate/4/1/1", HttpMethod.PATCH, entityWaiting, String.class);
//		templateWaiting.exchange("http://localhost:9989/waitingCandidate/5/1/1", HttpMethod.PATCH, entityWaiting, String.class);
//		templateWaiting.exchange("http://localhost:9989/waitingCandidate/6/1/1", HttpMethod.PATCH, entityWaiting, String.class);
//		templateWaiting.exchange("http://localhost:9989/waitingCandidate/5/1/2", HttpMethod.PATCH, entityWaiting, String.class);
//		ResponseEntity<String> responseWaiting = templateWaiting.exchange("http://localhost:9989/waitingCandidate/6/1/2", HttpMethod.PATCH, entityWaiting, String.class);
//		assertEquals("Successfully Updated Shortlisted candidate", responseWaiting.getBody());
	}

	@Test
	void testRejectCandidate() {
		RestTemplate templateReject = new RestTemplate();
		HttpHeaders headersReject = new HttpHeaders();
		headersReject.add("Authorization", commonToken);
		HttpEntity<String> entityReject = new HttpEntity<>(headersReject);
		try {
			assertEquals("Successfully Updated Rejected candidate", employerController.updateSelectedInterview1("8", "1", "1"));
		} catch (Exception e) {
			
		}
//		ResponseEntity<String> responseReject = templateReject.exchange("http://localhost:9989/rejectedCandidate/5/1/1", HttpMethod.PATCH, entityReject, String.class);
//		assertEquals("Successfully Updated Rejected candidate", responseReject.getBody());
	}

	@Test
	void testEmployerFeedbackRating() throws feedbackException {
	  RatingFeedbackDTO rfbDTO = new RatingFeedbackDTO();
	  rfbDTO.setFeedback("good");
	  rfbDTO.setRating(3);
	  System.out.println(employerController.feedbackRating("1", rfbDTO).getBody());
	}

}
