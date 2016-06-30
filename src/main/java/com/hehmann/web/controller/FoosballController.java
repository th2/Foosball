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
	
	private Tournament getTournamentFromRequest(HttpServletRequest request) throws NumberFormatException, UnkownIdException {
		String[] parameters = request.getRequestURI().split("/");
		Integer tournamentId = Integer.parseInt(parameters[2]);
		return tc.getTournament(tournamentId);
	}
	
	private String checkParameter(String parameter) throws IllegalArgumentException {
		if(parameter != null && parameter.length() > 0)
			return parameter;
		else throw new IllegalArgumentException();
	}
	
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
			tournament = getTournamentFromRequest(request);
		} catch (NumberFormatException e) {
			model.put("message", "Ungültige TurnierId.");
			return "tournamentError";
		} catch (UnkownIdException e) {
			model.put("message", "Unbekannte TurnierId.");
			return "tournamentError";
		}
		
		model.put("tournament", tournament);
		return "tournamentView";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/*/addTeam", method = RequestMethod.GET)
	public String addTeam(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			String teamName = checkParameter(request.getParameter("name"));
			int teamId = getTournamentFromRequest(request).createTeam(teamName);
			
			response.put("status", "ok");
			response.put("teamId", teamId);
			response.put("teamName", teamName);
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/*/deleteTeam", method = RequestMethod.GET)
	public String deleteTeam(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			int teamId = Integer.parseInt(checkParameter(request.getParameter("id")));
			getTournamentFromRequest(request).deleteTeam(teamId);
			
			response.put("status", "ok");
			response.put("teamId", teamId);
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
}