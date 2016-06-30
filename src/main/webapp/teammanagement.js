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

function createTeamNode(teamId) {
	var newDiv = document.createElement('div')
	newDiv.id = teamId
	newDiv.className = 'team'
	newDiv.ondragover = function (event) { allowDrop(event) }
	newDiv.ondrop = function (event) { drop(event) }

	var newSpan = document.createElement('span')
	newSpan.className = 'teamname'
	newSpan.innerHTML = teams[teamId].name + '<br/><span id="' + teamId + 'count">0</span>/2 Spieler | ' +
	'<a href="#" onclick="deleteTeam(\'' + teamId + '\')">entfernen</a></span>'

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

	document.getElementById(teamId).appendChild(newP)
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
			teams['team' +  data.teamId] = { name: data.teamName, size: 0, players: {} }
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
			document.getElementById('team0count').textContent = ++teams['team0'].size
			document.getElementById('newplayername').value = ''
		})
	}
}

function deletePlayer(playerId){
	for (var teamId in teams) {
		if (playerId in teams[teamId].players) {
			sendToBackend('deletePlayer', 'teamId=' + teamId.substring(4) +
				'&playerId=' + playerId.substring(6), function (data) {
				delete teams['team' + data.teamId].players['player' + data.playerId]
				document.getElementById('team' + data.teamId + 'count').textContent = --teams['team' + data.teamId].size
				document.getElementById('player' + data.playerId).parentElement.removeChild(
					document.getElementById('player' + data.playerId))
			})
		}
	}
}

function dragStart(ev) {
	ev.dataTransfer.setData('id', ev.target.id)
}

function allowDrop(ev) {
    if (ev.target.className === 'team' && 
    	(teams[ev.target.id].size < 2 || 
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

function movePlayerToTeam(playerId, newTeamId) {
	for (var oldTeamId in teams) {
		if ((oldTeamId !== newTeamId) && (playerId in teams[oldTeamId].players)) {
			sendToBackend('movePlayer', 'oldTeamId=' + oldTeamId.substring(4) +
				'&playerId=' + playerId.substring(6) +
				'&newTeamId=' + newTeamId.substring(4), function (data) {
				delete teams['team' + data.oldTeamId].players['player' + data.playerId]
				document.getElementById('team' + data.oldTeamId + 'count').textContent = --teams['team' + data.oldTeamId].size
				
				teams['team' + data.newTeamId].players['player' + data.playerId] = data.player
				document.getElementById('team' + data.newTeamId).appendChild(document.getElementById('player' + data.playerId))
				document.getElementById('team' + data.newTeamId + 'count').textContent = ++teams['team' + data.newTeamId].size
			})

		}
	}
}

// create DOM nodes for elements in data model
for (teamId in teams) {
	if (teamId !== 'team0')
		createTeamNode(teamId)
	for (playerId in teams[teamId].players){
		createPlayerNode(teamId, playerId)
		document.getElementById(teamId + 'count').textContent = teams[teamId].size
	}
}
