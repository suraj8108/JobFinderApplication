package com.sprint.naukri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dao.JobDao;
import com.model.Job;
import com.model.JobSkillSet;
import com.model.Status;




@SpringBootTest
public class Test1 {
	
	@Test
	public void addJobTest() throws URISyntaxException 
	{
		
		List<JobSkillSet> status1=new ArrayList<>();
		JobSkillSet s1= new JobSkillSet("Web Developer");
		JobSkillSet s2= new JobSkillSet("App Developer");
		JobSkillSet s3= new JobSkillSet("Machine Learning");
		JobSkillSet s4 =new JobSkillSet("Full Stack");
		
	    RestTemplate restTemplate = new RestTemplate();
	    status1.add(s1);
	    status1.add(s2);
	    status1.add(s3);
	    status1.add(s4);
		Job j= new Job(2, Status.Open, status1);
	
		HttpHeaders headers=new HttpHeaders();
	     
		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     
		 	HttpEntity entity=new HttpEntity(j,headers);
		 
			ResponseEntity<String> result = restTemplate.exchange("http://localhost:9989/addjob",
	                HttpMethod.POST,entity,String.class);
		     
		    //Verify request succeed
		   Assertions.assertEquals(200, result.getStatusCodeValue());
		   Assertions.assertEquals("Job added successfully", result.getBody());
		}
	
	@Autowired
	JobDao jobdao;
	
	@Test
	public void getalljobsTest() throws URISyntaxException 
	{
		List<String> options = Arrays.asList("Web Developer");
		
	    RestTemplate restTemplate = new RestTemplate();
	
		HttpHeaders headers=new HttpHeaders();
		
		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 HttpEntity<List<String>> entity=new HttpEntity<>(options, headers);
		 
			ResponseEntity<List>  result = restTemplate.exchange("http://localhost:9989/getall",
	                HttpMethod.POST,entity,List.class);
			String category = null;
			
			System.out.println(result);
		     
		    //Verify request succeed
		   Assertions.assertEquals(200, result.getStatusCodeValue());
		  
		//Assertions.assertEquals(resultant, result.getBody());
		}
	
	

}
