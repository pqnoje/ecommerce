package com.pqnoje.ecommerce.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pqnoje.ecommerce.dao.ProductDao;
import com.pqnoje.ecommerce.helper.ExceptionMessageHelper;
import com.pqnoje.ecommerce.helper.IllegalArgumentExceptionMessageHelper;
import com.pqnoje.ecommerce.helper.SQLExceptionMessagerHelper;
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

	@Override
	public void update(Product product, int productId) {
		ProductDao productDao = new ProductDao();
		productDao.update(product, productId);
	}

	@Override
	public void delete(int productId) throws Exception, SQLException {
		try {
			ProductDao productDao = new ProductDao();
			productDao.delete(productId);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw sqlException;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

	@Override
	public Product get(int productId) throws Exception, SQLException, IllegalArgumentException {
		try {
			ProductDao productDao = new ProductDao();
			Optional<Product> product = productDao.get(productId);
			if (!product.isPresent()) {
				throw new IllegalArgumentException("Product ID not found. Product ID: " + productId);
			}
			return product.get();
		} catch (SQLException sqlException) {
			SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			throw sqlException;
		} catch (IllegalArgumentException illegalArgumentException) {
			IllegalArgumentExceptionMessageHelper.printStackMessageErrors(illegalArgumentException);
			throw illegalArgumentException;
		} catch (Exception exception) {
			ExceptionMessageHelper.printStackMessageErrors(exception);
			throw exception;
		}
	}

	@Override
	public List<Product> getAll() throws Exception, SQLException {
		try {
			ProductDao productDao = new ProductDao();
			List<Product> products = productDao.getAll();
			return products;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			throw sqlException;
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

}
