package com.orm.dp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class ProcessMockObject {

	// public static void main(String[] args) {
	// Class<MockObject> obj = MockObject.class;
	//
	// if (obj.isAnnotationPresent(Table.class)) {
	// Annotation annotation = obj.getAnnotation(Table.class);
	// Table tableInfo = (Table) annotation;
	// System.out.println("Table Name = " + tableInfo.name());
	// }
	//
	// for (Field method : obj.getDeclaredFields()) {
	// if (method.isAnnotationPresent(Column.class)) {
	// Annotation annotation = method.getAnnotation(Column.class);
	// Column test = (Column) annotation;
	// System.out.println("Column Name = " + test.name());
	// }
	// }
	//
	// }
	public static void main(String[] args) throws Exception {

		MockObject mockObject = new MockObject();

		mockObject.setId(139L);
		mockObject.setYd(138L);
		// mockObject.setName("  Dummy Name");

		if (mockObject.getClass().isAnnotationPresent(Table.class)) {
			Annotation annotation = mockObject.getClass().getAnnotation(Table.class);
			Table tableInfo = (Table) annotation;
			System.out.println("Table Name = " + tableInfo.name());
		}

		for (Field field : mockObject.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				Annotation annotation = field.getAnnotation(Column.class);
				Column test = (Column) annotation;
				System.out.println("  field.getName() " + field.getName() + "  " + field.getType() + "  " + field.get(mockObject));
				// System.out.println("Column Name = " + test.name() +
				// mockObject.getName());
			}
		}

	}

	public static String getSelectQuery(Class clazz, long id) throws Exception {

		// SELECT * FROM MOCKR WHERE ID = 1

		String sqlQuery = " SELECT * FROM ";

		if (clazz.isAnnotationPresent(Table.class)) {
			Annotation annotation = clazz.getAnnotation(Table.class);
			Table tableInfo = (Table) annotation;
			sqlQuery = sqlQuery + tableInfo.name() + " WHERE ID =  ";

		}
		sqlQuery = sqlQuery + id + " ";
		// Object obj = clazz.newInstance();
		// for (Field field : clazz.getDeclaredFields()) {
		// field.setAccessible(true);
		// if (field.isAnnotationPresent(Column.class)) {
		// Annotation annotation = field.getAnnotation(Column.class);
		// Column columnName = (Column) annotation;
		// String fieldName = field.getName();
		// System.out.println(columnName.name() + "   " + fieldName);
		// Field tempField = obj.getClass().getDeclaredField(fieldName);
		// tempField.setAccessible(true);
		// tempField.set(obj, value);
		// // sqlQuery = sqlQuery + " " + columnName.name() + " , ";
		// // System.out.println("  field.getName() " + field.getName() +
		// // "  " + field.getType() );
		// }
		// }

		/*
		 * Object obj = clazz.newInstance(); Field ydField =
		 * obj.getClass().getDeclaredField("yd"); Field nameField =
		 * obj.getClass().getDeclaredField("name"); ydField.setAccessible(true);
		 * nameField.setAccessible(true); ydField.setLong(obj, 239L);
		 * nameField.set(obj, "This is your new name");
		 * System.out.println("  Value =   " + ((MockObject) (obj)).getYd());
		 * System.out.println("  Value =   " + ((MockObject) (obj)).getName());
		 */

		// Object obj = clazz.newInstance();
		// Class[] paramString = new Class[1];
		// paramString[0] = String.class;
		// obj.getClass().getDeclaredMethod("setName" , paramString ).invoke(obj
		// , "Parkash" );
		// System.out.println(obj.getClass().getMethod("getName").invoke(obj));

		return sqlQuery;

	}

	public static Object makeObject(ResultSet rs, Class clazz) throws Exception {
		Object obj = clazz.newInstance();
		Hashtable<String, String> hashTableFields = new Hashtable<String, String>();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				Annotation annotation = field.getAnnotation(Column.class);
				Column column = (Column) annotation;
				String fieldName = field.getName();
				String columnName = column.name();
				hashTableFields.put(columnName, fieldName);
				System.out.println(columnName + "   " + fieldName);
			}
		}

		Enumeration<String> colNames = hashTableFields.keys();
		while (colNames.hasMoreElements()) {
			String colNameKey = colNames.nextElement();
			String fieldName = hashTableFields.get(colNameKey);
			System.out.println(" Enumeration " + colNameKey + " Field Name  " + fieldName);
			Field tempField = obj.getClass().getDeclaredField(fieldName);
			tempField.setAccessible(true);
			Object tempParam = rs.getObject(colNameKey);
			tempField.set(obj, tempParam);
		}

		return obj;
	}

	public static String getInsertQuery(Object mockObject) throws Exception {
		String sqlQuery = " INSERT INTO ";

		if (mockObject.getClass().isAnnotationPresent(Table.class)) {
			Annotation annotation = mockObject.getClass().getAnnotation(Table.class);
			Table tableInfo = (Table) annotation;
			sqlQuery = sqlQuery + tableInfo.name() + " ( ";

		}

		for (Field field : mockObject.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				Annotation annotation = field.getAnnotation(Column.class);
				Column columnName = (Column) annotation;
				sqlQuery = sqlQuery + " " + columnName.name() + " , ";
				// System.out.println("  field.getName() " + field.getName() +
				// "  " + field.getType() + "  " + field.get(mockObject));
			}
		}

		sqlQuery = sqlQuery.trim();
		sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 1);
		sqlQuery = sqlQuery + " ) ";
		sqlQuery = sqlQuery + " VALUES ( ";

		for (Field field : mockObject.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				System.out.println("  field.getName() " + field.getName() + "  " + field.getType() + "  " + field.get(mockObject));
				if (field.getType().toString().trim().equalsIgnoreCase("class java.lang.String")) {
					sqlQuery = sqlQuery + "'" + field.get(mockObject) + "'" + " , ";
				} else {
					sqlQuery = sqlQuery + field.get(mockObject) + " , ";
				}

			}
		}
		sqlQuery = sqlQuery.trim();
		sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 1);
		sqlQuery = sqlQuery + " ) ";

		return sqlQuery;
	}
}
