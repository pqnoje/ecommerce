package com.pqnoje.ecommerce.service;

import java.sql.SQLException;
import java.util.List;

import com.pqnoje.ecommerce.model.Product;
import com.pqnoje.ecommerce.model.Shelf;

public interface ProductService {
	
	public Product save(Product product);
	public void associateToShelfs(List<Shelf> shelfs, int productId);
	public List<Product> listProductsByShelfId(int shelfId) throws Exception, SQLException, IllegalArgumentException;
	public void update(Product product, int productId);
	public void delete(int productId) throws Exception, SQLException;
	public Product get(int productId) throws Exception, SQLException, IllegalArgumentException;
	public List<Product> getAll() throws Exception, SQLException;
}
