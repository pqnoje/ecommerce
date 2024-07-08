package com.pqnoje.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pqnoje.ecommerce.model.Product;
import com.pqnoje.ecommerce.model.Shelf;
import com.pqnoje.ecommerce.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping(path = "/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) throws ResponseStatusException {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.save(product));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(product);
		}
	}
	
	@PostMapping(path = "/product/{productId}/associateToShelfs")
	public ResponseEntity<String> associateProductToShelfs(@RequestBody List<Shelf> shelfs, @PathVariable int productId) throws ResponseStatusException {
		try {
			this.productService.associateToShelfs(shelfs, productId);
			return ResponseEntity.status(HttpStatus.CREATED).body("Product has associated to one or more shelfs.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Worng parameters.");
		}
	}
	
	@GetMapping(path = "/product/shelf/{shelfId}")
	public ResponseEntity<List<Product>> listProductsByShelf(@PathVariable int shelfId) throws ResponseStatusException {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.listProductsByShelfId(shelfId));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	} 

}
