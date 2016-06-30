package com.hehmann.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hehmann.domain.Team;
import com.hehmann.domain.Tournament;
import com.hehmann.web.controller.exception.UnkownIdException;

@Controller
@RequestMapping("/")
public class FoosballController {
	TournamentController tc = TournamentController.getInstance();
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String showTournamentList(@RequestParam(value="newTournament", required=true) String newTournamentName, Map<String, Object> model) {			
		if(!tc.createTournament(newTournamentName))
			model.put("message", "Turnier mit Namen \"" + newTournamentName + "\" existiert bereits.");
		return showTournamentList(model);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showTournamentList(Map<String, Object> model) {
		model.put("tournamentList", tc.getTournamentsList());
		return "tournamentList";
	}
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String showTournamentDetails(Map<String, Object> model, HttpServletRequest request) {
		Tournament tournament;
		try {
			tournament = tc.getTournamentFromRequest(request);
		} catch (NumberFormatException e) {
			model.put("message", "Ung�ltige TurnierId.");
			return "tournamentError";
		} catch (UnkownIdException e) {
			model.put("message", "Unbekannte TurnierId.");
			return "tournamentError";
		}
		
		model.put("tournament", tournament);
		return "tournamentView";
	}
}