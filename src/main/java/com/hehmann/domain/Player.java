package com.hehmann.domain;

import org.json.simple.JSONObject;

public class Player {
	private Integer id;
	private String name;
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object other){
		if(!other.getClass().equals(this.getClass()))
			return false;
		return this.getName().equals(((Player)other).getName());
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject response = new JSONObject();
		response.put("name", getName());
		return response;
	}
}
