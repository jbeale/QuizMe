//Socket.io Server for real-time quizzing
//jbeale

var app = require('express')();
var server = require('http').createServer(app);
var io = require('socket.io')(server);
var QuizWhiz = require('./quizwhiz-api.js');
var cors = require('cors');
var colors = require('colors');
var User = require('./model/User.js');
var Session = require('./model/Session.js');
app.use(cors);
console.log('QuizWhiz Interaction Server 0.5.0'.underline.yellow);

var sessions = [];

var State = {
    EMPTY_STATE:0,WAITING_TO_START:1,DISPLAYING_QUESTION:2,QUESTION_CLOSED:3,SHOWING_ANSWER:4,QUIZ_ENDED:5
};

function registerSession(sessionData) {

    if (sessions[sessionData.sessionCode] == null) {
        var session = new Session();
        session.data = sessionData;
        session.incrementClientCount();
        session.setState(State.WAITING_TO_START);
        sessions.push(session);
        return session;
    } else {
        session.incrementClientCount();
        return sessions[sessionData.sessionCode];
    }
}

io.on('connection', function(socket) {
    var socketId = socket.id
    var connection = socket.request.connection;
    console.log("New connection from " + connection.remoteAddress+" / "+connection.hostname);
    var user = null;
    var isSessionOwner = false;
    var currentSession = null;
    var session = null;
    var roomId = null;

    function joinRoom(roomName) {
        socket.join(roomName);
        roomId = roomName;
    }

    function changeState(newState) {
        session.setState(newState);
        socket.to(roomId).emit("session state change", newState);
    }

    //JOIN SESSION: Used by all users to join a session room
    socket.on('join session', function (authToken, sessionCode) {
        console.log("RECEIVED COMMAND join session: "+authToken+" --> "+sessionCode);
        //first validate the token and session code
        sessionCode = sessionCode.replace(/\D/g,''); //filter session code for non-numerics
        QuizWhiz.REST.joinSession(authToken, sessionCode, function(apiResponse) {
            if (apiResponse.wasSuccessful()) {
                joinRoom(sessionCode);
                currentSession = apiResponse.getData();
                session = registerSession(currentSession);
                socket.emit('join result', true, getStrippedSessionInfo());
                console.log("Sucessfully joined user to session: ".green+apiResponse.getData().sessionName);
                if (authToken != null && authToken.length > 1) {
                    //try to assign the user. If the token ends up not being valid, user will simply be joined as guest.
                    setUser(authToken);
                }
                return;
            }
            socket.emit('join result', false);
            console.log('Invalid Session code '+sessionCode+' provided. Connection terminated'.red);
            socket.disconnect();
        });
    });

    //START SESSION: Used by instructor users (session owners) to begin their session
    socket.on('start session', function () {
        console.log('STARTING SESSION '+currentSession.sessionName);
        if (!isSessionOwner) return;
        if (session.getState() != State.WAITING_TO_START) return;
        if (session.hasNextQuestion()) {
            io.sockets.in(roomId).emit("display question", session.nextQuestion().data, session.currentQuestionIndex);
            changeState(State.DISPLAYING_QUESTION);
        } else {
            //empty activity how dumb
            changeState(State.QUIZ_ENDED);
        }
    });

    //Used by students to submit answer to question
    socket.on('question response', function(choice) {
        
    });

    //Used by instructors to close a question
    socket.on('close question', function() {
        if (!isSessionOwner) return;
    });

    //Used by instructors to reveal correctness of closed question
    socket.on('reveal correctness', function() {
        if (!isSessionOwner) return;
    });

    //Used by instructor to go to next question
    socket.on('next question', function() {
        if (!isSessionOwner) return;
    });

    //used by instructor to end the session. This does the following:
    //1. Brings students to final "Results" screen.
    //2. Brings instructor to final "Class Results" screen.
    //3. Sends all necessary data to Java server
    socket.on('end session', function() {
        if (!isSessionOwner) return;
    });

    ////////////////////////
    /*Non-socket functions*/
    function setUser(token) {
        QuizWhiz.REST.getUser(token, function(apiResponse) {
            if (apiResponse.wasSuccessful()) {
                rs = apiResponse.getData();
                var u = new User(rs.id, rs.firstname, rs.lastname, rs.username);
                user = u;
                if (currentSession.ownerUserId == u.getId()) {
                    isSessionOwner = true;
                    console.log("User "+ u.getFirstname()+" "+ u.getLastname()+" established as session owner of "+currentSession.sessionName);
                }
            }
        });
    }

    //Returns a limited session info object for client (we don't want to send all the questions at the beginning).
    function getStrippedSessionInfo() {
        var clientFriendlySession = {};
        clientFriendlySession.sessionName = currentSession.sessionName;
        clientFriendlySession.ownerUser = currentSession.ownerUser;
        clientFriendlySession.numQuestions = session.getTotalQuestions();
        return clientFriendlySession;
    }
});


server.listen(3001, function () {
    console.log("Starting on 0.0.0.0:3001...             " + ("  OK  ".black.greenBG));
});

server.on('error', function(e) {
    console.log("Starting on 0.0.0.0:3001...             " + (" FAIL ".black.redBG));
    console.log(e.message.red);
})