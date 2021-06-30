const userElement = document.querySelector("#user");
const messageElement = document.querySelector("#message");

const client = new tmi.Client({
	channels: ["solary"],
});

client.connect();

client.on("message", (channel, tags, message, self) => {
	console.log(`${tags["display-name"]}: ${message}`);
	userElement.textContent = tags["display-name"];
	messageElement.textContent = message;
});

var buttonSend = document.getElementById("buttoncard");
buttonSend.onclick = () => {
	var username = document.getElementById("user").innerText;
	var message = document.getElementById("message").innerText;
	socket.emit("chat", username, message);
};


var buttonHide = document.getElementById("buttonhide");
buttonHide.onclick = () => {
	socket.emit("hideChat");
};

