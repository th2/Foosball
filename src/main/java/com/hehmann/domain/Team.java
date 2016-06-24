package com.hehmann.domain;

import java.util.List;

public class Team {
	private String name;
	private List<Player> players;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public boolean addPlayer(Player player) {
		if(players.size() > 2)
			return false;
		if(players.contains(player))
			return false;
		
		players.add(player);
		return true;
	}
	public boolean removePlayer(Player player) {
		return players.remove(player);
	}
}
