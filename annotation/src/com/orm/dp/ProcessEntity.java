package com.orm.dp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class ProcessEntity {

	public static String getSelectQuery(Class clazz, long id) throws Exception {

		String sqlQuery = " SELECT * FROM ";

		if (clazz.isAnnotationPresent(Table.class)) {
			Annotation annotation = clazz.getAnnotation(Table.class);
			Table tableInfo = (Table) annotation;
			sqlQuery = sqlQuery + tableInfo.name() + " WHERE ID =  ";

		}
		sqlQuery = sqlQuery + id + " ";
		return sqlQuery;
	}

	public static String getDeleteQuery(Object obj) throws Exception {
		Class clazz = obj.getClass();
		String sqlQuery = " DELETE FROM ";

		if (clazz.isAnnotationPresent(Table.class)) {
			Annotation annotation = clazz.getAnnotation(Table.class);
			Table tableInfo = (Table) annotation;
			sqlQuery = sqlQuery + tableInfo.name() + " WHERE ID =  ";
		}

		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				Annotation annotation = field.getAnnotation(Column.class);
				Column column = (Column) annotation;
				String fieldName = field.getName();
				String columnName = column.name();
				if (columnName.equalsIgnoreCase("ID")) {
					sqlQuery = sqlQuery + field.get(obj) + " ";
				}

			}
		}
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
				// System.out.println(columnName + "   " + fieldName);
			}
		}

		Enumeration<String> colNames = hashTableFields.keys();
		while (colNames.hasMoreElements()) {
			String colNameKey = colNames.nextElement();
			String fieldName = hashTableFields.get(colNameKey);
			// System.out.println(" Enumeration " + colNameKey + " Field Name  "
			// + fieldName);
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

			}
		}

		sqlQuery = sqlQuery.trim();
		sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 1);
		sqlQuery = sqlQuery + " ) ";
		sqlQuery = sqlQuery + " VALUES ( ";

		for (Field field : mockObject.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				// System.out.println("  field.getName() " + field.getName() +
				// "  " + field.getType() + "  " + field.get(mockObject));
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
