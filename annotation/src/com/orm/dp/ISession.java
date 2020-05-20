package com.orm.dp;

public interface ISession {
	
	public void save(Object object) throws Exception;
	public Object get(Class clazz, Integer id) throws Exception;
	public void delete(Object object) throws Exception;
	public void close();
	
}
