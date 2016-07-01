function generateMatches(){
	document.getElementById('generatebutton').disabled = true
	var numtables = document.getElementById('numtables').value
	var matchmode = document.getElementById('matchmode').value
	
	sendToBackend('match/generate', 'numtables=' + numtables + '&matchmode=' + matchmode, function (data) {
		document.getElementById('generatebutton').disabled = false
	})
}
