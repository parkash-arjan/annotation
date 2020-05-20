package com.orm.dp;

@Table(name="PAFKIET_STUDENT")
public class PAFKietStudent {

	@Column(name="ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
