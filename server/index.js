const { text } = require("express");
const express = require("express");
const tmi = require('tmi.js');
const app = express();
const http = require("http");
const server = http.createServer(app);
const io = require("socket.io")(server, {
	cors: {
		origin: "*",
	},
});

app.use(express.static(__dirname + '/public'));

app.get("/", (req, res) => {
	res.sendFile(__dirname + "/index.html");
});

io.on("connection", (socket) => {
	console.log("a user connected");
	socket.emit("message", "oueeeeee");

	socket.on("chat", (username, message) => {
		console.log(`${username} a envoyé ${message}`);
		io.emit("displayChat", username, message);
	});

	socket.on("hideChat", () => {
		io.emit("hideChat")
	})

	socket.on("messageTel", (text) => {
		console.log(text)
	});
});


// TMI ----------------------------
const client = new tmi.Client({
	channels: ["kamet0"],
});

client.connect();

client.on("message", (channel, tags, message, self) => {
	console.log(`${tags["display-name"]}: ${message}`);
	io.emit("displayChat", tags["display-name"], message);

});


server.listen(3000, () => {
	console.log("listening on *:3000");
});
