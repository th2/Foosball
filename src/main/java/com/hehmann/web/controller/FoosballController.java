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

import com.hehmann.domain.Tournament;

@Controller
@RequestMapping("/")
public class FoosballController {
	private Map<Integer, Tournament> tournaments = new HashMap<Integer, Tournament>();
	private int newTournamentId = 0;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String showTournamentList(@RequestParam(value="newTournament", required=true) String newTournamentName, Map<String, Object> model) {			
		if(tournaments.containsKey(newTournamentName))
			model.put("message", "Turnier mit Namen \"" + newTournamentName + "\" existiert bereits.");
		else {
			tournaments.put(newTournamentId, new Tournament(newTournamentId, newTournamentName));
			newTournamentId++;
		}
		return showTournamentList(model);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showTournamentList(Map<String, Object> model) {
		model.put("tournamentList", tournaments.entrySet());
		return "tournamentList";
	}
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String showTournamentDetails(Map<String, Object> model, HttpServletRequest request) {
		Integer tournamentId;
		try {
			String[] parameters = request.getRequestURI().split("/");
			tournamentId = Integer.parseInt(parameters[2]);
		} catch (NumberFormatException e) {
			model.put("message", "Ungültige TurnierId.");
			return "tournamentError";
		}
		
		if(!tournaments.containsKey(tournamentId)) {
			model.put("message", "Unbekannte TurnierId.");
			return "tournamentError";
		}
		
		model.put("tournament", tournaments.get(tournamentId));
		return "tournamentView";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String getTournamentData(Map<String, Object> model, HttpServletRequest request) {
		Integer tournamentId;
		String action;
		
		try {
			String[] parameters = request.getRequestURI().split("/");
			tournamentId = Integer.parseInt(parameters[2]);
			action = URLDecoder.decode(parameters[3], "iso-8859-1");
		} catch (NumberFormatException e) {
			return sendData(model, getDataError(1)); // invalid tournamentId
		} catch (UnsupportedEncodingException e) {
			return sendData(model, getDataError(2)); // invalid action
		}
		
		if(!tournaments.containsKey(tournamentId)) {
			return sendData(model, getDataError(0)); // tournamentId not found
		}

		return sendData(model, getDataResponse(tournamentId, action));
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject getDataResponse(Integer tournamentId, String action) {
		JSONObject response = new JSONObject();
		response.put("tournamentId", tournamentId);
		response.put("action", action);
		response.put("tournamentName", tournaments.get(tournamentId).getName());
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject getDataError(Integer errorCode) {
		JSONObject response = new JSONObject();
		response.put("error", errorCode);
		return response;
	}

	private String sendData(Map<String, Object> model, JSONObject response) {
		model.put("content", response);
		return "data";
	}
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String showData(Map<String, Object> model) {
		model.put("content", "test3");
		return "data";
	}
	
	@RequestMapping(value = "/greetings.html", method = RequestMethod.POST)
	public String showAllGreetings(@RequestParam(value="greetingText", required=true) String greetingText, Map<String, Object> model) {			
		model.put("greetingText", greetingText);
		return "greetings";
	}
	
	@RequestMapping(value = "/addgreeting.html", method = RequestMethod.GET)
	public String showAddGreetingPage() {
		return "addgreeting";
	}
}