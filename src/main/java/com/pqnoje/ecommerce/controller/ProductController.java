package com.pqnoje.ecommerce.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<String> associateProductToShelfs(@RequestBody List<Shelf> shelfs, @PathVariable int productId)
			throws ResponseStatusException {
		try {
			this.productService.associateToShelfs(shelfs, productId);
			return ResponseEntity.status(HttpStatus.CREATED).body("Product has associated to one or more shelfs.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Worng parameters.");
		}
	}

	@PutMapping(path = "/product/{productId}")
	public ResponseEntity<String> updateProduct(@RequestBody Product product, @PathVariable int productId)
			throws ResponseStatusException {
		try {
			this.productService.update(product, productId);
			return ResponseEntity.status(HttpStatus.OK).body("Product was updated.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}

	@DeleteMapping(path = "product/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable int productId) throws ResponseStatusException {
		try {
			this.productService.delete(productId);
			return ResponseEntity.status(HttpStatus.OK).body("Product was deleted.");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This product ID does not exists.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Something went wrong");
		}
	}

	@GetMapping(path = "product/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable int productId) throws ResponseStatusException {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.productService.get(productId));
		} catch (IllegalArgumentException illegalArgumentException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (SQLException sqlException) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "product")
	public ResponseEntity<List<Product>> getAllProducts() throws ResponseStatusException {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.productService.getAll());
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@GetMapping(path = "product/shelf/{shelfId}")
	public ResponseEntity<List<Product>> getAllProducts(@PathVariable int shelfId) throws ResponseStatusException {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.productService.listProductsByShelfId(shelfId));
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

}
