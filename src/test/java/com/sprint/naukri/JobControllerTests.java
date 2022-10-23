package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.controller.JobController;
import com.model.Employer;
import com.model.Job;

@SpringBootTest
public class JobControllerTests {
//  
//  
//  @Test
//  public void testGetAllJobs() {
//    JobController jc = new JobController();
//    HttpHeaders headers = new HttpHeaders();
//    HttpEntity<List<Job>> entity = new HttpEntity<List<Job>>(headers);
//    RestTemplate template = new RestTemplate();
//    List<Job> jobList = template.exchange("http://localhost:8081/getAllJobs", HttpMethod.GET, entity, List.class).getBody();
//   
////    List<Job> jobList = jc.getAllJobs().getBody();
//    assertNotNull(jobList);    
//  }
//  
//  @Test
//  public void testHello() {
//    JobController jc = new JobController();
//    assertEquals("Hello world", jc.hello());
//  }
//  
//  @Test
//  public void testGetJobsByIndustry() {
//    HttpHeaders headers = new HttpHeaders();
//    HttpEntity<List<Job>> entity = new HttpEntity<List<Job>>(headers);
//    RestTemplate template = new RestTemplate();
//    List<Job> jobList = template.exchange("http://localhost:8081/getJobsByIndustry/Backend", HttpMethod.GET, entity, List.class).getBody();
//    assertNotNull(jobList);
//  }
//  
//  @Test
//  public void testGetJobsByLocation() {
//    HttpHeaders headers = new HttpHeaders();
//    HttpEntity<List<Job>> entity = new HttpEntity<List<Job>>(headers);
//    RestTemplate template = new RestTemplate();
//    List<Job> jobList = template.exchange("http://localhost:8081/getJobsByLocation/Mumbai", HttpMethod.GET, entity, List.class).getBody();
//    assertNotNull(jobList);
//  }
//  
//  
//  @Test
//  public void testAddJob() {
//    Job j = new Job();
//    j.setJobDescription("This is a new job");
//    j.setJobStatus("OPEN");
//    j.setCandidateList(null);
//    j.setIndustry("Web");
//    HttpHeaders headers = new HttpHeaders();
//    HttpEntity<Job> entity = new HttpEntity<Job>(j, headers);
//    RestTemplate template = new RestTemplate();
//    String output = template.exchange("http://localhost:8081/addJob", HttpMethod.POST, entity, String.class).getBody();
//    assertEquals("Job added successfully", output);
//  }
//  
//  @Test
//  public void testGetAllEmployers() {
//    HttpHeaders headers = new HttpHeaders();
//    HttpEntity<List<Employer>> entity = new HttpEntity<List<Employer>>(headers);
//    RestTemplate template = new RestTemplate();
//    List<Employer> employerList = template.exchange("http://localhost:8081/getAllEmployers", HttpMethod.GET, entity, List.class).getBody();
//    assertNotNull(employerList);
//  }
//  
//  @Test
//  public void testAddEmployer() {
//    Employer e = new Employer();
//    e.setEmployerName("Name1");
//    e.setLocation("India");
//    HttpHeaders headers = new HttpHeaders();
////    headers.setAccept(new ArrayList<>()); //MediaType.APPLICATION_JSON
////    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//    HttpEntity<Employer> entity = new HttpEntity<Employer>(e, headers);
//    RestTemplate template = new RestTemplate();
//    String output = template.exchange("http://localhost:8081/addEmployer", HttpMethod.POST, entity, String.class).getBody();
//    assertEquals("Employer added successfully", output);
//  }
//  
//  

}
