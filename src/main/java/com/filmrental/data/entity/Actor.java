package com.filmrental.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actor")
public class Actor {
	
	@Id
	private String id;

	private String actorId;
	
	private String firstName;

	private String lastName;

	private String lastUpdate;
	
	public Actor() {
		
	}
	
	public Actor(String actorId, String firstName, String lastName, String lastUpdate) {
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastUpdate = lastUpdate;
	}

	public String  getId() {
		return id;
	}

	public void setId(String  id) {
		this.id = id;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	
	public String getActorId() {
		return actorId;
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

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


}
