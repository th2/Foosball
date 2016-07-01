package com.hehmann.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hehmann.domain.Player;

@Controller
@RequestMapping("/*/team/*")
public class TeamManagementController {
	TournamentController tc = TournamentController.getInstance();
	
	@RequestMapping(value = "/*/team/addTeam", method = RequestMethod.GET, 
			params = {"name"}) @SuppressWarnings("unchecked")
	public String addTeam(Map<String, Object> model, HttpServletRequest request,
			@RequestParam(value = "name") String teamName) {
		JSONObject response = new JSONObject();
		try {
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
	
	@RequestMapping(value = "/*/team/deleteTeam", method = RequestMethod.GET, 
			params = {"id"}) @SuppressWarnings("unchecked")
	public String deleteTeam(Map<String, Object> model, HttpServletRequest request,
			@RequestParam(value = "id") int teamId) {
		JSONObject response = new JSONObject();
		try {
			tc.getTournamentFromRequest(request).deleteTeam(teamId);
			response.put("status", "ok");
			response.put("teamId", teamId);
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
	
	@RequestMapping(value = "/*/team/addPlayer", method = RequestMethod.GET, 
			params = {"name"}) @SuppressWarnings("unchecked")
	public String addPlayer(Map<String, Object> model, HttpServletRequest request,
			@RequestParam(value = "name") String playerName) {
		JSONObject response = new JSONObject();
		try {
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
	
	@RequestMapping(value = "/*/team/deletePlayer", method = RequestMethod.GET, 
			params = {"teamId", "playerId"}) @SuppressWarnings("unchecked")
	public String deletePlayer(Map<String, Object> model, HttpServletRequest request,
			@RequestParam(value = "teamId") int teamId, @RequestParam(value = "playerId") int playerId) {
		JSONObject response = new JSONObject();
		try {
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
	
	@RequestMapping(value = "/*/team/movePlayer", method = RequestMethod.GET, 
			params = {"oldTeamId", "newTeamId", "playerId"}) @SuppressWarnings("unchecked")
	public String movePlayer(Map<String, Object> model, HttpServletRequest request,
			@RequestParam(value = "oldTeamId") int oldTeamId,
			@RequestParam(value = "newTeamId") int newTeamId,
			@RequestParam(value = "playerId") int playerId) {
		JSONObject response = new JSONObject();
		try {
			Player player = tc.getTournamentFromRequest(request).getTeam(oldTeamId).getPlayer(playerId);
			if(tc.getTournamentFromRequest(request).getTeam(newTeamId).addPlayer(player)) {
				tc.getTournamentFromRequest(request).getTeam(oldTeamId).deletePlayer(playerId);
				response.put("status", "ok");
				response.put("oldTeamId", oldTeamId);
				response.put("newTeamId", newTeamId);
				response.put("playerId", playerId);
				response.put("player", player.toJSON());
			} else {
				response.put("status", "error");
			}
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		} catch (IndexOutOfBoundsException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}
}
