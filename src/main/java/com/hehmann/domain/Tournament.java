package com.hehmann.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hehmann.web.controller.exception.DuplicateValueException;
import com.hehmann.web.controller.exception.UnkownIdException;

public class Tournament {
	private int id;
	private String name;
	private TournamentStatus status;
	private Map<Integer, Team> teams = new HashMap<Integer, Team>();

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
	
	public void addTeam(Team team) throws DuplicateValueException {
		if(teams.containsKey(team.getId()) || teams.containsValue(team))
			throw new DuplicateValueException();
		teams.put(team.getId(), team);
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
	
	public int getHighestTeamId() {
		int max = 0;
		for (Integer teamId : teams.keySet()) {
			if (teamId > max)
				max = teamId;
		}
		return max;
	}
	
	public int getHighestPlayerId() {
		int max = 0;
		for (Integer teamId : teams.keySet()) {
			int maxPlayerId = teams.get(teamId).getHighestPlayerId();
			if (maxPlayerId > max)
				max = maxPlayerId;
		}
		return max;
	}

	public Tournament(int id, String name){
		this.id = id;
		this.name = name;
		this.status = TournamentStatus.PREPARATION;
		this.addTeam(new Team(0, "Ohne Team"));
	}
	
	@Override
	public boolean equals(Object other) {
		if(!other.getClass().equals(this.getClass()))
			return false;
		return this.getName().equals(((Tournament)other).getName());
	}
}
