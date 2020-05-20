package com.orm.dp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class DBSesssion {

	private static DBSesssion dbSession = null;
	private SQLServerDataSource dataSource;

	public DBSesssion() {
		initializeDataSource();
	}

	private void initializeDataSource() {
		try {
			dataSource = new SQLServerDataSource();
			dataSource.setUser("sa");
			dataSource.setPassword("parjan");
			dataSource.setServerName("localhost");
			dataSource.setPortNumber(1433);
			dataSource.setDatabaseName("JAVA_BRAIN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DBSesssion getDBSession() {

		if (dbSession == null) {
			dbSession = new DBSesssion();
		}

		return dbSession;
	}

	public void save1(Object object) {
		try {
			Connection connection = dataSource.getConnection();
			Statement insertStatement = connection.createStatement();
			String sqlQuery = "INSERT INTO MOCK ( ID , NAME ) VALUES (02133222 , 'MockObject-031')";
			insertStatement.execute(sqlQuery);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void select1(Object object) {
		try {
			Connection connection = dataSource.getConnection();
			Statement selectStatement = connection.createStatement();
			String sqlQuery = "SELECT * FROM MOCKR WHERE ID = 1";
			selectStatement.execute(sqlQuery);
			ResultSet rs = selectStatement.executeQuery(sqlQuery);
			while (rs.next()) {

				String YD = rs.getString("YD");
				String NAME = rs.getString("NAME");

				//System.out.println("YD : " + YD);
				//System.out.println("NAME : " + NAME);

			}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void select(Class clazz, long id) {
		try {
			String selectQuery = ProcessMockObject.getSelectQuery(clazz, id);
			//System.out.println(selectQuery);

			Connection connection = dataSource.getConnection();
			Statement selectStatement = connection.createStatement();
			ResultSet rs = selectStatement.executeQuery(selectQuery);
			if (rs.next()) {
				MockObject mockObj = null;
				mockObj = ((MockObject) (ProcessMockObject.makeObject(rs, clazz)));
				//System.out.println(" mockObj.getName() " + mockObj.getName());
				//System.out.println(" mockObj.getYd() " + mockObj.getYd());

			}
			// Object aa = ((MockObject) (ProcessMockObject.makeObject(rs,
			// clazz)));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void saveObject(Object object) {
		try {
			Connection connection = dataSource.getConnection();
			Statement insertStatement = connection.createStatement();
			String sqlQuery = ProcessMockObject.getInsertQuery(object);
			insertStatement.execute(sqlQuery);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {

		DBSesssion.getDBSession().select(MockObject.class, 1L);

		// MockObject mockObject = new MockObject();
		// mockObject.setId(1);
		// mockObject.setYd(2);
		// mockObject.setName("I am Mock Object");
		// DBSesssion.getDBSession().save(mockObject);
		// DBSesssion.getDBSession().get(MockObject.class, 1L);

	}

	private Connection getConnection() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public ISession openSession() {
		return new ISession() {
			private Connection anny_connection = getConnection();

			@Override
			public void save(Object object) throws Exception {
				Statement insertStatement = anny_connection.createStatement();
				String sqlQuery = ProcessEntity.getInsertQuery(object);
				insertStatement.execute(sqlQuery);
			}

			@Override
			public Object get(Class clazz, Integer id) throws Exception {
				String selectQuery = ProcessEntity.getSelectQuery(clazz, id);
				//System.out.println(selectQuery);

				Connection connection = dataSource.getConnection();
				Statement selectStatement = connection.createStatement();
				ResultSet rs = selectStatement.executeQuery(selectQuery);
				Object obj = null;
				if (rs.next()) {
					obj =  ProcessEntity.makeObject(rs, clazz);
				}
				return obj;
			}

			@Override
			public void delete(Object object) throws Exception {
			   
				Statement deleteStatement = anny_connection.createStatement();
				String sqlQuery = ProcessEntity.getDeleteQuery(object );
				//System.out.println(sqlQuery);
				deleteStatement.execute(sqlQuery);    
			}
			@Override
			public void close() {
				try {
					if (anny_connection != null) {
						anny_connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
	}
}
