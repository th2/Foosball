package com.hehmann.domain;

import java.util.List;

import org.json.simple.JSONObject;

public class Player {
	private Integer id;
	private String name;
	List<Tournament> tournaments;
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Tournament> getTournaments() {
		return tournaments;
	}
	
	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	@Override
	public boolean equals(Object other){
		if(!other.getClass().equals(this.getClass()))
			return false;
		return this.getName().equals(((Player)other).getName());
	}
	
	@SuppressWarnings("unchecked")
	public String toJSON() {
		JSONObject response = new JSONObject();
		response.put("id", getId());
		response.put("name", getName());
		return response.toString();
	}
}
