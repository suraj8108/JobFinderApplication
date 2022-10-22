package com.sprint.naukri;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.dao.CandidateDao;
import com.dao.SkillDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Candidate;
import com.model.Skill;
import com.model.Project;

import com.service.CandidateService;


@SpringBootTest

public class CandidateTest {
	@Autowired
	 CandidateDao candao ;
	@Autowired
	CandidateService service;
	@Autowired
	SkillDao sdao;
	
	 

	//rest controller test
	@BeforeEach
	public  void setUp() throws Exception {
		candao.deleteAll();
		
		
	
		
	}
	@AfterEach
	void setdown() throws Exception {
		candao.deleteAll();
	}
	
	
	
	@Test
	@Transactional
	public void getAllCandidatesTest() {
		Candidate cand1 = new Candidate();
		cand1.setAge(21);
		cand1.setCandidateName("Yash");
		candao.save(cand1);
		
		Candidate cand2 = new Candidate();
		cand2.setAge(21);
		cand2.setCandidateName("Yash");
		
		candao.save(cand2);
		
		List<Candidate> clist = new ArrayList<>();
		clist.add(cand1);
		clist.add(cand2);
//		System.out.println(clist);
//		System.out.println(service.getAllCandidates());
		Assertions.assertEquals(clist.toString(),service.getAllCandidates().toString());
	
	
	}


	


	@Test
	void addcandidate() {
		Candidate cand1 = new Candidate();
		cand1.setAge(21);
		cand1.setCandidateName("Yash");
		service.addCandidate(cand1);
		
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
	
	@ParameterizedTest
	@ValueSource(ints = {1})
	@Nullable
	void findbyidTest(int i) {
		Candidate cand1 = new Candidate();
		cand1.setAge(21);
		cand1.setCandidateName("Yash");
		
		
		candao.save(cand1);
		//System.out.println(cand1.getCandidateId());
		cand1.setAge(25);
		candao.save(cand1);
		
		Candidate cn = service.findById(i);
		assertNotNull(cn);
		
	
	
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0})
	@Nullable
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
