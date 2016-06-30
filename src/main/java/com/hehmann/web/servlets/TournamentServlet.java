package com.hehmann.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hehmann.web.controller.TournamentController;
import com.hehmann.web.controller.exception.UnkownIdException;

public class TournamentServlet extends HttpServlet {
	private static final long serialVersionUID = -1202837198234900551L;
	
	TournamentController tc = TournamentController.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");

		try {
			int tournamentId = Integer.parseInt(request.getParameter("t"));
			tc.getTournament(tournamentId).registerListener(response.getWriter());
			
		} catch (UnkownIdException e) {
			response.sendError(500);
		}
		

		/*PrintWriter writer = response.getWriter();

		for(int i=0; i<10; i++) {
			
			writer.write("event:add_team\n");
			writer.write("data: { \"id\": "+ System.currentTimeMillis() +
					", \"name\": " + System.currentTimeMillis() +
					", \"t\": " + request.getParameter("t") +
					" }\n\n");
			
			writer.flush();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		writer.close();*/
	}

}
