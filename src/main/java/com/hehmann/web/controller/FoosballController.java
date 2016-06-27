package com.hehmann.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
			tournamentId = Integer.parseInt(URLDecoder.decode(request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			model.put("message", "Ungültige TurnierId.");
			return "tournamentError";
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