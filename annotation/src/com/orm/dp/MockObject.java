package com.orm.dp;

@Table(name = "MOCKR")
public class MockObject {

	@Column(name = "ID")
	private long id;

	@Column(name = "YD")
	private long yd;

	@Column(name = "NAME")
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getYd() {
		return yd;
	}

	public void setYd(long yd) {
		this.yd = yd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
