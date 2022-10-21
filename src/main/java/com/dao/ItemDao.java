package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Item;
@Repository
public interface ItemDao extends JpaRepository<Item, Integer>{
	
	public int countByItemName(String ItemName);
	
	public Item findByItemName(String ItemName);
}
