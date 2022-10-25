package com.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.InterviewDAO;

import com.model.Interview;
import com.model.Job;

import java.util.List;



import com.dto.RatingFeedbackDTO;
import com.enums.PostInterviewStatus;
import com.exception.NoSuchInterviewFoundException;


@Service
public class InterviewService {
	@Autowired
	InterviewDAO interviewDAO;

	public Interview getInterviewById(int id) throws NoSuchInterviewFoundException {
	    try {
	      Interview i = interviewDAO.findById(id).get();
	      return i;
	    } catch (Exception e) {
	      throw new NoSuchInterviewFoundException(id);
	    }
	  }
	    
	public void provideCandidateFeedback(Interview interview, RatingFeedbackDTO dto) {
	  interview.setCandidateRating(dto.getRating());
	  interview.setCandidateFeedback(dto.getFeedback());
	  interviewDAO.save(interview);
	}

  

    public void provideEmployerFeedback(Interview interview, RatingFeedbackDTO dto) {
      interview.setEmployerRating(dto.getRating());
      interview.setEmployerFeedback(dto.getFeedback());
      interviewDAO.save(interview);
    }
  
  

    public List<Interview> getAllInterviews() {
      return interviewDAO.findAll();
    }
    
    public void rejectAllInterviewsForJob(Job job) {
      List<Interview> interviewsNotRejected = interviewDAO.findByJobAndPostInterviewStatusIsNot(job, PostInterviewStatus.REJECTED);
      for (Interview i: interviewsNotRejected) {
        i.setPostInterviewStatus(PostInterviewStatus.REJECTED);
        interviewDAO.save(i);
      }
    }
  

}
