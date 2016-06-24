package com.hehmann.domain;

import java.util.List;

public class Player {
	private String name;
	List<Tournament> tournaments;
	
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
	
}
