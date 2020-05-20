package com.orm.dp;

@Table(name = "STUDENT")
public class Student {

	@Column(name = "NAME")
	private String name;
	@Column(name = "ID")
	private Integer id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
