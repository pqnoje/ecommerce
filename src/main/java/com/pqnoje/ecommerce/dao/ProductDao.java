package com.pqnoje.ecommerce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pqnoje.ecommerce.configuration.DatabaseConfiguration;
import com.pqnoje.ecommerce.model.Product;
import com.pqnoje.ecommerce.model.Shelf;

public class ProductDao implements Dao<Product> {

	@Override
	public Optional<Product> get(long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Product> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product save(Product product) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DatabaseConfiguration.getProperties().getUrl(),
					DatabaseConfiguration.getProperties().getUsername(),
					DatabaseConfiguration.getProperties().getPassword());

			String sql = """
					INSERT
						INTO
						products (name,
						description)
					VALUES (?, 
					?);
										""";
			PreparedStatement preparedStmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, product.getName());
			preparedStmt.setString(2, product.getDescription());

			int affectedRows = preparedStmt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating product failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					product.setProductId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating product failed, no ID obtained.");
				}
			}

			preparedStmt.close();
			connection.close();

		} catch (Exception exception) {
			System.out.println(exception);
		}

		return product;
	}

	@Override
	public void update(Product product, String[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Product product) {
		// TODO Auto-generated method stub

	}

	public void associateToShelfs(List<Shelf> shelfs, int productId) {

		shelfs.stream().forEach(shelf -> {
			try {
				Connection connection = null;
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(DatabaseConfiguration.getProperties().getUrl(),
						DatabaseConfiguration.getProperties().getUsername(),
						DatabaseConfiguration.getProperties().getPassword());
				String sql = """
						INSERT
							INTO
							products_shelfs (product_id,
							shelf_id)
						VALUES (?,
						?);
												""";
				PreparedStatement preparedStmt = connection.prepareStatement(sql);
				preparedStmt.setInt(1, productId);
				preparedStmt.setInt(2, shelf.getShelfId());

				preparedStmt.executeUpdate();

				preparedStmt.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	public List<Product> listProductsByShelfId(int shelfId) {
		List<Product> products = new ArrayList<Product>();
		Connection connection = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DatabaseConfiguration.getProperties().getUrl(),
					DatabaseConfiguration.getProperties().getUsername(),
					DatabaseConfiguration.getProperties().getPassword());

			String sql = """
					SELECT
						p.product_id,
						p.name,
						p.description
					FROM
						products p
					INNER JOIN products_shelfs ps
					ON
						ps.product_id = p.product_id
					WHERE
						ps.shelf_id = ?;
										""";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, shelfId);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setProductId(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setDescription(resultSet.getString(3));
				products.add(product);
			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			System.out.println(exception);
		}

		return products;
	}

}
