package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EmployerDao;
@Service
public class EmployerService {
	@Autowired
	EmployerDao employerDao;
	

}
