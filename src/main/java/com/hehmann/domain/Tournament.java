package com.hehmann.domain;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

import com.hehmann.web.controller.exception.DuplicateValueException;
import com.hehmann.web.controller.exception.UnkownIdException;

public class Tournament {
	private int id;
	private String name;
	private TournamentStatus status;
	private Map<Integer, Team> teams = new HashMap<Integer, Team>();
	private int newTeamId = 0;
	private int newPlayerId = 0;
	private List<PrintWriter> listeners = new ArrayList<PrintWriter>();

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public TournamentStatus getStatus() {
		return status;
	}
	
	public void setStatus(TournamentStatus status) {
		this.status = status;
	}
	
	public Set<Integer> getTeamIds() {
		return teams.keySet();
	}
	
	public Team getTeam(Integer teamId) throws UnkownIdException {
		if(teams.containsKey(teamId))
			return teams.get(teamId);
		else
			throw new UnkownIdException();
	}
	
	public int createTeam(String teamName) throws DuplicateValueException {
		Team newTeam = new Team(newTeamId++, teamName + listeners.size());
		if(teams.containsValue(newTeam))
			throw new DuplicateValueException();
		teams.put(newTeam.getId(), newTeam);
		return newTeam.getId();
	}
	
	public void createPlayer(int teamId, String playerName) {
		teams.get(teamId).addPlayer(new Player(newPlayerId++, playerName));
	}
	
	public void removeTeam(Integer teamId) {
		teams.remove(teamId);
	}
	
	public int getNumberOfPlayers() {
		int total = 0;
		for (Integer teamId : teams.keySet()) {
			total += teams.get(teamId).getPlayers().size();
		}
		return total;
	}

	public Tournament(int id, String name){
		this.id = id;
		this.name = name;
		this.status = TournamentStatus.PREPARATION;
		this.createTeam("Ohne Team");
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject response = new JSONObject();
		Map<String, JSONObject> teamJSON = new HashMap<String, JSONObject>();
		for(Integer teamId : getTeamIds())
			teamJSON.put("team" + teamId, teams.get(teamId).toJSON());
		response.putAll(teamJSON);
		
		return response;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!other.getClass().equals(this.getClass()))
			return false;
		return this.getName().equals(((Tournament)other).getName());
	}

	public void registerListener(PrintWriter writer) {
		listeners.add(writer);
	}
}
