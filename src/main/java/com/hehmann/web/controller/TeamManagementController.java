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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/*/addTeam", method = RequestMethod.GET)
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/*/deleteTeam", method = RequestMethod.GET)
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
}
