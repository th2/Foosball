package com.hehmann.web.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hehmann.domain.Tournament;
import com.hehmann.web.controller.exception.UnkownIdException;

public class TournamentController {
	private static TournamentController tc;
	private Map<Integer, Tournament> tournaments = new HashMap<Integer, Tournament>();
	private int newTournamentId = 0;
	
	public static TournamentController getInstance(){
		if(tc == null)
			tc = new TournamentController();
		return tc;
	}
	
	public Set<Entry<Integer, Tournament>> getTournamentsList() {
		return tournaments.entrySet();
	}
	
	public Tournament getTournament(Integer tournamentId) throws UnkownIdException {
		if(tournaments.containsKey(tournamentId))
			return tournaments.get(tournamentId);
		else
			throw new UnkownIdException();
	}

	public boolean createTournament(String name) {
		if(!tournaments.containsKey(name)) {
			tournaments.put(newTournamentId, new Tournament(newTournamentId, name));
			newTournamentId++;
			return true; 
		}
		return false;
	}
}
