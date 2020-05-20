package com.orm.dp;

import java.util.ArrayList;
import java.util.List;

@Table(name = "PAFKIET_COURSE")
public class PAFKietCourse {

	@Column(name = "ID")
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Child
	private List<PAFKietStudent> students = new ArrayList<PAFKietStudent>();

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

	public List<PAFKietStudent> getStudents() {
		return students;
	}

	public void setStudents(List<PAFKietStudent> students) {
		this.students = students;
	}

}
