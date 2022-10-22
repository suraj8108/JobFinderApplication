package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.InterviewDao;
import com.dto.CandiadateRatingFeedbackDTO;
import com.model.Interview;
@Service
public class InterviewService {
	@Autowired
	InterviewDao interviewDAO;

	public Interview getInterviewById(int parseInt) {
		
		return null;
	}

	public void provideCandidateFeedback(Interview i, CandiadateRatingFeedbackDTO dto) {

	    i.setEmployerRating(dto.getRating());
	    i.setEmployerFeedback(dto.getFeedback());
	    interviewDAO.save(i);
	    
	}

}
