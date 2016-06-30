<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  	<title>${tournament.getName()}</title>
  	<link rel="stylesheet" type="text/css" href="/kicker/theme.css">
	<link rel="stylesheet" type="text/css" href="/kicker/teammanagement.css">
</head><body>
<header>
	<h1>${tournament.getName()}</h1>
</header>

<nav>
	<b>Spieler & Teams</b> | Partien | Rangliste ${tournament.getId()}
</nav>

<main id="teamlist">
	<div id="team0" class="team" ondragover="allowDrop(event)" ondrop="drop(event)">
		<span class="teamname">Ohne Team <br/><span id="team0count">6</span> Spieler</span>
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
</main>
<script>
	var teams = ${tournament.toJSON()}
	var tournamentId = ${tournament.getId()}
</script>
<script src="/kicker/teammanagement.js"></script>

<!--<footer class="clear">Stand: <c:out value="<%=new java.util.Date()%>" /></footer>-->

</body></html>
