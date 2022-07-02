package com.example.wt2.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="todos")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters =true)
public class TodoItems {
	@Id
	@GeneratedValue
	private long id;
	@NotNull
	private String title;
	
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAT = new Date();
	
	@Column(columnDefinition = "INTEGER DEFAULT '0'")
	private boolean completed;
	
	@NotNull
	@ManyToOne
	private User owner;
	

	
	

	//Setters & Getters
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

	public Date getCreatedAT() {
		return createdAT;
	}

	public void setCreatedAT(Date createdAT) {
		this.createdAT = createdAT;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@Override
	public String toString() {
		return String.format(
				"ToDo[id=%s, title='%s', completed='%s']", id, title, completed);
	}
	
	public TodoItems() {
		super();
	}
	
	
}
