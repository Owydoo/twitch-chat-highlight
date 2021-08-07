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

app.get("/admin", (req, res) => {
	res.sendFile(__dirname + "/admin.html");
});

io.on("connection", (socket) => {
	console.log("a user connected");
	socket.emit("connected");

	var client;

	socket.on("sendChannelName", (channelName) => {
		console.log(channelName);
		// TMI ----------------------------
		client = new tmi.Client({
			channels: [channelName],
		});

		client.connect();

		client.on("message", (channel, tags, message, self) => {
			console.log(`${tags["display-name"]}: ${message}`);
			socket.emit("sendChat", tags["display-name"], message);
		});

		socket.on("disconnect", (reason) => {
			console.log(reason);
			client.disconnect();
			delete client;
		});
		
	});


	socket.on("chat", (username, message) => {
		console.log(`${username} a envoyé ${message}`);
		io.emit("displayChat", username, message);
	});

	socket.on("hideChat", () => {
		io.emit("hideChat")
	})

	socket.on("sendMessageToLive", (username, message) => {
		io.emit("displayChat", username, message)
	});

	socket.on("messageTel", (text) => {
		console.log(text)
	});
});

server.listen(3000, () => {
	console.log("listening on *:3000");
});
