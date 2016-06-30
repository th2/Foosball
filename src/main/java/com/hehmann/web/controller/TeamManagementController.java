package com.hehmann.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/*/*")
public class TeamManagementController {
	TournamentController tc = TournamentController.getInstance();
	
	private String checkParameter(String parameter) throws IllegalArgumentException {
		if(parameter != null && parameter.length() > 0)
			return parameter;
		else throw new IllegalArgumentException();
	}
	
	@RequestMapping(value = "/*/addTeam", method = RequestMethod.GET) @SuppressWarnings("unchecked")
	public String addTeam(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			String teamName = checkParameter(request.getParameter("name"));
			int teamId = tc.getTournamentFromRequest(request).createTeam(teamName);
			
			response.put("status", "ok");
			response.put("teamId", teamId);
			response.put("teamName", teamName);
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
	
	@RequestMapping(value = "/*/deleteTeam", method = RequestMethod.GET) @SuppressWarnings("unchecked")
	public String deleteTeam(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			int teamId = Integer.parseInt(checkParameter(request.getParameter("id")));
			tc.getTournamentFromRequest(request).deleteTeam(teamId);
			
			response.put("status", "ok");
			response.put("teamId", teamId);
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
	
	@RequestMapping(value = "/*/addPlayer", method = RequestMethod.GET) @SuppressWarnings("unchecked")
	public String addPlayer(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			String playerName = checkParameter(request.getParameter("name"));
			int playerId = tc.getTournamentFromRequest(request).createPlayer(playerName);
			
			response.put("status", "ok");
			response.put("playerId", playerId);
			response.put("playerName", playerName);
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
	
	@RequestMapping(value = "/*/deletePlayer", method = RequestMethod.GET) @SuppressWarnings("unchecked")
	public String deletePlayer(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			int teamId = Integer.parseInt(checkParameter(request.getParameter("teamId")));
			int playerId = Integer.parseInt(checkParameter(request.getParameter("playerId")));
			tc.getTournamentFromRequest(request).getTeam(teamId).deletePlayer(playerId);
			
			response.put("status", "ok");
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		} catch (IndexOutOfBoundsException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
}
