package com.hehmann.web.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/data")
public class FoosballController {
	
	@RequestMapping(value = "/t", method = RequestMethod.GET)
	public String showT(Map<String, Object> model) {
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