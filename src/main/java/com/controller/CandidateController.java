package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dao.CandidateDao;


@Controller
public class CandidateController {

	
	@Autowired
	CandidateDao candidatedao;
	
	
	
}
