package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Job;

public interface EmployerDao extends JpaRepository<Job, Integer> {

}
