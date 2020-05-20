package com.orm.dp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class TestConnection {
	public static void main(String[] args) throws Exception {
		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser("sa");
		ds.setPassword("parjan");
		ds.setServerName("localhost");
		ds.setPortNumber(1433);
		ds.setDatabaseName("JAVA_BRAIN");
		Connection con = ds.getConnection();
		
		  String SQL = " SELECT * FROM JB_PARENT";
		  Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(SQL);

	      while (rs.next()) {
	         System.out.println(rs.getString("PARENT_NAME") );
	      }
	      rs.close();
	      stmt.close();
		
	}
}
