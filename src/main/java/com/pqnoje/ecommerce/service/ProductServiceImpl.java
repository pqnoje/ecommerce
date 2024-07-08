package com.pqnoje.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pqnoje.ecommerce.dao.ProductDao;
import com.pqnoje.ecommerce.model.Product;
import com.pqnoje.ecommerce.model.Shelf;

@Service
public class ProductServiceImpl implements ProductService {

	@Override
	public Product save(Product product) {
		ProductDao productDao = new ProductDao();
		Product savedProduct = productDao.save(product);
		return savedProduct;
	}

	@Override
	public void associateToShelfs(List<Shelf> shelfs, int productId) {
		ProductDao productDao = new ProductDao();
		productDao.associateToShelfs(shelfs, productId);
	}

	@Override
	public List<Product> listProductsByShelfId(int shelfId) {
		ProductDao productDao = new ProductDao();
		List<Product> products = productDao.listProductsByShelfId(shelfId);
		return products;
	}

}
