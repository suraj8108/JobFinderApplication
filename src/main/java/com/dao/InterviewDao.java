package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Interview;

public interface InterviewDao extends JpaRepository<Interview, Integer> {

}
