package com.ibm.bluemix.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.db2.jcc.DB2Connection;
import com.ibm.db2.jcc.DB2SimpleDataSource;

public class DataService {
	
	String databaseHost="";
	int port = 0;
	String databaseName = "";
	String username="";
	String password="";
	String plan = "";
	
	public DataService() {
		setup();
	}
	
	void setup() {
		
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
        String Service_Name  = "sqldb";
 
        // Get the Service Credentials for DB2 SQL Database
        if (VCAP_SERVICES != null) {
            
            try {
            	JSONObject obj = new JSONObject(VCAP_SERVICES);
                JSONArray service = obj.getJSONArray(Service_Name);
 
                // retrieve the service information             
                JSONObject catalog = service.getJSONObject(0);
                
                plan = catalog.getString("plan");
 
                // retrieve the credentials
                JSONObject credentials = catalog.getJSONObject("credentials");
 
                // get the credential contents
                databaseHost = credentials.getString("host");
                port = (int)credentials.get("port");
                databaseName = credentials.getString("db");
                username = credentials.getString("username");
                password = credentials.getString("password");
 
            } catch (NullPointerException | JSONException e) {          
                e.printStackTrace();                
            }
        }

	}
	
	public boolean saveMessages(MessageResponse param) {
		// Connect to the Database
		Connection con = null;
		try {
			DB2SimpleDataSource dataSource = new DB2SimpleDataSource();
			dataSource.setServerName(databaseHost);
			dataSource.setPortNumber(port);
			dataSource.setDatabaseName(databaseName);
			dataSource.setUser(username);
			dataSource.setPassword (password);
			dataSource.setDriverType(4);
			con=dataSource.getConnection();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("Error connecting to database");
			System.out.println("SQL Exception: " + e);
			return false;
		}
		
		String sqlStatement="";
		
		try {
			sqlStatement = "INSERT INTO FEEDBACK(UserInput,ClassLabel,Confidence) VALUES (?,?,?)";
			PreparedStatement db2Stmt = null;
			db2Stmt = con.prepareStatement(sqlStatement);
			db2Stmt.setString(1, param.getUserInput());
			db2Stmt.setString(2, param.getClassLabel());
			db2Stmt.setString(3, param.getConfidence());
			System.out.println("Executing: " + sqlStatement);
			db2Stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error executing:" + sqlStatement);
			System.out.println("SQL Exception: " + e);
			return false;
		}
		
		return true;
	}
	
	


}
