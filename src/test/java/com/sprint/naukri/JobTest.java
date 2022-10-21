package com.sprint.naukri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
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

import com.dao.JobDao;
import com.model.Job;

@SpringBootTest
public class JobTest {
	@Autowired
	JobDao dao;
	
	@Test
	void testbyID() {
		Job job = new Job();
		job.setJobDescription("technical");
		job.setStatus("active");
		job.setJobId(1);
		dao.save(job);
		
		Job job2 = dao.findById(job.getJobId()).get();
		Assertions.assertEquals(job.getJobId(), job2.getJobId());
	}
	
	@Test
	void testbystatus() {
		Job job = new Job();
		job.setJobDescription("technical skill");
		job.setStatus("discarded");
		job.setJobId(2);
		dao.save(job);
		
		Job job2 = dao.findAllByStatus("discarded").get(0);
		Assertions.assertEquals(job.getStatus(), job2.getStatus());
	}
	
	@Test
	void testbydescription() {
		Job job = new Job();
		job.setJobDescription("technical skill");
		job.setStatus("discarded");
		job.setJobId(3);
		dao.save(job);
		
		Job job2 = dao.findAllByJobDescription("technical skill").get(0);
		Assertions.assertEquals(job.getStatus(), job2.getStatus());
	}

}
