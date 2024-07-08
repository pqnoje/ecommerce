package com.pqnoje.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pqnoje.ecommerce.model.Shelf;
import com.pqnoje.ecommerce.service.ShelfService;

@RestController
public class ShelfController {
	
	@Autowired
	ShelfService shelfService;
	
	@PostMapping(path = "/shelf")
	public ResponseEntity<Shelf> createProduct(@RequestBody Shelf shelf) throws ResponseStatusException {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.shelfService.save(shelf));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(shelf);
		}
	}

}
