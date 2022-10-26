package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dao.CandidateDAO;
import com.helper.JwtUtil;
import com.model.Candidate;
import com.model.Skill;
import com.model.JwtRequest;
import com.model.JwtResponse;
import com.model.Project;
import com.service.CandidateService;

@SpringBootTest
class AuthenticationTest {

//	@Autowired
//	CandidateDAO canDao;
//	
//	@Autowired
//	JwtUtil jwtUtil;
//
//	
//	static String commonToken;
//	
//	Candidate cand1 = new Candidate(); 
//	
//	@BeforeEach
//	void startConnection() {
//		cand1.setAge(22);
//		cand1.setCandidateName("yash");
//		cand1.setEducationQualification("B.tech");
//		cand1.setExperience(2);
//		cand1.setLocation("sfs");
//		cand1.setEmailId("suraj@gmail.com");
//		cand1.setPassword("121aaa");
//		
//		List<Project> pl = new ArrayList<>();
//		
//		
//		pl.add(new Project("yjbabv","happened"));
//		pl.add(new Project("slnacncs","happened"));
//		
//		Set<Skill> css = new HashSet<>() ;
//		css.add(new Skill("Tyons"));
//		css.add(new Skill("sflndskn"));
//		
//		cand1.setProjectList(pl);
//		cand1.setSkillSet(css);
//		
//		//Register Candidate
//		String url = "http://localhost:9989/registerCandidate";
//		RestTemplate restTemplate = new RestTemplate();
//		
//		HttpHeaders headers = new HttpHeaders();
//		
//		HttpEntity<Candidate> request = new HttpEntity<>(cand1, headers);
//		
//		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//		
//		System.out.println("Login =>" + response);
//		
//		//Get The Token to access all the APIS
//		JwtRequest jwtRequest = new JwtRequest("suraj@gmail.com", "121aaa");
//		
//		String url1 = "http://localhost:9989/authenticate";
//		RestTemplate restTemplate1 = new RestTemplate();
//		HttpHeaders headers1 = new HttpHeaders();
//		HttpEntity<JwtRequest> request1 = new HttpEntity<>(jwtRequest, headers1);
//		
//		ResponseEntity<JwtResponse> response1 = restTemplate1.postForEntity(url1, request1, JwtResponse.class);
//		
//		JwtResponse resp = response1.getBody();
//		
//		commonToken = "Bearer " + resp.getJwtToken();
//		
//	}
//	
////	@BeforeEach
////	void deleteAllEntries(){
////		canDao.deleteAll();
////	}
//	
//	@Test
//	void testAuthenticateCandidate() {
//		
//		JwtRequest jwtRequest = new JwtRequest("suraj@gmail.com", "121aaa");
//		
//		String url = "http://localhost:9989/authenticate";
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		HttpEntity<JwtRequest> request = new HttpEntity<>(jwtRequest, headers);
//		
//		ResponseEntity<JwtResponse> response = restTemplate.postForEntity(url, request, JwtResponse.class);
//		
//		JwtResponse resp = response.getBody();
//		System.out.println("Token Body =>" + response.getBody());
//		
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(jwtRequest.getUsername(), jwtUtil.extractUsername(resp.getJwtToken()));
//		
//	}
//	
////	@Test
////	void testRegisterCandidate() {
////		
////		String url = "http://localhost:9989/registerCandidate";
////		RestTemplate restTemplate = new RestTemplate();
////		
////		Candidate cand = new Candidate("Suraj", 12, "BE", 12, "Mumbai", "suraj@gmail.com", "121aaa");
////		
////		HttpHeaders headers = new HttpHeaders();
////		headers.add("Authorization", commonToken);
////		
////		HttpEntity<Candidate> request = new HttpEntity<>(cand, headers);
////		
////		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
////		System.out.println("Login =>" + response);
////		
////		assertEquals(HttpStatus.OK, response.getStatusCode());
////		
////	}
	
	
	

}
