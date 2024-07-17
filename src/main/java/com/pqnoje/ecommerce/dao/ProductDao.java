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
import com.pqnoje.ecommerce.helper.SQLExceptionMessagerHelper;
import com.pqnoje.ecommerce.model.Product;
import com.pqnoje.ecommerce.model.Shelf;

public class ProductDao implements Dao<Product> {

	@Override
	public Optional<Product> get(int productId) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Optional<Product> resultProduct = Optional.empty();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DatabaseConfiguration.getProperties().getUrl(),
					DatabaseConfiguration.getProperties().getUsername(),
					DatabaseConfiguration.getProperties().getPassword());

			String sql = """
							SELECT product_id, name, description
							FROM ecommerce.products
							WHERE product_id = ?;
					""";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();

				product.setProductId(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setDescription(resultSet.getString(3));
				resultProduct = Optional.of(product);
			}

		} catch (SQLException sqlException) {
			SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}

			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}

		}

		return resultProduct;
	}

	@Override
	public List<Product> getAll() throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		List<Product> products = new ArrayList<Product>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DatabaseConfiguration.getProperties().getUrl(),
					DatabaseConfiguration.getProperties().getUsername(),
					DatabaseConfiguration.getProperties().getPassword());

			String sql = """
							SELECT product_id, name, description
							FROM ecommerce.products;
					""";

			preparedStatement = connection.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();

				product.setProductId(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setDescription(resultSet.getString(3));

				products.add(product);
			}

		} catch (SQLException sqlException) {
			SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}

			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}

		}

		return products;
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
	public void update(Product product, int productId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DatabaseConfiguration.getProperties().getUrl(),
					DatabaseConfiguration.getProperties().getUsername(),
					DatabaseConfiguration.getProperties().getPassword());

			String sql = """
					UPDATE ecommerce.products
					SET name = ?, description = ?
					WHERE product_id = ?;
										""";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setInt(3, productId);

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating product failed, no rows affected.");
			}

		} catch (Exception exception) {
			System.out.println(exception);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}

			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}
		}
	}

	@Override
	public void delete(int productId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DatabaseConfiguration.getProperties().getUrl(),
					DatabaseConfiguration.getProperties().getUsername(),
					DatabaseConfiguration.getProperties().getPassword());

			String sql = """
					DELETE FROM ecommerce.products
					WHERE product_id = ?;;
										""";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException();
			}

		} catch (SQLException sqlException) {
			throw new SQLException("This product ID does not exists.");
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}

			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}
		}

	}

	public List<Product> listProductsByShelfId(int shelfId) {
		List<Product> products = new ArrayList<Product>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

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

			preparedStatement = connection.prepareStatement(sql);
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

		} catch (Exception exception) {
			System.out.println(exception);
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}

			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlException) {
				SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
			}
		}

		return products;
	}

	public void associateToShelfs(List<Shelf> shelfs, int productId) {

		shelfs.stream().forEach(shelf -> {

			Connection connection = null;
			PreparedStatement preparedStatement = null;
			
			try {
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
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, productId);
				preparedStatement.setInt(2, shelf.getShelfId());

				preparedStatement.executeUpdate();

				connection.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null) {
						preparedStatement.close();
					}
				} catch (SQLException sqlException) {
					SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
				}

				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException sqlException) {
					SQLExceptionMessagerHelper.printStackMessageErrors(sqlException);
				}
			}
		});

	}

}
