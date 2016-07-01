package com.hehmann.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hehmann.domain.Match;
import com.hehmann.domain.MatchType;
import com.hehmann.domain.Team;
import com.hehmann.domain.Tournament;

@Controller
@RequestMapping("/*/match/*")
public class MatchGeneratorController {
	TournamentController tc = TournamentController.getInstance();
	
	private List<Match> generateGroupMatches(List<Team> teams) {
		List<Match> matches = new ArrayList<Match>();
		for(int a = 0; a < teams.size() - 1; a++){
			for(int b = a + 1; b < teams.size(); b++) {
				matches.add(new Match(teams.get(a), teams.get(b), MatchType.GROUP, 0));
			}
		}
		return matches;
	}
	
	@RequestMapping(value = "/*/match/generate", method = RequestMethod.GET, 
			params = {"numtables", "matchmode"}) @SuppressWarnings("unchecked")
	public String generateMatch(Map<String, Object> model, HttpServletRequest request,
			@RequestParam(value = "numtables") int numTables, @RequestParam(value = "matchmode") int matchMode) {
		Tournament tournament = tc.getTournamentFromRequest(request);
		JSONObject response = new JSONObject();
		
		Set<Integer> teamIds = tc.getTournamentFromRequest(request).getTeamIds();
		List<Team> playerGroup = new ArrayList<Team>();
		for (Integer teamId : teamIds) {
			playerGroup.add(tournament.getTeam(teamId));
		}
		List<Match> matches = generateGroupMatches(playerGroup);
		response.put("matches", matches);
		
		response.put("status", "ok");
		response.put("numTables", numTables);
		response.put("matchMode", matchMode);
		model.put("content", response);
		return "data";
	}

}
