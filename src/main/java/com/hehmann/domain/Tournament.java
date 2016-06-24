package com.hehmann.domain;

public class Tournament {
	private String name;
	private TournamentStatus status;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TournamentStatus getStatus() {
		return status;
	}
	public void setStatus(TournamentStatus status) {
		this.status = status;
	}

	public Tournament(String name){
		this.name = name;
		status = TournamentStatus.PREPARATION;
	}
}
