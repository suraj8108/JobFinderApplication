package com.sprint.naukri;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dao.CandidateDAO;
import com.dao.ProjectDAO;
import com.dao.SkillDAO;
import com.dto.ProfileDTO;
import com.dto.ProjectDTO;
import com.dto.SkillDTO;
import com.exception.CandidateNotFoundException;
import com.model.Candidate;
import com.model.JwtRequest;
import com.model.JwtResponse;
import com.model.Project;
import com.model.Skill;
import com.service.CandidateService;
import com.service.InterviewService;
import com.service.ProjectService;

@SpringBootTest

public class TransactionalCandidateControllerTest {
    
    @Autowired
    CandidateDAO candao ;
    @Autowired
    CandidateService candidateService;
    @Autowired
    SkillDAO sdao;
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectDAO projectdao;
    @Autowired
    SkillDAO skilldao; 
    @Autowired
    InterviewService interviewService;
    
    
    

    Candidate cand1=new Candidate();
   
    Candidate cand2 = new Candidate();
   
    List<ProjectDTO> pdtl = new ArrayList<>();
    Set<SkillDTO> csdts = new HashSet<>() ;
   String commonToken;
   //rest controller test
   @BeforeEach
   void setUp() throws Exception {
       candao.deleteAll();
     
       cand1.setAge(21);
       List<Project> projectList = new ArrayList<>();
       
       cand1.setProjectList(projectList);
       cand1.setCandidateName("Yash");
       cand1.setEmailId("ysvjnskvn");
       pdtl.add(new ProjectDTO("yjbabv","happened"));
       pdtl.add(new ProjectDTO("slnacncs","happened"));
       candao.save(cand1);
      ////
       cand2.setAge(22);
       cand2.setCandidateName("yash");
       cand2.setEducationQualification("B.tech");
       cand2.setExperience(2);
       cand2.setLocation("sfs");
       cand2.setEmailId("suraj@gmail.com");
       cand2.setPassword("121aaa");
       cand2.setProjectList(projectList);
    
       
       //Register Candidate
       String url = "http://localhost:9989/registerCandidate";
       RestTemplate restTemplate = new RestTemplate();
       
       HttpHeaders headers = new HttpHeaders();
       
       HttpEntity<Candidate> request = new HttpEntity<>(cand2, headers);
       
       ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
       
       //Get The Token to access all the APIS
       JwtRequest jwtRequest = new JwtRequest("suraj@gmail.com", "121aaa");
       
       String url1 = "http://localhost:9989/authenticate";
       RestTemplate restTemplate1 = new RestTemplate();
       HttpHeaders headers1 = new HttpHeaders();
       HttpEntity<JwtRequest> request1 = new HttpEntity<>(jwtRequest, headers1);
       
       ResponseEntity<JwtResponse> response1 = restTemplate1.postForEntity(url1, request1, JwtResponse.class);
       
       JwtResponse resp = response1.getBody();
      
       commonToken = "Bearer " + resp.getJwtToken();
       
       
       
   }
   @AfterEach
   void setdown() throws Exception {
       candao.deleteAll();
     
       
   }
    

  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    @Test
//    public void addProjectbyIdTest() throws URISyntaxException, CandidateNotFoundException{
//        
//      
//       candao.save(cand1);
//       System.out.println(cand1.getCandidateId());
//       System.out.println(candao.findById(cand1.getCandidateId()).get());
//       List<ProjectDTO> pdt = new ArrayList<>();
//       pdt.add(new ProjectDTO("kjsnv","skjnvk"));
//       Candidate c = new Candidate();
//       c = candao.findByCandidateName("yashss");
//       System.out.println(c);
//        
//        RestTemplate restTemplate = new RestTemplate();
//        
//        HttpHeaders headers=new HttpHeaders();
//         
//         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//         
//         HttpEntity<List<ProjectDTO>> entity=new HttpEntity<>(pdt,headers);
// 
//        ResponseEntity<String> result = restTemplate.exchange("http://localhost:9989/addProjectById/"+""+c.getCandidateId(),
//                HttpMethod.POST,entity,String.class);
//         System.out.println(cand1);
//       
//       Assertions.assertEquals(202, result.getStatusCodeValue());
//       Assertions.assertEquals("Project added successfully", result.getBody());
//   
//  }




}

/*
 * suraj method without transactional
 ProfileDTO dto = new ProfileDTO();
        dto.setAge(22);
        dto.setCandidateName("yashss");
        dto.setEducationQualification("extra");
        dto.setEmailId("emailid");
        dto.setExperience(2);
        dto.setLocation("here");
        dto.setPassword("password");
        
        dto.setProjectDTOList(pdtl);
        
        dto.setSkillDTOSet(csdts);
        
      String url  = "http://localhost:9989/addProfile";
      RestTemplate template = new RestTemplate();
      
      HttpHeaders headers = new HttpHeaders();
      HttpEntity<ProfileDTO> entity=new HttpEntity(dto,headers);

      String response = template.exchange(url,  HttpMethod.POST, entity,String.class).getBody();
      Assertions.assertEquals("Candidate added successfully", response);
       Candidate c= candao.findByCandidateName("yashss");
      String url2 = "http://localhost:9989/addProjectById/"+c.getCandidateId();
      
      HttpEntity<ProjectDTO> entity=new HttpEntity(dto,headers);

      ResponseEntity<String> result = template.exchange(url, null)
      */
