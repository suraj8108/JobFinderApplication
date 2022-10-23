package com.sprint.naukri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import com.dao.CandidateDAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Candidate;
import com.model.CandidateSkill;
import com.model.Project;
import com.service.CandidateService;

@SpringBootTest
public class CandidateTest {
	@Autowired
	CandidateDAO candao ;
	@Autowired
	CandidateService service;
	
	Candidate cand1=new Candidate();;
	
	
	

	//rest controller test
	@BeforeEach
	void setUp() throws Exception {
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
		
		Set<CandidateSkill> css = new HashSet<>() ;
		css.add(new CandidateSkill("Tyons"));
		css.add(new CandidateSkill("sflndskn"));
		
		cand1.setProjectList(pl);
		cand1.setCanditationSkillSet(css);
		
		
		
		
	}
	@AfterEach
	void setdown() throws Exception {
		candao.deleteAll();
		candao.flush();
		
	}
	
	
	
	@Test
	public void getAllCandidatesTest() {
		
		candao.save(cand1);
		
		List<Candidate> clist = new ArrayList<>();
		clist.add(cand1);
		
		System.out.println(clist);
		System.out.println(service.getAllCandidates());
		Assertions.assertEquals(clist.toString(),service.getAllCandidates().toString());
	}


	

//	@ParameterizedTest
//	@Nullable
//	@MethodSource("canditestvalues")
	@Test
	void addcandidate() {
		Candidate candexpec = cand1;
		candao.save(candexpec);
		

		
		Candidate candactual = candao.findById(candexpec.getCandidateId()).get();
		Assertions.assertEquals(candexpec.getCandidateName(), candactual.getCandidateName());
		
	}
//	private static Stream<Arguments> canditestvalues() {
//	    return Stream.of(
//	      Arguments.of(cand1),
//	      Arguments.of(new Candidate( "yash", 22, 1, "hell", "b.tech", new String[] {"Project1","Project2"} )),
//	      Arguments.of(new Candidate())
//	    );
//	}
	
//	@ParameterizedTest
//	@Nullable
//	@MethodSource("updatecanditestvalues")
	@Test
	void updateCandidate() {
		Candidate candexpec = cand1;
		service.updateCandidate(candexpec);
		

		
		Candidate candactual = candao.findById(candexpec.getCandidateId()).get();
		Assertions.assertEquals(candexpec.getCandidateName(), candactual.getCandidateName());
		
	}
//	private static Stream<Arguments> updatecanditestvalues() {
//		Candidate candy = can1
//	    return Stream.of(
//	      Arguments.of(new Candidate( "yash", 22, 1, "hell", "b.tech", new String[] {"Project1","Project2"} )),
//	      Arguments.of(new Candidate( "yash", 22, 1, "hell", "b.tech", new String[] {"Project1","Project2"} )),
//	      Arguments.of(new Candidate())
//	    );
//	}
//	
	@Test
	void deleteCandidateTest() {
		candao.save(cand1);
		Candidate cand2 = cand1;

		service.deleteCandidate(cand1);
		Example<Candidate> ex = Example.of(cand1);

		Assertions.assertFalse(candao.exists(ex));
		
		
	}
	
//	@Test
//	void updateLocationTest() {
//		candao.save(cand1);
//		Candidate cand2 = cand1;
//
//		service.updateCandidate(cand2);
//		
//	}
	
	
	
	
	
}
