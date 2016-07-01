package com.hehmann.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hehmann.domain.Player;

@Controller
@RequestMapping("/*/team/*")
public class TeamManagementController {
	TournamentController tc = TournamentController.getInstance();
	
	private String checkParameter(String parameter) throws IllegalArgumentException {
		if(parameter != null && parameter.length() > 0)
			return parameter;
		else throw new IllegalArgumentException();
	}
	
	@RequestMapping(value = "/*/team/addTeam", method = RequestMethod.GET) @SuppressWarnings("unchecked")
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
	
	@RequestMapping(value = "/*/team/deleteTeam", method = RequestMethod.GET) @SuppressWarnings("unchecked")
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
	
	@RequestMapping(value = "/*/team/addPlayer", method = RequestMethod.GET) @SuppressWarnings("unchecked")
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
	
	@RequestMapping(value = "/*/team/deletePlayer", method = RequestMethod.GET) @SuppressWarnings("unchecked")
	public String deletePlayer(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			int teamId = Integer.parseInt(checkParameter(request.getParameter("teamId")));
			int playerId = Integer.parseInt(checkParameter(request.getParameter("playerId")));
			tc.getTournamentFromRequest(request).getTeam(teamId).deletePlayer(playerId);

			response.put("status", "ok");
			response.put("teamId", teamId);
			response.put("playerId", playerId);
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		} catch (IndexOutOfBoundsException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
	
	@RequestMapping(value = "/*/team/movePlayer", method = RequestMethod.GET) @SuppressWarnings("unchecked")
	public String movePlayer(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			int oldTeamId = Integer.parseInt(checkParameter(request.getParameter("oldTeamId")));
			int newTeamId = Integer.parseInt(checkParameter(request.getParameter("newTeamId")));
			int playerId = Integer.parseInt(checkParameter(request.getParameter("playerId")));

			Player player = tc.getTournamentFromRequest(request).getTeam(oldTeamId).getPlayer(playerId);
			if(tc.getTournamentFromRequest(request).getTeam(newTeamId).addPlayer(player)) {
				tc.getTournamentFromRequest(request).getTeam(oldTeamId).deletePlayer(playerId);
				response.put("status", "ok");
				response.put("oldTeamId", oldTeamId);
				response.put("newTeamId", newTeamId);
				response.put("playerId", playerId);
				response.put("player", player.toJSON());
			} else {
				response.put("status", "error1");
			}
		} catch (IllegalArgumentException e) {
			response.put("status", "error2");
		} catch (IndexOutOfBoundsException e) {
			response.put("status", "error3");
		}
		model.put("content", response);
		return "data";
	}
}
