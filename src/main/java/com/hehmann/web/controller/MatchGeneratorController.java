package com.hehmann.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/*/match/*")
public class MatchGeneratorController {
	
	@RequestMapping(value = "/*/match/generate", method = RequestMethod.GET) @SuppressWarnings("unchecked")
	public String generateMatch(Map<String, Object> model, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		try {
			response.put("status", "ok");;
		} catch (IllegalArgumentException e) {
			response.put("status", "error");
		}
		model.put("content", response);
		return "data";
	}

}
