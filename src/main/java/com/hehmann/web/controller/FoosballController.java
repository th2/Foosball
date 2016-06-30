package com.hehmann.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hehmann.domain.Tournament;
import com.hehmann.web.controller.exception.UnkownIdException;

@Controller
@RequestMapping("/")
public class FoosballController {
	TournamentController tc = TournamentController.getInstance();
	
	// serve list of tournaments and process new tournament creation requests
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String showTournamentList(@RequestParam(value="newTournament", required=true) String newTournamentName, Map<String, Object> model) {			
		if(!tc.createTournament(newTournamentName))
			model.put("message", "Turnier mit Namen \"" + newTournamentName + "\" existiert bereits.");
		return showTournamentList(model);
	}

	// serve list of tournaments
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showTournamentList(Map<String, Object> model) {
		model.put("tournamentList", tc.getTournamentsList());
		return "tournamentList";
	}
	
	// serve tournament details page
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String showTournamentDetails(Map<String, Object> model, HttpServletRequest request) {
		Tournament tournament;
		try {
			tournament = tc.getTournamentFromRequest(request);
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
}