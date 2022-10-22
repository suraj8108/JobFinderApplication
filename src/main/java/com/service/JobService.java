package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.JobDao;
@Service
public class JobService {
	
	@Autowired
	JobDao jobDao;
	
}
