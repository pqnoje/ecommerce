package com.pqnoje.ecommerce.service;

import java.util.List;

import com.pqnoje.ecommerce.model.Product;
import com.pqnoje.ecommerce.model.Shelf;

public interface ProductService {
	
	public Product save(Product product);
	public void associateToShelfs(List<Shelf> shelfs, int productId);
	public List<Product> listProductsByShelfId(int shelfId);

}
