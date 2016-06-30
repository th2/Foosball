package com.hehmann.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

public class Team {
	private Integer id;
	private String name;
	private Map<Integer, Player> players;
	
	public Team(Integer id, String name) {
		this.id = id;
		this.name = name;
		this.players = new HashMap<Integer, Player>();
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

	public Set<Integer> getPlayerIds() {
		return players.keySet();
	}
	
	public Player getPlayer(int playerId) {
		return players.get(playerId);
	}
	
	public boolean addPlayer(Player player) {
		if(id != 0 && players.size() > 2)
			return false;
		if(players.containsValue(player))
			return false;
		
		players.put(player.getId(), player);
		return true;
	}
	
	public Player deletePlayer(int playerId) {
		return players.remove(playerId);
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
		response.put("size", players.keySet().size());

		Map<String, JSONObject> playerJSON = new HashMap<String, JSONObject>();
		for (Integer playerId : players.keySet()) {
			playerJSON.put("player" + playerId, players.get(playerId).toJSON());
		}
		response.put("players", playerJSON);
		
		return response;
	}

	public int getHighestPlayerId() {
		int max = 0;
		for (int playerId : players.keySet()) {
			if (playerId > max)
				max = playerId;
		}
		return max;
	}
}
