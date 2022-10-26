package com.sprint.naukri;

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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

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
import com.exception.feedbackException;
import com.exception.skillNotFoundException;
import com.model.Candidate;
import com.model.Interview;
import com.model.Project;
import com.model.Skill;
import com.service.CandidateService;
import com.service.InterviewService;
import com.service.ProjectService;
@SpringBootTest
@Transactional
public class AllTransactionalCandidateServiceTest {
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
    
    
    
     Candidate cand1 = new Candidate();
     List<Project> pl = new ArrayList<>();
     Set<Skill> css = new HashSet<>() ;
    
    @BeforeEach
   
    public  void setUp() throws Exception {
     candao.flush();
    candao.deleteAll();
    skilldao.deleteAll();
         
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
// 
        css.add(s1);
        css.add(s2);
//     
//        cand1.setSkillSet(css); 
//        
//       
        
        
        
        
        
        
    }
    @AfterEach
    void setdown() throws Exception {
    candao.deleteAll();
    }
 
    @Test
    public void addProjectbyIdTest() throws CandidateNotFoundException{
        cand1.setSkillSet(css); 
        int length = cand1.getProjectList().size();
        System.out.println(cand1);
        candao.save(cand1);
      
        System.out.println(cand1);

       List<ProjectDTO> pdt = new ArrayList<>();
       pdt.add(new ProjectDTO("kjsnv","skjnvk"));
        
        candidateService.addProjectbyId(cand1.getCandidateId(), pdt);
        int lengthafter = cand1.getProjectList().size();
        
        Assertions.assertNotEquals(length, lengthafter);
  }

    @Test
    public void addProjectbyIdTestfalied(){
        
        int length = cand1.getProjectList().size();
      
      
      

       List<ProjectDTO> pdt = new ArrayList<>();
       pdt.add(new ProjectDTO("kjsnv","skjnvk"));
        
        
       
        Exception exception = Assertions.assertThrows(CandidateNotFoundException.class, 
                () -> {candidateService.addProjectbyId(cand1.getCandidateId(), pdt);
        });

        String expectedMessage = "Check the id entered";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
        
  }
    @Test
    public void updateLocationByCandidateIdTest(){
        
        Candidate cand2 = new Candidate();
        cand2.setAge(22);
        cand2.setLocation("sjnnsn");
        cand2.setCandidateName("aysbb");
         List<Project> pl2 = new ArrayList<>();
         
        Project p1 = new Project("yjzcascbabv","happened");
        p1.setCandidate(cand2);
        Project p2 = new Project("slnaascasccncs","happened");
        p2.setCandidate(cand2);
        pl2.add(p1);
        pl2.add(p2);
        cand2.setProjectList(pl2);
        
        String before = cand2.getLocation();
      
       candao.save(cand2);


       candidateService.updateLocationByCandidateId(cand2.getCandidateId(), "Lexter");
      
       Candidate actual = candao.findById(cand2.getCandidateId()).get();
       System.out.println(actual);
      
  
       Assertions.assertFalse(before.equals(actual.getLocation()));
       
 }
    @Test
    public void updateLocationByCandidateIdTestFailed(){
        
        Candidate cand2 = new Candidate();
        cand2.setAge(22);
        cand2.setLocation("sjnnsn");
        cand2.setCandidateName("aysbb");
         List<Project> pl2 = new ArrayList<>();
         
        Project p1 = new Project("yjzcascbabv","happened");
        p1.setCandidate(cand2);
        Project p2 = new Project("slnaascasccncs","happened");
        p2.setCandidate(cand2);
        pl2.add(p1);
        pl2.add(p2);
        cand2.setProjectList(pl2);
        
        String before = cand2.getLocation();
      
       candao.save(cand2);


       ;

       Exception exception = Assertions.assertThrows(NoSuchElementException.class, 
       () -> {candidateService.updateLocationByCandidateId(40, "Lexter");
       });

  

       
 }
    @Test
    public void addSkillByCandidateIdTest() throws CandidateNotFoundException{
        
         Candidate cand2 = new Candidate();
         cand2.setAge(22);
         cand2.setCandidateName("aysbb");
          List<Project> pl2 = new ArrayList<>();
          
         Project p1 = new Project("yjzcascbabv","happened");
         p1.setCandidate(cand2);
         Project p2 = new Project("slnaascasccncs","happened");
         p2.setCandidate(cand2);
         pl2.add(p1);
         pl2.add(p2);
         cand2.setProjectList(pl2);
         
       
        candao.save(cand2);
        
        int lengthBefore = cand2.getSkillSet().size();
        
        candidateService.addSkillDTOByCandidateId(cand2.getCandidateId(), new SkillDTO("Java"));

        int lengthAfter  = candao.findById(cand2.getCandidateId()).get().getSkillSet().size();
      
        Assertions.assertNotEquals(lengthBefore,lengthAfter);
        
        
  }
    @Test
    public void addSkillByCandidateIdTestFailed(){
        
         Candidate cand2 = new Candidate();
         cand2.setAge(22);
         cand2.setCandidateName("aysbb");
          List<Project> pl2 = new ArrayList<>();
          
         Project p1 = new Project("yjzcascbabv","happened");
         p1.setCandidate(cand2);
         Project p2 = new Project("slnaascasccncs","happened");
         p2.setCandidate(cand2);
         pl2.add(p1);
         pl2.add(p2);
         cand2.setProjectList(pl2);
         
       
        candao.save(cand2);
        
       

        
        Exception exception = Assertions.assertThrows(CandidateNotFoundException.class, 
         () -> {
             candidateService.addSkillDTOByCandidateId(50, new SkillDTO("Java"));
                });

        String expectedMessage = "Candidate User Not Found";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
        
        
  }
    
    
    
    
    @Test
    public void removeSkillbyCanidateIdAndSkillName() throws CandidateNotFoundException, skillNotFoundException{
        
         Candidate cand2 = new Candidate();
         cand2.setAge(22);
         cand2.setCandidateName("aysbb");
          List<Project> pl2 = new ArrayList<>();
          
         Project p1 = new Project("yjzcascbabv","happened");
         p1.setCandidate(cand2);
         Project p2 = new Project("slnaascasccncs","happened");
         p2.setCandidate(cand2);
         pl2.add(p1);
         pl2.add(p2);
         cand2.setSkillSet(css);
         
         
       
        candao.save(cand2);
        
        int lengthBefore = cand2.getSkillSet().size();
        
        candidateService.removeSkillbyCanidateIdAndSkillName(cand2.getCandidateId(),"JSon" );

        int lengthAfter  = candao.findById(cand2.getCandidateId()).get().getSkillSet().size();
        System.out.println(lengthBefore+" "+lengthAfter);

        Assertions.assertNotEquals(lengthBefore,lengthAfter);
        
        
  }
    
    
    @Test
    public void removeSkillbyCanidateIdAndSkillNameFailed() throws CandidateNotFoundException, skillNotFoundException{
        
         Candidate cand2 = new Candidate();
         cand2.setAge(22);
         cand2.setCandidateName("aysbb");
          List<Project> pl2 = new ArrayList<>();
          
         Project p1 = new Project("yjzcascbabv","happened");
         p1.setCandidate(cand2);
         Project p2 = new Project("slnaascasccncs","happened");
         p2.setCandidate(cand2);
         pl2.add(p1);
         pl2.add(p2);
         cand2.setSkillSet(css);
         
         
       
        candao.save(cand2);
        
      
        List<Exception> exceptionList= new ArrayList<>();
       
        
        Exception exception = Assertions.assertThrows(skillNotFoundException.class, 
                () -> {
                    candidateService.removeSkillbyCanidateIdAndSkillName(cand2.getCandidateId(),"klslm" );
                       });

               String expectedMessage = "No such skill is linked with candidate";
               String actualMessage = exception.getMessage();

               Assertions.assertTrue(actualMessage.contains(expectedMessage));
        
        
  }
   
    @Test
    public void removeSkillbyCanidateIdAndSkillNameFailed2() throws CandidateNotFoundException, skillNotFoundException{
        
         Candidate cand2 = new Candidate();
         cand2.setAge(22);
         cand2.setCandidateName("aysbb");
          List<Project> pl2 = new ArrayList<>();
          
         Project p1 = new Project("yjzcascbabv","happened");
         p1.setCandidate(cand2);
         Project p2 = new Project("slnaascasccncs","happened");
         p2.setCandidate(cand2);
         pl2.add(p1);
         pl2.add(p2);
         cand2.setSkillSet(css);
         
         
       
        candao.save(cand2);
        
      
        candidateService.removeSkillbyCanidateIdAndSkillName(cand2.getCandidateId(),"JSon" );
        List<Exception> exceptionList= new ArrayList<>();
       
        
        Exception exception = Assertions.assertThrows(CandidateNotFoundException.class, 
                () -> {
                    candidateService.removeSkillbyCanidateIdAndSkillName(12,"Json" );
                       });

               String expectedMessage = "No candidate is found while checking skill";
               String actualMessage = exception.getMessage();

               Assertions.assertTrue(actualMessage.contains(expectedMessage));
        
        
  }
    
    
    @Test
    public void provideCandidateFeedbackTest() throws feedbackException{
        
         Candidate cand2 = new Candidate();
         cand2.setAge(22);
         cand2.setCandidateName("aysbb");
          List<Project> pl2 = new ArrayList<>();
          
         Project p1 = new Project("yjzcascbabv","happened");
         p1.setCandidate(cand2);
         Project p2 = new Project("slnaascasccncs","happened");
         p2.setCandidate(cand2);
         pl2.add(p1);
         pl2.add(p2);
         cand2.setProjectList(pl2);
         
         List<Interview> interviews = new ArrayList<>();
         Interview inter = new Interview();
         inter.setCandidate(cand2);
//         inter.setCandidateRating(5);
//         inter.setCandidateFeedback("ssome gibberish");
         inter.setPreInterviewStatus(PreInterviewStatus.SHORTLISTED);
         inter.setPostInterviewStatus(PostInterviewStatus.SELECTED);
         interviews.add(inter);
         cand2.setInterviewList(interviews);
         candao.save(cand2);
         RatingFeedbackDTO dtoshit = new RatingFeedbackDTO(5, "ssome gibberish");
       
         interviewService.provideCandidateFeedback(inter.getInterviewId(),dtoshit);
         
        
        
        Candidate cand3 = candao.findById(cand2.getCandidateId()).get();
        
        Assertions.assertTrue(cand3.getInterviewList().get(0).getCandidateRating()==5);


     
        
       
  } 

    @Test
    public void provideCandidateFeedbackTestfailed(){
        
        Candidate cand2 = new Candidate();
        cand2.setAge(22);
        cand2.setCandidateName("aysbb");
         List<Project> pl2 = new ArrayList<>();
         
        Project p1 = new Project("yjzcascbabv","happened");
        p1.setCandidate(cand2);
        Project p2 = new Project("slnaascasccncs","happened");
        p2.setCandidate(cand2);
        pl2.add(p1);
        pl2.add(p2);
        cand2.setProjectList(pl2);
        
        List<Interview> interviews = new ArrayList<>();
        Interview inter = new Interview();
        inter.setCandidate(cand2);
//        inter.setCandidateRating(5);
//        inter.setCandidateFeedback("ssome gibberish");
        inter.setPreInterviewStatus(PreInterviewStatus.SHORTLISTED);
      //  inter.setPostInterviewStatus(PostInterviewStatus.SELECTED);
        interviews.add(inter);
        cand2.setInterviewList(interviews);
        candao.save(cand2);
        RatingFeedbackDTO dtoshit = new RatingFeedbackDTO(5, "ssome gibberish");
      
        
       
       
       Candidate cand3 = candao.findById(cand2.getCandidateId()).get();
       
     
       
         
         
       
        
        Exception exception = Assertions.assertThrows(feedbackException.class, 
                () -> {
                    interviewService.provideCandidateFeedback(inter.getInterviewId(),dtoshit);
                    });
        

               String expectedMessage = 
                       "candidate need to complete his interview or data corrupted"
                     
               ;
               String actualMessage = exception.getMessage();

               Assertions.assertTrue(actualMessage.equals(expectedMessage));
        
     
        
       
  } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}