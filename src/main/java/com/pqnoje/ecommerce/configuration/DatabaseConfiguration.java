package com.pqnoje.ecommerce.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.pqnoje.ecommerce.model.Database;

public class DatabaseConfiguration {

	public static Database getProperties() {

		Database databaseConfiguration = new Database();

		try (InputStream input = new FileInputStream("src/resources/configuration.properties")) {

			Properties prop = new Properties();

			prop.load(input);

			databaseConfiguration.setPassword(prop.getProperty("db.password"));
			databaseConfiguration.setUrl(prop.getProperty("db.url"));
			databaseConfiguration.setUsername(prop.getProperty("db.username"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return databaseConfiguration;
	}

}
