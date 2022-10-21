package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dao.CandidateDao;
import com.model.Candidate;

@Service
public class CandidateService implements UserDetailsService{

	@Autowired
	CandidateDao canDao;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException{
		// TODO Auto-generated method stub
		Candidate can = canDao.findByEmailId(emailId);
		System.out.println(emailId);
		
		//return new User(can.getEmailId(), can.getPassword(), new ArrayList<>());
		//System.out.println(passwordEncoder().encode("121aaa"));
		return new User("suraj@gmail.com",  "121aaa", new ArrayList<>());
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		//return NoOpPasswordEncoder.getInstance();
//		return new BCryptPasswordEncoder();
//	}
	
	
}
