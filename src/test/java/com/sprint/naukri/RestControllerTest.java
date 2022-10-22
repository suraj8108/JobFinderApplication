package com.sprint.naukri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.dao.CandidateDao;
import com.model.Candidate;
import com.model.Project;
import com.service.CandidateService;

@SpringBootTest
public class RestControllerTest {
	@Autowired
	CandidateDao candao ;
	@Autowired
	CandidateService service;
	
	//Candidate c = new Candidate("", 0, 0, baseUrl, baseUrl, null, null);
	
	
	

	//rest controller test
	@BeforeEach
	void setUp() throws Exception {
	
		candao.deleteAll();
	}
	@AfterEach
	void setdown() throws Exception {
		candao.deleteAll();
		candao.flush();
	}
	
//	@Test
//	public void addProfiletest() throws URISyntaxException 
//	{
//	    RestTemplate restTemplate = new RestTemplate();
//	     
////	    final String baseUrl = "http://localhost:9989/addcandidate";
////	    URI uri = new URI(baseUrl);
//	    
//		
//		
//		HttpHeaders headers=new HttpHeaders();
//	     
//		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	     
//		 HttpEntity entity=new HttpEntity(c,headers);
//	 
//		ResponseEntity<String> result = restTemplate.exchange("http://localhost:9989/addprofile",
//                HttpMethod.POST,entity,String.class);
//	     
//	    //Verify request succeed
//	   Assertions.assertEquals(202, result.getStatusCodeValue());
//	   Assertions.assertEquals("Candidate added successfully", result.getBody());
//	}

	
	
	
	@Test
	public void getallcandidates() throws URISyntaxException 
	{
	    RestTemplate restTemplate = new RestTemplate();
	   
	    
		HttpHeaders headers=new HttpHeaders();
	     
		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     
		 HttpEntity<List<Candidate>> entity=new HttpEntity<>(headers);
	 
		ResponseEntity<List>  result = restTemplate.exchange("http://localhost:9989/getallcandidates",
                HttpMethod.GET,entity,List.class);
		
		List<Candidate> temp = candao.findAll();
		
		//Assertions.assertEquals(temp,result.getBody());
		
		//assertTrue(temp.equals(result.getBody()));
		
		Assertions.assertEquals(temp.toString(),result.getBody().toString());
		
	    //Verify request succeed
	    Assertions.assertEquals(200, result.getStatusCodeValue());
	 
	 		
	}
	
//	@Test
//	public void updateCandidateTest() throws URISyntaxException 
//	{
//	    
//		Candidate cand1 =new Candidate( "yash", 22, 1, "hell", "b.tech", new String[] {"Project1","Project2"} );
//		candao.save(cand1);
//		Candidate cand2 =new Candidate( cand1.getCandidateId(),"yashascsa", 22, 1, "hell", "b.tech", new String[] {"Project1","Project2"} );
//
//		RestTemplate template=new RestTemplate();
//        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//	   
//	    
//		HttpHeaders headers=new HttpHeaders();
//	     
//		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	     
//		 HttpEntity<Candidate> entity=new HttpEntity<>(cand2,headers);
//	 
//		ResponseEntity<String>  result = template.exchange("http://localhost:9989/updatecandidate",
//                HttpMethod.PATCH,entity,String.class);
//		
//		
//		
//		
//		
//	    Assertions.assertEquals(202, result.getStatusCodeValue());
//		   Assertions.assertEquals("Candidate updated successfully", result.getBody());
//
//	 		
//	}
//	
//	
//	@Test
//	public void deleteCandidateTest() throws URISyntaxException 
//	{
//	    
//		Candidate cand1 =new Candidate( "yash", 22, 1, "hell", "b.tech", new String[] {"Project1","Project2"} );
//		candao.save(cand1);
//		Candidate cand2 =new Candidate( cand1.getCandidateId(),"yash", 22, 1, "hell", "b.tech", new String[] {"Project1","Project2"} );
//
//		RestTemplate restTemplate = new RestTemplate();
//	   
//	    
//		HttpHeaders headers=new HttpHeaders();
//	     
//		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	     
//		 HttpEntity<Candidate> entity=new HttpEntity<>(cand2,headers);
//	 
//		ResponseEntity<String>  result = restTemplate.exchange("http://localhost:9989/deletecandidate",
//                HttpMethod.DELETE,entity,String.class);
//		
//		
//		
//		//Assertions.assertEquals(temp,result.getBody());
//		
//		//assertTrue(temp.equals(result.getBody()));
//		
//		//Assertions.assertEquals(temp.toString(),result.getBody().toString());
//		
//	    //Verify request succeed
//		
//	    Assertions.assertEquals(202, result.getStatusCodeValue());
//		   Assertions.assertEquals("Candidate delete successfully", result.getBody());
//
//	 		
//	}
}
