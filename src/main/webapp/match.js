function generateMatches(){
	document.getElementById('generatebutton').disabled = true
	
	sendToBackend('match/generate', '', function (data) {
		console.log(data)
		document.getElementById('generatebutton').disabled = false
	})
}
