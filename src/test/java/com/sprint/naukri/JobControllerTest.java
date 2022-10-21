package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.controller.JobController;
import com.model.Job;

@SpringBootTest
public class JobControllerTest {
  
  
  @Test
  public void testGetAllJobs() {
    JobController jc = new JobController();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<List<Job>> entity = new HttpEntity<List<Job>>(headers);
    RestTemplate template = new RestTemplate();
    List<Job> jobList = template.exchange("http://localhost:9989/getalljobs", HttpMethod.GET, entity, List.class).getBody();
    assertNotNull(jobList);    
  }
  
  @Test
  public void testAddJob() {
    Job j = new Job();
    j.setJobDescription("This is a new job");
    j.setStatus("active");
    j.setCandidateSet(null);
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<Job> entity = new HttpEntity<Job>(j, headers);
    RestTemplate template = new RestTemplate();
    String output = template.exchange("http://localhost:9989/addjob", HttpMethod.POST, entity, String.class).getBody();
    assertEquals("job added successfuly", output);
  }
  
	@Test
	public String updateJobTest() {
		Job job = new Job();
		job.setJobId(1);
		job.setJobDescription("tech");
		job.setStatus("active");
		
		RestTemplate restTemplate = new RestTemplate();
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		
//		HttpEntity<Job> entity = new HttpEntity<>(job, headers);
//		
//		ResponseEntity<String> res = restTemplate.exchange("https://localhost:9989/updatejob", 
//				HttpMethod.PATCH, entity, String.class);
//	
//		Assertions.assertEquals(202, res.getStatusCodeValue());
//		Assertions.assertEquals("job updated successfuly", res.getBody());
//	}
//	
//	{
		HttpHeaders headers=new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      
      HttpEntity<Job> entity=new HttpEntity(job,headers);
  
      RestTemplate template=new RestTemplate();
      template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
      return template.exchange("http://localhost:9989/updateitem",
              HttpMethod.PATCH,entity,String.class).getBody();

}
}