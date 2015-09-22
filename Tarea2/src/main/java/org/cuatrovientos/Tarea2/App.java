package org.cuatrovientos.Tarea2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    		throws ClassNotFoundException, SQLException {
    	
    	Class.forName("org.sqlite.JDBC");
    	Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");
    	Statement statement = connection.createStatement();		
    	PreparedStatement preparedStatement;
    	
    	String option;
    	Scanner reader = new Scanner(System.in);
    	String sql;
    	String id;
		String name;
    	/*
    	 * To create the DB.
    	 * String SQL ="create table customers (id integer, name varchar(40))";
    	  statement.executeUpdate(SQL);
    	 */
    	
    	
    	do{
    		System.out.println("Choose an option:");
    		System.out.println("1. Show all the records.");
    		System.out.println("2.Insert a ner recordr.");
    		System.out.println("3. Modify a record.");
    		System.out.println("4. Delete a record.");
    		System.out.println("5. Delete all the records.");
    		System.out.println("6. Exit.");
    		option = reader.nextLine();
    	
    	switch (Integer.parseInt(option)){
    	
    	case 1:		
    	sql="select * from customers";
    	ResultSet result = statement.executeQuery(sql);
    	while (result.next()) {
			//System.out.print("ID: " + resultSet.getString(1));
			//System.out.println(" Name: " + resultSet.getString(2));
			System.out.print("ID: " + result.getInt("id"));
			System.out.println(" Name: " + result.getString("name"));
		}
    	result.close();
    		break;
    		
    	case 2:		
    		
    		System.out.println("Please enter the ID of the customer:");
    		id = reader.nextLine();
    		System.out.println("Please enter the name of the customer:");
    		name = reader.nextLine();
    		preparedStatement = connection.prepareStatement("insert into customers values (?,?)");
    		preparedStatement.setInt(1,Integer.parseInt(id));
    		preparedStatement.setString(2,name);
    		preparedStatement.addBatch();
    		preparedStatement.executeBatch();
    		
    		break;
    		
    	case 3:	
    		String change;
    		System.out.println("Enter the id of a customer for do the changed:");
    		change = reader.nextLine();
    		System.out.println("Please enter the ID of the customer:");
    		id = reader.nextLine();
    		System.out.println("Please enter the name of the customer:");
    		name = reader.nextLine();
    		preparedStatement = connection.prepareStatement("update customers set id=(?), name=(?) where id =(?)");
    		preparedStatement.setInt(1,Integer.parseInt(id));
    		preparedStatement.addBatch();
    		preparedStatement.setString(2,name);
    		preparedStatement.addBatch();
    		preparedStatement.setString(3,change);
    		preparedStatement.addBatch();
    		preparedStatement.executeBatch();
    		break;
    		
    	case 4:	
    		String delete;
    		System.out.println("Enter the id of a customer for delete it:");
    		delete = reader.nextLine();
    		preparedStatement = connection.prepareStatement("delete from customers where id=(?)");
    		preparedStatement.setInt(1,Integer.parseInt(delete));
    		preparedStatement.addBatch();
    		preparedStatement.executeBatch();
    		break;
    		
    	case 5:
    		sql="delete from customers";
    		statement.executeUpdate(sql);
    		break;
    		
    	case 6:
    		System.out.println("Goodby");
    		break;
    		
    	default:	
    		System.out.println("Incorrect option.");
    		break;
    	} 	
    	}while(Integer.parseInt(option) !=6);
    	connection.close();
    }
}
