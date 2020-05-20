package com.orm.dp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class Driver {
	public static void main(String[] args) throws Exception {

		Student student = new Student();
		student.setName("Parkash");
		student.setId(1);
		ISession session = DBSesssion.getDBSession().openSession();
		session.save(student);
		session.close();

//		session = DBSesssion.getDBSession().openSession();
//		student = (Student) (session.get(Student.class, 1));
//		System.out.println(student.getName());
//		System.out.println(student.getId());
//		session.close();

//		session = DBSesssion.getDBSession().openSession();
//		session.delete(student);
//
//		session.close();

	}
}
