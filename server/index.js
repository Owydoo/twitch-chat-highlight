const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const io = require('socket.io')(server, {
    cors: {
      origin: '*',
    }
  });

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', (socket) => {
  console.log('a user connected');
  socket.emit("message", "oueeeeee")
  
  socket.on("chat", (username, message) => {
    console.log(`${username} a envoyé ${message}`)
  })
});

server.listen(3000, () => {
  console.log('listening on *:3000');
});