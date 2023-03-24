package com.cathay.spring.files.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_data")
public class MyEntity {

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "checked")
	private boolean checked;

	public MyEntity() {

	}

	public MyEntity(String title, String description, boolean checked) {
		this.title = title;
		this.description = description;
		this.checked = checked;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean checked() {
		return checked;
	}

	public void setchecked(boolean ischecked) {
		this.checked = ischecked;
	}

	@Override
	public String toString() {
		return "data [id=" + id + ", title=" + title + ", desc=" + description + ", checked=" + checked + "]";
	}

}
