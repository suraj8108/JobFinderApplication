package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CandidateDao;
import com.helper.JwtUtil;
import com.model.Candidate;
import com.model.JwtRequest;
import com.model.JwtResponse;
import com.service.CandidateService;


@RestController
public class AuthenticationController {

	@Autowired
	CandidateDao candDao;
	
	@Autowired
	private JwtUtil jwtUtility; 
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CandidateService candidateService;
	
	@PostMapping("/")
	public String login() {
		return "Successfully Landed to the Page";
	}
	
	@RequestMapping(value = "/authenticate", headers = "Accept=application/json", method = RequestMethod.POST)
	public JwtResponse authenticateCand(@RequestBody JwtRequest jwtRequest) throws Exception {
		
//		System.out.println(HttpRe);
		//System.out.println( request.getContentType() + request.getContentLength());
//		System.out.println(request.);
		System.out.println(jwtRequest);
		System.out.println(jwtRequest.getUsername());
		System.out.println(jwtRequest.getPassword());
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							jwtRequest.getUsername(), 
							jwtRequest.getPassword()
							)
					);
		}
		catch (BadCredentialsException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw new Exception("INVALID_CREDENTIALS");
		}
		
		final UserDetails userDetails = candidateService.loadUserByUsername(jwtRequest.getUsername());
		
		final String token = jwtUtility.generateToken(userDetails);
		
		return new JwtResponse(token);
		
	}
	
	@PostMapping("/registerCandidate")
	public ResponseEntity<String> registerCandidate(@RequestBody Candidate candidate) {
		
		candDao.save(candidate);
		return new ResponseEntity<>("Added Successfully", HttpStatus.OK);
		
	}
	
	
}
