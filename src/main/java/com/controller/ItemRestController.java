package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.ItemDao;
import com.model.Item;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@RestController
public class ItemRestController {
	
	@Autowired
	ItemDao itemDao;
	
	
	
	
	@GetMapping("/home")
	public String gethomeinfo() {
		return "its working";
	}
	
	@ApiOperation(value = "addanitem",notes="Adding an item through get method",nickname = "Add1i" )
	@PostMapping("/addanitem")
	@ApiResponses(value= {@ApiResponse(code=200,message="object processed")})
	public ResponseEntity addItem(@RequestBody Item item) {
		
		itemDao.save(item);
		
		return new ResponseEntity("item added successfully",HttpStatus.ACCEPTED);
		
	}
	
	@ApiOperation(value = "getallitem",notes="Getting a list od all items",nickname = "getlist" )

	@GetMapping("/getallitems")
	public List<Item> getallitems() {
		
		
		return itemDao.findAll();
		
	}
	@ApiOperation(value = "updateanitem",notes="updating an item through patchmapping",nickname = "update" )
	@ApiResponses(value= {@ApiResponse(code=200,message="object updated")})

	@PatchMapping("/updateitem")
	public ResponseEntity updateItem(@RequestBody Item item) {
		
		itemDao.save(item);
		return  new ResponseEntity("item updated successfully",HttpStatus.ACCEPTED);
	}
	
	
	@ApiOperation(value = "deleteanitem",notes="deleting an item through deletemapping",nickname = "delete" )
	@ApiResponses(value= {@ApiResponse(code=200,message="object deleted")})

	@DeleteMapping("/deleteitem")
	public ResponseEntity deleteItem(@RequestBody Item item) {
		System.out.println(item);
		itemDao.delete(item);
		return  new ResponseEntity("item delete successfully",HttpStatus.FOUND);
	}
	
	@ApiOperation(value = "findanitem",notes="finding an item through an id",nickname = "find" )
	@ApiResponses(value= {@ApiResponse(code=200,message="object deleted")})
	
	@GetMapping("/findbyId/{id}")
	public Item getItem(@PathVariable int id ) {
		
		Item item = itemDao.findById(id).get();
		
		return item;
		
	}
	
	
	
}