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
 * @Author Aitor Jauregi
 * A simple program for administrate a data base.
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
    		option = showMenu(reader);
    	
    	switch (Integer.parseInt(option)){
    	
    	case 1:		
			showRecords(statement);
    		break;
    		
    	case 2:		
    		enterRecord(connection, reader);
    		break;
    		
    	case 3:	
    		changeRecord(connection, reader);
    		break;
    		
    	case 4:	
    		deleteRecord(connection, reader);
    		break;
    		
    	case 5:
    		deleteAll(statement);
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

	/**
	 * Delete all the records
	 * @param statement
	 * @throws SQLException
	 */
	private static void deleteAll(Statement statement) throws SQLException {
		String sql;
		sql="delete from customers";
		statement.executeUpdate(sql);
	}

	/**
	 * Delete one record
	 * @param connection
	 * @param reader
	 * @throws SQLException
	 */
	private static void deleteRecord(Connection connection, Scanner reader)
			throws SQLException {
		PreparedStatement preparedStatement;
		String delete;
		System.out.println("Enter the id of a customer for delete it:");
		delete = reader.nextLine();
		preparedStatement = connection.prepareStatement("delete from customers where id=(?)");
		preparedStatement.setInt(1,Integer.parseInt(delete));
		preparedStatement.addBatch();
		preparedStatement.executeBatch();
	}

	/**
	 * Change one record
	 * @param connection
	 * @param reader
	 * @throws SQLException
	 */
	private static void changeRecord(Connection connection, Scanner reader)
			throws SQLException {
		PreparedStatement preparedStatement;
		String id;
		String name;
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
	}

	/**
	 * Enter one record
	 * @param connection
	 * @param reader
	 * @throws SQLException
	 */
	private static void enterRecord(Connection connection, Scanner reader)
			throws SQLException {
		PreparedStatement preparedStatement;
		String id;
		String name;
		System.out.println("Please enter the ID of the customer:");
		id = reader.nextLine();
		System.out.println("Please enter the name of the customer:");
		name = reader.nextLine();
		preparedStatement = connection.prepareStatement("insert into customers values (?,?)");
		preparedStatement.setInt(1,Integer.parseInt(id));
		preparedStatement.setString(2,name);
		preparedStatement.addBatch();
		preparedStatement.executeBatch();
	}

	/**
	 * Show all records
	 * @param statement
	 * @throws SQLException
	 */
	private static void showRecords(Statement statement) throws SQLException {
		String sql;
		sql="select * from customers";
    	ResultSet result = statement.executeQuery(sql);
    	while (result.next()) {
			//System.out.print("ID: " + resultSet.getString(1));
			//System.out.println(" Name: " + resultSet.getString(2));
			System.out.print("ID: " + result.getInt("id"));
			System.out.println(" Name: " + result.getString("name"));
		}
    	result.close();
	}

	/**
	 * Show a menu
	 * @param reader
	 * @return
	 */
	private static String showMenu(Scanner reader) {
		String option;
		System.out.println("Choose an option:");
		System.out.println("1. Show all the records.");
		System.out.println("2.Insert a new recordr.");
		System.out.println("3. Modify a record.");
		System.out.println("4. Delete a record.");
		System.out.println("5. Delete all the records.");
		System.out.println("6. Exit.");
		option = reader.nextLine();
		return option;
	}
}
