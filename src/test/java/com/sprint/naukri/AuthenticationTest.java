package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.*;

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

import com.dao.CandidateDao;
import com.helper.JwtUtil;
import com.model.Candidate;
import com.model.JwtRequest;
import com.model.JwtResponse;

@SpringBootTest
class AuthenticationTest {

	@Autowired
	CandidateDao canDao;
	
	@Autowired
	JwtUtil jwtUtil;
	
	static String commonToken;
	@BeforeAll
	static void startConnection() {
		//Get The Token to access all the APIS
		JwtRequest jwtRequest = new JwtRequest("suraj@gmail.com", "121aaa");
		
		String url = "http://localhost:9989/authenticate";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<JwtRequest> request = new HttpEntity<>(jwtRequest, headers);
		
		ResponseEntity<JwtResponse> response = restTemplate.postForEntity(url, request, JwtResponse.class);
		
		JwtResponse resp = response.getBody();
		
		commonToken = "Bearer " + resp.getJwtToken();
		
		System.out.println("Testing Started");
		System.out.println("Common Token => " + commonToken );
	}
	
//	@BeforeEach
//	void deleteAllEntries(){
//		canDao.deleteAll();
//	}
	
	@Test
	void testAuthenticateCandidate() {
		
		JwtRequest jwtRequest = new JwtRequest("suraj@gmail.com", "121aaa");
		
		String url = "http://localhost:9989/authenticate";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<JwtRequest> request = new HttpEntity<>(jwtRequest, headers);
		
		ResponseEntity<JwtResponse> response = restTemplate.postForEntity(url, request, JwtResponse.class);
		
		JwtResponse resp = response.getBody();
		System.out.println("Token Body =>" + response.getBody());
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(jwtRequest.getUsername(), jwtUtil.extractUsername(resp.getJwtToken()));
		
	}
	
//	@Test
//	void testRegisterCandidate() {
//		
//		String url = "http://localhost:9989/registerCandidate";
//		RestTemplate restTemplate = new RestTemplate();
//		
//		Candidate cand = new Candidate("Suraj", 12, "BE", 12, "Mumbai", "suraj@gmail.com", "121aaa");
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", commonToken);
//		
//		HttpEntity<Candidate> request = new HttpEntity<>(cand, headers);
//		
//		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//		System.out.println("Login =>" + response);
//		
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		
//	}
	
	
	

}
