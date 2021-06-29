const userElement = document.querySelector('#user')
const messageElement = document.querySelector('#message')


const client = new tmi.Client({
	channels: [ 'solary' ]
});

client.connect();

client.on('message', (channel, tags, message, self) => {
	// "Alca: Hello, World!"
	console.log(`${tags['display-name']}: ${message}`);
    userElement.textContent = tags['display-name']
    messageElement.textContent = message
});
				