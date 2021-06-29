const userElement = document.querySelector('#user')
const messageElement = document.querySelector('#message')


const client = new tmi.Client({
	channels: [ 'sardoche' ]
});

client.connect();

client.on('message', (channel, tags, message, self) => {
	// "Alca: Hello, World!"
	console.log(`${tags['display-name']}: ${message}`);
    userElement.textContent = tags['display-name']
    messageElement.textContent = message
});


var button = document.getElementById("buttoncard");
  button.onclick = () => {
	var username = document.getElementById("user").innerText
	var message = document.getElementById("message").innerText
    socket.emit("chat",username, message)
  }
				