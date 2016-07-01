<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<title>${tournament.getName()}</title>
  	<link rel="stylesheet" type="text/css" href="/kicker/theme.css">
	<link rel="stylesheet" type="text/css" href="/kicker/team.css">
	<link rel="stylesheet" type="text/css" href="/kicker/match.css">
</head><body>
<header>
	<h1>${tournament.getName()}</h1>
</header>

<nav>
	<a id="navteam" class="navactive" href="#team" onclick="changeView('team')">Spieler & Teams</a> |
	<a id="navmatch"  href="#match" onclick="changeView('match')">Partien</a> | 
	<a id="navrank"  href="#rank" onclick="changeView('rank')">Rangliste ${tournament.getId()}</a>
</nav>

<main>
	<article id="teamlist">
		<div id="team0" class="team" ondragover="allowDrop(event)" ondrop="drop(event)">
			<span class="teamname">Ohne Team <br/><span id="team0count">0</span> Spieler</span>
			<p class="player">
				<input type="text" id="newplayername">
				<button onclick="addPlayer()">hinzufügen</button>
			</p>
			<p id="deleteplayer" class="player" ondragover="return false" onclick="alert('Spieler auf löschen ziehen um sie zu entfernen.')"> löschen </p>
		</div>
		<div id="addteam" class="team">
			<span id="addteamtext" class="teamname">Neues Team:</span>
			<input type="text" id="newteamname">
			<button id="addteambutton" onclick="addTeam()">hinzufügen</button>
		</div>
	</article>
	<article id="matchlist" style="display:none;">
		<details open="open">
			<summary>Turniereigenschaften</summary>
			<p>
				<label for="numtables">Anzahl Tische: </label><input id="numtables" type="number" value="1"/><br/>
				Spielmodus: <select id="matchmode">
					<option value="0" selected>Jeder gegen Jeden</option>
					<option value="1">2 Gruppen mit Halbfinale und Finale</option>
					<option value="2">4 Gruppen mit Viertelfinale, Halbfinale und Finale</option>
				</select><br/>
				<button id="generatebutton" onclick="generateMatches()">Spielplan generieren</button><br/>
			</p>
		</details>
	</article>
	<article id="ranklist" style="display:none;">
		Team | Spieler | Spiele | Punkte | Tordifferenz | Tore | Gegentore
	</article>
</main>
<script>
	function changeView(view){
		if(view === 'team') {
			document.getElementById('navteam').className = "navactive"
			document.getElementById('navmatch').className = ""
			document.getElementById('navrank').className = ""
			document.getElementById('teamlist').style.display = "block"
			document.getElementById('matchlist').style.display = "none"
			document.getElementById('ranklist').style.display = "none"
		} else if(view === 'match') {
			document.getElementById('navteam').className = ""
			document.getElementById('navmatch').className = "navactive"
			document.getElementById('navrank').className = ""
			document.getElementById('teamlist').style.display = "none"
			document.getElementById('matchlist').style.display = "block"
			document.getElementById('ranklist').style.display = "none"
		} else { // if(view === 'rank')
			document.getElementById('navteam').className = ""
			document.getElementById('navmatch').className = ""
			document.getElementById('navrank').className = "navactive"
			document.getElementById('teamlist').style.display = "none"
			document.getElementById('matchlist').style.display = "none"
			document.getElementById('ranklist').style.display = "block"
		}
	}

	var teams = ${tournament.toJSON()}
	var tournamentId = ${tournament.getId()}
</script>
<script src="/kicker/communication.js"></script>
<script src="/kicker/team.js"></script>
<script src="/kicker/match.js"></script>

<!--<footer class="clear">Stand: <c:out value="<%=new java.util.Date()%>" /></footer>-->

</body></html>
