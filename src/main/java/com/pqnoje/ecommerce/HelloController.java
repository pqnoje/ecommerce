package com.pqnoje.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		
		Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ecommerce",
                "root", "$Fate4ever");
 
            // mydb is database
            // mydbuser is name of database
            // mydbuser is password of database
 
            String sql = "insert into products (name, description)"
            		+ "values (?, ?);";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, "Camiseta de manga longa.");
            preparedStmt.setString (2, "Camiseta cor de rosa.");
            
            preparedStmt.execute();
            
            preparedStmt.close();
            connection.close();
            
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
		
		return "Greetings from Spring Boot!";
	}

}
