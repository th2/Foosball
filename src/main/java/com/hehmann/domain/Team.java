package com.hehmann.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public class Team {
	private Integer id;
	private String name;
	private List<Player> players;
	
	public Team(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.players = new ArrayList<Player>();
	}
	
	public Integer getId() {
		return id;
	}
	
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
		if(id != 0 && players.size() > 2)
			return false;
		if(players.contains(player))
			return false;
		
		players.add(player);
		return true;
	}
	
	public boolean removePlayer(Player player) {
		return players.remove(player);
	}

	@Override
	public boolean equals(Object other) {
		if(!other.getClass().equals(this.getClass()))
			return false;
		return this.getName().equals(((Team)other).getName());
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject response = new JSONObject();
		response.put("id", getId());
		response.put("name", getName());

		Map<String, JSONObject> playerJSON = new HashMap<String, JSONObject>();
		for (Player player : getPlayers()) {
			playerJSON.put("player" + player.getId(), player.toJSON());
		}
		response.put("players", playerJSON);
		
		return response;
	}

	public int getHighestPlayerId() {
		int max = 0;
		for (Player player : players) {
			if (player.getId() > max)
				max = player.getId();
		}
		return max;
	}
}
