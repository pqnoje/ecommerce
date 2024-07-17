package com.pqnoje.ecommerce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import com.pqnoje.ecommerce.configuration.DatabaseConfiguration;
import com.pqnoje.ecommerce.model.Shelf;

public class ShelfDao implements Dao<Shelf> {

	@Override
	public Optional<Shelf> get(int id)  throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Shelf> getAll()  throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shelf save(Shelf shelf) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DatabaseConfiguration.getProperties().getUrl(),
					DatabaseConfiguration.getProperties().getUsername(),
					DatabaseConfiguration.getProperties().getPassword());

			String sql = """
					INSERT
						INTO
						shelfs (name,
						description)
					VALUES (?,
					?);
										""";
			PreparedStatement preparedStmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, shelf.getName());
			preparedStmt.setString(2, shelf.getDescription());

			int affectedRows = preparedStmt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating shelf failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					shelf.setShelfId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating shelf failed, no ID obtained.");
				}
			}

			preparedStmt.close();
			connection.close();

		} catch (Exception exception) {
			System.out.println(exception);
		}

		return shelf;
	}

	@Override
	public void update(Shelf t, int shelfId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int t) {
		// TODO Auto-generated method stub

	}

}
