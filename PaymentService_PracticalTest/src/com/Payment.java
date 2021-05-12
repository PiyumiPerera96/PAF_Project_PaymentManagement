package com;

import java.sql.*;

public class Payment 
{

			//CONNECTION
			public Connection connect()
			{
					Connection con = null;

					try
					{
							Class.forName("com.mysql.jdbc.Driver");
							con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment",	"root", "");
			
					}
					catch(Exception e)
					{
							e.printStackTrace();
					}

					return con;
			}
			
			
			
			
			//READ
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
							
							// Prepare the HTML table to be displayed
							output = "<table  class='table table-dark table-striped'><tr><th>Payment Code</th>"
									+"<th>Payment Type</th><th>Total Price</th>"
									+ "<th>Payment Status</th>"
									+ "<th>Date</th>"
									+ "<th>Time</th>"
									+ "<th>Edit</th><th>Delete</th></tr>";

							String query = "select * from payments";
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery(query);

							// iterate through the rows in the result set
							while (rs.next())
							{
									String PaymentID = Integer.toString(rs.getInt("PaymentID"));
									String PaymentCode = rs.getString("PaymentCode");
									String PaymentType = rs.getString("PaymentType");
									String TotalPrice = Double.toString(rs.getDouble("TotalPrice"));
									String PaymentStatus = rs.getString("PaymentStatus");
									String Date = rs.getString("Date");
									String Time = rs.getString("Time");

									// Add a row into the HTML table
									output += "<tr><td>" + PaymentCode + "</td>";
									output += "<td>" + PaymentType + "</td>";
									output += "<td>" + TotalPrice + "</td>"; 
									output += "<td>" + PaymentStatus + "</td>";
									output += "<td>" + Date + "</td>"; 
									output += "<td>" + Time + "</td>";
				

									// buttons
									output += "<td><input name='btnUpdate' type='button' value='Edit' class='btnUpdate btn btn-secondary' data-paymentid='" + PaymentID + "'></td>"
											+"<td><input name='btnRemove' type='button' value='Delete' class='btnRemove btn btn-danger' data-paymentid='" + PaymentID + "'>" + "</td></tr>";
							}

							con.close();

							// Complete the HTML table
							output += "</table>";
					}
					catch (Exception e)
					{
							output = "Error while reading the payments.";
							System.err.println(e.getMessage());
					}
					
					return output;
			}
			
			
			
			

			//INSERT
			public String insertPayment(String code, String type, String tprice, String status, String date, String time)
			{
					String output = "";
					
					try
					{
							Connection con = connect();
							
							if (con == null)
							{
									return "Error while connecting to the database for inserting";
							}

							// create a prepared statement
							String query = " insert into payments (`PaymentID`,`PaymentCode`,`PaymentType`,`TotalPrice`,`PaymentStatus`,`Date`,`Time`) values (?, ?, ?, ?, ?, ?, ?)";
							
							PreparedStatement preparedStmt = con.prepareStatement(query);

							// binding values
							preparedStmt.setInt(1, 0);
							preparedStmt.setString(2, code);
							preparedStmt.setString(3, type);
							preparedStmt.setDouble(4, Double.parseDouble(tprice));
							preparedStmt.setString(5, status);
							preparedStmt.setString(6, date);
							preparedStmt.setString(7, time);

							//execute the statement
							preparedStmt.execute();
							con.close();

							String newPayments = readPayment();
							output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
					}
					catch (Exception e)
					{
								output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}";
								System.err.println(e.getMessage());
					}
					
					return output;
			}
			

			
			//UPDATE
			public String updatePayment(String ID,String code, String type, String tprice, String status, String date, String time)
			{
					String output = "";
					
					try
					{
							Connection con = connect();
							
							if (con == null)
							{
									return "Error while connecting to the database for updating";
							}

							// create a prepared statement
							String query = "UPDATE payments SET PaymentCode=?, PaymentType=?, TotalPrice=?, PaymentStatus=?, Date=?, Time=? WHERE PaymentID=?";
							
							PreparedStatement preparedStmt = con.prepareStatement(query);

							// binding values
							preparedStmt.setString(1, code);
							preparedStmt.setString(2, type);
							preparedStmt.setDouble(3, Double.parseDouble(tprice));
							preparedStmt.setString(4, status);
							preparedStmt.setString(5, date);
							preparedStmt.setString(6, time);
							preparedStmt.setInt(7, Integer.parseInt(ID));

							//execute the statement
							preparedStmt.executeUpdate();
							con.close();

							String newPayments = readPayment();
							output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
			
					}
					catch (Exception e)
					{
							output = "{\"status\":\"error\", \"data\":\"Error while updating the payment.\"}";
							System.err.println(e.getMessage());
					}
					
					return output;
			}
			
			

			//DELETE
			public String deletePayment(String PaymentID)
			{
					String output = "";
					
					try
					{
							Connection con = connect();
							
							if (con == null)
							{
									return "Error while connecting to the database for deleting";
							}

							// create a prepared statement
							String query = "DELETE from payments where PaymentID=?";
							
							PreparedStatement preparedStmt = con.prepareStatement(query);

							// binding values
							preparedStmt.setInt(1, Integer.parseInt(PaymentID));

							//execute the statement
							preparedStmt.executeUpdate();
							con.close();

							String newPayments = readPayment();
							output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
					}
					catch (Exception e)
					{
						output = "{\"status\":\"error\", \"data\":\"Error while deleting the payment.\"}";
						System.err.println(e.getMessage());
					}
					
					return output;
			}

	
}
