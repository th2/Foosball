function teamNameExists(needle) {
	for (teamId in teams)
		if (teams[teamId].name === needle) return true
	return false
}

function playerNameExists(needle) {
	for (teamId in teams)
		for (playerId in teams[teamId].players)
			if (teams[teamId].players[playerId].name === needle) return true
	return false
}

function numberOfPlayers(teamId) {
	return Object.keys(teams[teamId].players).length
}

function createTeamNode(teamId) {
	var newDiv = document.createElement('div')
	newDiv.id = teamId
	newDiv.className = 'team'
	newDiv.ondragover = function (event) { allowDrop(event) }
	newDiv.ondrop = function (event) { drop(event) }

	var newSpan = document.createElement('span')
	newSpan.className = 'teamname'
	newSpan.innerHTML = teams[teamId].name + '<br/><span id="' + teamId + 'count">0</span>/2 Spieler | ' +
	'<a href="#" onclick="deleteTeam(\'' + teamId + '\')">löschen</a></span>'

	newDiv.appendChild(newSpan)
	document.getElementById('teamlist').insertBefore(newDiv, document.getElementById('addteam'))
}

function createPlayerNode(teamId, playerId) {
	var newP = document.createElement('p')
	newP.id = playerId
	newP.className = 'player'
	newP.draggable = true
	newP.ondragstart = function (event) { dragStart(event) }
	newP.innerHTML = teams[teamId].players[playerId].name

	document.getElementById('team0').appendChild(newP)
}

function sendToBackend(action, parameters, callback) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var data = JSON.parse(xhttp.responseText)
			if (data.status === 'ok')
				callback(data)
			else
				alert('Aktion fehlgeschlagen, bitte laden Sie die Seite neu.')
		}
	}
	xhttp.open('GET', '/kicker/' + tournamentId + '/' + action + '/?' + parameters, true)
	xhttp.send()
}

function addTeam(){
	var newTeamName = document.getElementById('newteamname').value
	if (newTeamName === '') {
		alert('Bitte einen Teamnamen eingeben.')
	} else if(teamNameExists(newTeamName)) {
		alert('Ein Team mit Namen "' + newTeamName + '" existiert bereits.')
	} else {
		document.getElementById('addteambutton').disabled = true
		
		sendToBackend('addTeam', 'name=' + encodeURI(newTeamName), function (data) {
			teams['team' +  data.teamId] = { name: data.teamName, players: {} }
			createTeamNode('team' + data.teamId)
			document.getElementById('addteambutton').disabled = false
			document.getElementById('newteamname').value = ''
		})
	}
}

function deleteTeam(teamId){
	for (var playerId in teams[teamId].players)
		movePlayerToTeam(playerId, 'team0')
	
	sendToBackend('deleteTeam', 'id=' + teamId.substring(4), function (data) {
		delete teams['team' +  data.teamId]
		document.getElementById('teamlist').removeChild(document.getElementById('team' +  data.teamId))
	})
		
}

function addPlayer(){
	var newPlayerName = document.getElementById('newplayername').value
	if (newPlayerName === '') {
		alert('Bitte einen Spielernamen eingeben.')
	} else if(playerNameExists(newPlayerName)) {
		alert('Ein Spieler mit Namen "' + newPlayerName + '" existiert bereits.')
	} else {
		sendToBackend('addPlayer', 'name=' + newPlayerName, function (data) {
			teams['team0'].players[ 'player' + data.playerId ] = { name: data.playerName }
			createPlayerNode('team0', 'player' + data.playerId)
			document.getElementById('newplayername').value = ''
		})
	}
}

function deletePlayer(playerId){
	for (var teamId in teams) {
		if (playerId in teams[teamId].players) {
			delete teams[teamId].players[playerId]
			document.getElementById(teamId + 'count').textContent = numberOfPlayers(teamId)
		}
	}
	document.getElementById(playerId).parentElement.removeChild(document.getElementById(playerId))
}

function dragStart(ev) {
	ev.dataTransfer.setData('id', ev.target.id)
}

function allowDrop(ev) {
    if (ev.target.className === 'team' && 
    	(numberOfPlayers(ev.target.id) < 2 || 
   		ev.target.id === 'team0'))
	    	ev.preventDefault()
}

function drop(ev) {
	ev.preventDefault()
	var playerId = ev.dataTransfer.getData('id')
	var teamId = ev.target.id
	if(teamId !== 'deleteplayer')
		movePlayerToTeam(playerId, teamId)
	else
		deletePlayer(playerId)
}

function movePlayerToTeam(playerId, teamId) {
	var player = { name: '?' }
	for (var i in teams) {
		if (playerId in teams[i].players) {
			player = teams[i].players[playerId]
			delete teams[i].players[playerId]

			document.getElementById(i + 'count').textContent = numberOfPlayers(i)
		}
	}
	teams[teamId].players[playerId] = player

	document.getElementById(teamId).appendChild(document.getElementById(playerId))
	document.getElementById(teamId + 'count').textContent = numberOfPlayers(teamId)
}

// create DOM nodes for elements in data model
for (teamId in teams) {
	if (teamId !== 'team0')
		createTeamNode(teamId)
	for (playerId in teams[teamId].players){
		createPlayerNode(teamId, playerId)
	}
}
