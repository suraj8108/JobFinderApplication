package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dao.CandidateDAO;
import com.dao.ProjectDAO;
import com.dao.SkillDAO;
import com.dto.ProfileDTO;
import com.dto.ProjectDTO;
import com.dto.RatingFeedbackDTO;
import com.dto.SkillDTO;
import com.enums.PostInterviewStatus;
import com.enums.PreInterviewStatus;
import com.exception.CandidateNotFoundException;
import com.exception.CandidateValidationExceptioncheck;
import com.exception.ProjectNotFoundException;
import com.exception.feedbackException;
import com.model.Candidate;
import com.model.Interview;
import com.model.Project;
import com.model.Skill;
import com.service.CandidateService;
import com.service.InterviewService;
import com.service.ProjectService;


@SpringBootTest

public class CandidateServiceTest {
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
	InterviewService interviewService;
	
	
	 

	 Candidate cand1 = new Candidate();
	 List<Project> pl = new ArrayList<>();
	 Set<Skill> css = new HashSet<>() ;
	 List<ProjectDTO> pdtl = new ArrayList<>();
	 Set<SkillDTO> csdts = new HashSet<>() ;
	//rest controller test
	@BeforeEach
	public  void setUp() throws Exception {
	    candao.deleteAll();
	    sdao.deleteAll();
	         
	        cand1.setAge(22);
	        cand1.setCandidateName("yashss");
	        cand1.setEducationQualification("extra");
	        cand1.setExperience(2);
	        cand1.setLocation("here");
	        cand1.setEmailId("emailid");
	        cand1.setPassword("password");
	        
	        
	        
	        
	        Project p1 = new Project("yjbabv","happened");
	        p1.setCandidate(cand1);
	        Project p2 = new Project("slnacncs","happened");
	        p2.setCandidate(cand1);
	        pl.add(p1);
	        pl.add(p2);
	        cand1.setProjectList(pl);
	       
	        Skill s1 = new Skill("Json");
	        Skill s2 = new Skill("Java");
	        Set<Candidate> dummySet = new HashSet<>();
	        dummySet.add(cand1);
	       
	        css.add(s1);
	        css.add(s2);
	        cand1.setSkillSet(css);
	  
	        
        
		
	}
	@AfterEach
	void setdown() throws Exception {
		candao.deleteAll();
		sdao.deleteAll();
	}
	
	@Test 
	void addProfileTest(){
	    
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
	  
	    
	
      candidateService.addProfile(dto);
      Candidate candexpec = candidateService.findCandidateByName("yashss");
      
      Candidate candactual = candao.findById(candexpec.getCandidateId()).get();
      Assertions.assertEquals(candexpec.getCandidateName(), candactual.getCandidateName());
      
	    
	}
	@Test 
    void addProfileTestfailed(){
        
        ProfileDTO dto = new ProfileDTO();
        dto.setAge(2);
      
        dto.setEducationQualification("extra");
        dto.setEmailId("emailid");
        dto.setExperience(2);
        dto.setLocation("here");
        dto.setPassword("password");
        
        dto.setProjectDTOList(pdtl);
        
        dto.setSkillDTOSet(csdts);
      
    
      
      Candidate candexpec = candidateService.findCandidateByName("yashss");
      Exception exception = Assertions.assertThrows(CandidateValidationExceptioncheck.class, 
              () -> {candidateService.addProfile(dto);
      });

      String expectedMessage = "hey there check the docs for validation errors";
      String actualMessage = exception.getMessage();

    //  Assertions.assertTrue(actualMessage.contains(expectedMessage));
        
    }
	
	
	 @Test
	 public void removeProjectByProjectIdTest() throws ProjectNotFoundException{
	      
	       
	        candao.save(cand1);

	      int length = (int) projectdao.count();
	        
	        projectService.removeById(cand1.getProjectList().get(0).getProjectId());
	       
	        int lengthafter = (int) projectdao.count();
	        
       
	        Assertions.assertNotEquals(length, lengthafter);
	  }
	 @Test
	 public void removeProjectByProjectIdTestFailed(){
	     candao.save(cand1);
	     int length = (int) projectdao.count();
         
         
        
         int lengthafter = (int) projectdao.count();
         
         Exception exception = Assertions.assertThrows(ProjectNotFoundException.class, 
         () -> {projectService.removeById(55);
         });

         String expectedMessage = "the provided id is not in this world";
         String actualMessage = exception.getMessage();

         Assertions.assertTrue(actualMessage.contains(expectedMessage));
         
   }
	 
	 @Test
     public void getallCandidates() throws ProjectNotFoundException{
	     candao.save(cand1);


	     int length = (int) candao.count();
         

        
         Assertions.assertEquals(length, 1);
   }
	 
	 
	 @Test
	 public void provideCandidateFeedbackTestfailed2(){
	        
        
        
        List<Interview> interviews = new ArrayList<>();
        Interview inter = new Interview();

        inter.setCandidateFeedback("ssome gibberish");
        inter.setPreInterviewStatus(PreInterviewStatus.SHORTLISTED);
        inter.setPostInterviewStatus(PostInterviewStatus.SELECTED);
        interviews.add(inter);
        cand1.setInterviewList(interviews);
        candao.save(cand1);
        RatingFeedbackDTO dtoshit = new RatingFeedbackDTO(22, "ssome gibberish");
        
        Exception exception = Assertions.assertThrows(feedbackException.class, 
                () -> {
                    interviewService.provideCandidateFeedback(inter.getInterviewId(),dtoshit);
                    });
        

       String expectedMessage = 
               "caught transaction exception might be an validation error";
       String actualMessage = exception.getMessage();

       Assertions.assertTrue(actualMessage.equals(expectedMessage));
	        
	     
	        
	       
	  } 
	   
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
	
	
//
//	@Test
//	void addcandidate() {
//		Candidate cand1 = new Candidate();
//		cand1.setAge(21);
//		cand1.setCandidateName("Yash");
//		service.addAndCheckSkill(cand1);
//		
//		Candidate candexpec = cand1;
//		
//		
//		Candidate candactual = candao.findById(candexpec.getCandidateId()).get();
//		Assertions.assertEquals(candexpec.getCandidateName(), candactual.getCandidateName());
//		
//	}
//
//	
//
//	@Test
//	void updateCandidate() {
//		Candidate cand1 = new Candidate();
//		cand1.setAge(21);
//		cand1.setCandidateName("Yash");
//		candao.save(cand1);
//		
//		Candidate candexpec = cand1;
//		//System.out.println(cand1);
//		service.updateCandidate(candexpec);
//		
//
//		
//		Candidate candactual = candao.findById(candexpec.getCandidateId()).get();
//		Assertions.assertEquals(candexpec.getCandidateName(), candactual.getCandidateName());
//		
//	}
//
//	@Test
//	void deleteCandidateTest() {
//		Candidate cand1 = new Candidate();
//		cand1.setAge(21);
//		cand1.setCandidateName("Yash");
//		candao.save(cand1);
//		
//	
//	
//		Candidate cand2 = cand1;
//		service.deleteCandidate(cand2);
//		Example<Candidate> ex = Example.of(cand2);
//
//		Assertions.assertFalse(candao.exists(ex));
//		
//		
//	}
//	
//	@Test
//	void updateLocationTest() {
//		
//		Candidate cand1 = new Candidate();
//		cand1.setAge(21);
//		cand1.setCandidateName("Yash");
//		candao.save(cand1);
//		cand1.setLocation("sdfksdjssdcs");;
//		
//
//		
//		service.updateLocation(cand1.getCandidateId(),"sdfksdj");
//		Candidate actual = candao.findById(cand1.getCandidateId()).get();
//		
//		
//		assertFalse(cand1.getLocation().equals(actual.getLocation()));
//		
//	}
////
////	@ParameterizedTest
////	@ValueSource(ints = {1})
////	void findbyidTest(int i) {
////		Candidate cand1 = new Candidate();
////		cand1.setAge(21);
////		cand1.setCandidateName("Yash");
////		
////		
////		candao.save(cand1);
////		//System.out.println(cand1.getCandidateId());
////		cand1.setAge(25);
////		candao.save(cand1);
////		
////		Candidate cn = service.findById(i);
////		assertNotNull(cn);
////		
////	
////	
////	}
////	
//	@ParameterizedTest
//	@ValueSource(ints = {0})
//
//	void findbyidTest0(int i) {
//		Candidate cand1 = new Candidate();
//		cand1.setAge(21);
//		cand1.setCandidateName("Yash");
//		candao.save(cand1);
//		//System.out.println(cand1.getCandidateId());
//		cand1.setAge(25);
//		candao.save(cand1);
//		
//		Candidate cn = service.findById(i);
//		assertNull(cn);
//	
//	}
//	
//	
//	
}
