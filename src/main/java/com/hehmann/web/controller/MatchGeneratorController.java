package com.hehmann.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/*/match/*")
public class MatchGeneratorController {
	
	@RequestMapping(value = "/*/match/generate", method = RequestMethod.GET, 
			params = {"numtables", "matchmode"}) @SuppressWarnings("unchecked")
	public String generateMatch(Map<String, Object> model, HttpServletRequest request,
			@RequestParam(value = "numtables") int numTables, @RequestParam(value = "matchmode") int matchMode) {
		JSONObject response = new JSONObject();
		response.put("status", "ok");
		response.put("numTables", numTables);
		response.put("matchMode", matchMode);
		model.put("content", response);
		return "data";
	}

}
