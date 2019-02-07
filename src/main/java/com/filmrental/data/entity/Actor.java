package com.filmrental.data.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Actor {
	
	@Id
	private String id;
	
	private int	actorId;
	
	private String firstName;

	private String lastName;

	private Date lastUpdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getActor_id() {
		return actorId;
	}

	public void setActor_id(int actor_id) {
		this.actorId = actor_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


}
