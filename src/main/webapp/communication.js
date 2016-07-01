function sendToBackend(action, parameters, callback) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4)
			if(xhttp.status == 200) {
				var data = JSON.parse(xhttp.responseText)
				if (data.status === 'ok')
					callback(data)
				else
					alert('Aktion fehlgeschlagen, bitte laden Sie die Seite neu.')
			} else {
				alert('Aktion fehlgeschlagen, bitte laden Sie die Seite neu.')
			}
	}
	xhttp.open('GET', '/kicker/' + tournamentId + '/' + action + '/?' + parameters, true)
	xhttp.send()
}
