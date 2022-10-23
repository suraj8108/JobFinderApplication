package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.lang.Nullable;

import com.dao.CandidateDAO;
import com.dao.SkillDAO;

import com.model.Candidate;
import com.model.Project;
import com.model.Skill;
import com.service.CandidateService;


@SpringBootTest

public class CandidateTest {
	@Autowired
	CandidateDAO candao ;
	@Autowired
	CandidateService service;
	@Autowired
	SkillDAO sdao;
	
	 

	Candidate cand1 = new Candidate();
	//rest controller test
	@BeforeEach
	public  void setUp() throws Exception {
		candao.deleteAll();
		
		candao.flush();
		
		 
		cand1.setAge(22);
		cand1.setCandidateName("yash");
		cand1.setEducationQualification("B.tech");
		cand1.setExperience(2);
		cand1.setLocation("sfs");
		cand1.setEmailId("suraj@gmail.com");
		cand1.setPassword("121aaa");
		
		
		
		
		List<Project> pl = new ArrayList<>();
		
		
		pl.add(new Project("yjbabv","happened"));
		pl.add(new Project("slnacncs","happened"));
		
		Set<Skill> css = new HashSet<>() ;
		css.add(new Skill("Tyons"));
		css.add(new Skill("sflndskn"));
		
		cand1.setProjectList(pl);
		cand1.setSkillSet(css);	
		
	}
	@AfterEach
	void setdown() throws Exception {
		candao.deleteAll();
	}
	
	

	@Test
	void addcandidate() {
		Candidate cand1 = new Candidate();
		cand1.setAge(21);
		cand1.setCandidateName("Yash");
		service.addAndCheckSkill(cand1);
		
		Candidate candexpec = cand1;
		
		
		Candidate candactual = candao.findById(candexpec.getCandidateId()).get();
		Assertions.assertEquals(candexpec.getCandidateName(), candactual.getCandidateName());
		
	}

	

	@Test
	void updateCandidate() {
		Candidate cand1 = new Candidate();
		cand1.setAge(21);
		cand1.setCandidateName("Yash");
		candao.save(cand1);
		
		Candidate candexpec = cand1;
		//System.out.println(cand1);
		service.updateCandidate(candexpec);
		

		
		Candidate candactual = candao.findById(candexpec.getCandidateId()).get();
		Assertions.assertEquals(candexpec.getCandidateName(), candactual.getCandidateName());
		
	}

	@Test
	void deleteCandidateTest() {
		Candidate cand1 = new Candidate();
		cand1.setAge(21);
		cand1.setCandidateName("Yash");
		candao.save(cand1);
		
	
	
		Candidate cand2 = cand1;
		service.deleteCandidate(cand2);
		Example<Candidate> ex = Example.of(cand2);

		Assertions.assertFalse(candao.exists(ex));
		
		
	}
	
	@Test
	void updateLocationTest() {
		
		Candidate cand1 = new Candidate();
		cand1.setAge(21);
		cand1.setCandidateName("Yash");
		candao.save(cand1);
		cand1.setLocation("sdfksdjssdcs");;
		

		
		service.updateLocation(cand1.getCandidateId(),"sdfksdj");
		Candidate actual = candao.findById(cand1.getCandidateId()).get();
		
		
		assertFalse(cand1.getLocation().equals(actual.getLocation()));
		
	}
//
//	@ParameterizedTest
//	@ValueSource(ints = {1})
//	void findbyidTest(int i) {
//		Candidate cand1 = new Candidate();
//		cand1.setAge(21);
//		cand1.setCandidateName("Yash");
//		
//		
//		candao.save(cand1);
//		//System.out.println(cand1.getCandidateId());
//		cand1.setAge(25);
//		candao.save(cand1);
//		
//		Candidate cn = service.findById(i);
//		assertNotNull(cn);
//		
//	
//	
//	}
//	
	@ParameterizedTest
	@ValueSource(ints = {0})

	void findbyidTest0(int i) {
		Candidate cand1 = new Candidate();
		cand1.setAge(21);
		cand1.setCandidateName("Yash");
		candao.save(cand1);
		//System.out.println(cand1.getCandidateId());
		cand1.setAge(25);
		candao.save(cand1);
		
		Candidate cn = service.findById(i);
		assertNull(cn);
	
	}
	
	
	
}
