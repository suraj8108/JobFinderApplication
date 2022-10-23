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

import com.dao.JobDAO;

import com.enums.JobStatus;
import com.model.Job;

@SpringBootTest
public class JobTest {
//	@Autowired
//	JobDAO dao;
//	
//	@Test
//	void testbyID() {
//		Job job = new Job();
//		job.setJobDescription("technical");
//		job.setJobStatus(JobStatus.OPENED);
//		job.setJobId(1);
//		dao.save(job);
//		
//		Job job2 = dao.findById(job.getJobId()).get();
//		Assertions.assertEquals(job.getJobId(), job2.getJobId());
//	}
//	
//	@Test
//	void testbystatus() {
//		Job job = new Job();
//		job.setJobDescription("technical skill");
//		job.setJobStatus(JobStatus.CLOSED);
//		job.setJobId(2);
//		dao.save(job);
//		
//		Job job2 = dao.findAllByJobStatus("discarded").get(0);
//		Assertions.assertEquals(job.getJobStatus(), job2.getJobStatus());
//	}
//	
//	@Test
//	void testbydescription() {
//		Job job = new Job();
//		job.setJobDescription("technical skill");
//		job.setJobStatus(JobStatus.CLOSED);
//		job.setJobId(3);
//		dao.save(job);
//		
//		Job job2 = dao.findAllByJobDescription("technical skill").get(0);
//		Assertions.assertEquals(job.getJobStatus(), job2.getJobStatus());
//	}

}
