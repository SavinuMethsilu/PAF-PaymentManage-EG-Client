package com;

import java.sql.*;

public class Payment
{
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
 
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/paf","root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	public String readPayment()
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
 
			// Prepare the html table to be displayed
	        output = "<table border='1'><tr><th>Payment ID</th><th>Customer Name</th><th>Amount</th>"+ "<th>Card Number</th>" + "<th>Update</th><th>Remove</th></tr>";
	        
	        String query = "select * from payment";
	        
	        Statement stmt = con.createStatement();
	        
	        ResultSet rs = stmt.executeQuery(query);
	        
	        // iterate through the rows in the result set
	        while (rs.next())
	        {
	          String payID = Integer.toString(rs.getInt("payID")); 
	          String customerName = rs.getString("customerName");
	          String amount = rs.getString("amount");
	          String cardNumber = rs.getString("cardNumber");
	          
	          
	        // Add a row into the html table
	          
	          
	          output += "<td>" + customerName + "</td>";
	          output += "<td>" + amount + "</td>";
	          output += "<td>" + cardNumber + "</td>";
	         
	          
	          //buttons
	          output += "<td><input name='btnUpdate' " + " type='button' value='Update'></td>" + "<td><form method='post' action='Payment.jsp'>" + "<input name='btnRemove' " + " type='submit' value='Remove'>" + "<input name='payID' type='hidden' " + " value='" + payID + "'>" + "</form></td></tr>"; } con.close();
	          
	          //output += "<td><input name='btnUpdate' type='button' value='Update' "
	        		 // + "class='btnUpdate btn btn-secondary' data-payID='" + payID + "'></td>"
	        		//  + "<td><input name='btnRemove' type='button' value='Remove' "
	        		//  + "class='btnRemove btn btn-danger' data-payID='" + payID + "'></td></tr>";
	         
	        
	          //Complete the html table
	          output += "</table>";
	        
	        
		}
	        
	          catch (Exception e)
	          {
	            output = "Error while reading the payment details!! .";
	            System.err.println(e.getMessage());
	          }
	          return output;
	          }
	
	public String insertPayment(String customerName, String amount, String cardNumber)
	{
		String output = "";
		
		try
		{
		Connection con = connect();
		if (con == null)
		{
		return "Error while connecting to the database !!";
		}
		// create a prepared statement
		String query = "insert into payment(`payID`,`CustomerName`,`amount`,`cardNumber`)" + " values (?, ?, ?, ?)";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, customerName);
		preparedStmt.setString(3, amount);
		preparedStmt.setString(4, cardNumber);
		
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" +newPayment + "\"}";
		 }
		
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}";
			 System.err.println(e.getMessage());
		 }
		
		 return output;
		 
		 }
	
	public String updatePayment(String payID, String customerName, String amount, String cardNumber)
    
    { 
	 String output = "";
	 
	 try
	 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {
			 return "Error while connecting to the database for updating."; 
		 }
		 
		 // create a prepared statement
		 String query = "UPDATE payment SET customerName=?,amount=?,cardNumber=? WHERE payID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 
		 preparedStmt.setString(1, customerName);
		 preparedStmt.setString(2, amount);
		 preparedStmt.setString(3, cardNumber);
		 preparedStmt.setInt(4, Integer.parseInt(payID)); 
	 
		 // execute the statement
		 preparedStmt.execute(); 
	     con.close();
		
				 String newPayment = readPayment();
				 output = "{\"status\":\"success\", \"data\": \"" +
				 newPayment + "\"}";
		 }
			 
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";
			 System.err.println(e.getMessage());
		 }
		 
		return output;
	}
		
	public String deletePayment(String payID){

		String output = "";

		try
		{
			Connection con = connect();

			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement

			String query = "delete from payment where payID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setInt(1, Integer.parseInt(payID));

			// execute the statement

			preparedStmt.execute();

			con.close();
			 
			 String newPayment = readPayment();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newPayment + "\"}";
		 }
		 
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\": \"Error while deleting the Payment.\"}";
			 System.err.println(e.getMessage());
		 }
		 
		 return output;
		 
		 }
	}
	