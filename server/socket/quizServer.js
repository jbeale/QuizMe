//Socket.io Server for real-time quizzing
//jbeale

var app = require('express')();
var server = require('http').createServer(app);
var io = require('socket.io')(server);
var QuizWhiz = require('./quizwhiz-api.js');
var cors = require('cors');
var colors = require('colors');
app.use(cors);
console.log('QuizWhiz Interaction Server 0.5.0'.underline.yellow);

io.on('connection', function(socket) {
    var socketId = socket.id
    var connection = socket.request.connection;
    console.log("New connection from " + connection.remoteAddress+" / "+connection.hostname);
    var user = null;
    var isSessionOwner = false;
    var currentSession = null;
    socket.on('join session', function (authToken, sessionCode) {
        console.log("RECEIVED COMMAND join session: "+authToken+" , "+sessionCode);
        //first validate the token and session code
        sessionCode = sessionCode.replace(/\D/g,''); //filter session code for non-numerics
        QuizWhiz.REST.joinSession(authToken, sessionCode, function(apiResponse) {
            if (apiResponse.wasSuccessful()) {
                socket.join(sessionCode);
                socket.emit('join result', true);
                currentSession = sessionCode;
                return;
            }
            socket.emit('join result', false);
            console.log('Invalid Session code '+sessionCode+' provided. Connection terminated'.error);
            socket.disconnect();
        });
    });
});


server.listen(3001, function () {
    console.log("Starting on 0.0.0.0:3001...             " + ("  OK  ".black.greenBG));
});

server.on('error', function(e) {
    console.log("Starting on 0.0.0.0:3001...             " + (" FAIL ".black.redBG));
    console.log(e.message.red);
})