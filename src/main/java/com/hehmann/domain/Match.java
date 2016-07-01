package com.hehmann.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public class Match {
	private Team team1;
	private Team team2;
	private int score1;
	private int score2;
	private MatchType type;
	private int table;
	private boolean complete;
	
	public Match(Team team1, Team team2, MatchType type, int table) {
		this.team1 = team1;
		this.team2 = team2;
		this.type = type;
		this.table = table;
		this.complete = false;
	}
	
	public void setScore(int score1, int score2) {
		this.score1 = score1;
		this.score1 = score2;
		this.complete = true;
	}

	@Override @SuppressWarnings("unchecked")
	public String toString() {
		JSONObject response = new JSONObject();
		response.put("team1Id", team1.getId());
		response.put("team2Id", team2.getId());
		response.put("score1", score1);
		response.put("score2", score2);
		response.put("type", type);
		response.put("table", table);
		response.put("complete", complete);
		return response.toString();
	}
	
	
}
