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
        console.log(("Registered new session "+sessionData.sessionCode).white.blueBG);
        var session = new Session();
        session.data = sessionData;
        session.incrementClientCount();
        session.setState(State.WAITING_TO_START);
        sessions[sessionData.sessionCode] =session;
        return session;
    } else {
        console.log(("Joined user to existing session "+sessionData.sessionCode).white);
        var session = sessions[sessionData.sessionCode];
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

    //Determines what state we're on and puts the client at that state.
    function syncClient() {
        if (session.getState() == State.WAITING_TO_START) {
            //we don't gotta do anything
        } else if (session.getState() == State.DISPLAYING_QUESTION) {
            socket.emit("display question", session.getCurrentQuestion().data, session.currentQuestionIndex);
        } else if (session.getState() == State.QUESTION_CLOSED) {
            socket.emit("display question", session.getCurrentQuestion().data, session.currentQuestionIndex);
            socket.emit("question closed");
        } else if (session.getState() == State.SHOWING_ANSWER) {
            socket.emit("display question", session.getCurrentQuestion().data, session.currentQuestionIndex);
            socket.emit("reveal correct ans", getCorrectAnswerIndex(session.getCurrentQuestion().data));
        }
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
                io.sockets.in(roomId).emit("participant count", session.getClientCount());

                console.log("Sucessfully joined user to session: ".green+apiResponse.getData().sessionName);

                if (authToken != null && authToken.length > 1) {
                    //try to assign the user. If the token ends up not being valid, user will simply be joined as guest.
                    setUser(authToken);
                } else {
                    //just join it as guest, and it won't have host privs.
                    socket.emit('join result', true, getStrippedSessionInfo());
                    syncClient();
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
        if (!isSessionOwner) return;
        if (session.getState() != State.WAITING_TO_START) return;
        console.log('STARTING SESSION '+currentSession.sessionName);
        if (session.hasNextQuestion()) {
            io.sockets.in(roomId).emit("display question", session.nextQuestion().data, session.currentQuestionIndex);
            changeState(State.DISPLAYING_QUESTION);
        } else {
            //empty activity how dumb
            changeState(State.QUIZ_ENDED);
        }
    });

    var responses = [0,0,0,0];
    var respCount = 0;
    //Used by students to submit answer to question
    socket.on('select answer', function(choiceIndex) {
        console.log("Received answer "+choiceIndex)
        //TODO don't send to all users, and actually keep track of individual responses.
        responses[choiceIndex]++;
        respCount++;
        io.sockets.in(roomId).emit("update response counts", responses, respCount);
        console.log(responses);
    });

    //Used by instructors to close a question
    socket.on('close question', function() {
        if (!isSessionOwner) return;
        if (session.getState() != State.DISPLAYING_QUESTION) return;
        session.setState(State.QUESTION_CLOSED);
        io.sockets.in(roomId).emit("question closed");
    });

    //Used by instructors to reveal correctness of closed question
    socket.on('reveal correctness', function() {
        if (!isSessionOwner) return;
        if (session.getState() != State.QUESTION_CLOSED) return;

        io.sockets.in(roomId).emit("reveal correct ans", getCorrectAnswerIndex(session.getCurrentQuestion().data));

        session.setState(State.SHOWING_ANSWER);
    });

    //Used by instructor to go to next question
    socket.on('next question', function() {
        if (!isSessionOwner) return;
        if (session.getState() != State.QUESTION_CLOSED && session.getState() != State.SHOWING_ANSWER) return;
        if (session.hasNextQuestion()) {
            session.setState(State.DISPLAYING_QUESTION);
            io.sockets.in(roomId).emit("display question", session.nextQuestion().data, session.currentQuestionIndex);
        } else {
            session.setState(State.QUIZ_ENDED);
        }
    });

    //used by instructor to end the session. This does the following:
    //1. Brings students to final "Results" screen.
    //2. Brings instructor to final "Class Results" screen.
    //3. Sends all necessary data to Java server
    socket.on('end session', function() {
        if (!isSessionOwner) return;
    });

    socket.on('disconnect', function() {
        if (isSessionOwner) {
            //let all the clients know their host is gone
            io.sockets.in(roomId).emit("instructor disconnected");
        }
        if (session != null) {
            session.decrementClientCount();
            io.sockets.in(roomId).emit("participant count", session.getClientCount());
        }

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
                    socket.emit('set owner');
                    console.log("User "+ u.getFirstname()+" "+ u.getLastname()+" established as session owner of "+currentSession.sessionName);
                } else {
                    //it'll be joined as a user but not owner
                }
            } else {
                //no success so the token was probably invalid.

            }
            //Send the session join result because we waited to do it for the user info.
            socket.emit('join result', true, getStrippedSessionInfo());
            syncClient();
        });
    }

    //Returns a limited session info object for client (we don't want to send all the questions at the beginning).
    function getStrippedSessionInfo() {
        var clientFriendlySession = {};
        clientFriendlySession.sessionName = currentSession.sessionName;
        clientFriendlySession.ownerUser = currentSession.ownerUser;
        clientFriendlySession.numQuestions = session.getTotalQuestions();
        clientFriendlySession.isHost = isSessionOwner;
        clientFriendlySession.sessionCode = currentSession.sessionCode;
        return clientFriendlySession;
    };

    function getCorrectAnswerIndex(questionData) {
        for (var i = 0; i < questionData.choices.length; i++){
            if (questionData.choices[i].correct) {
                return i;
            }
        }
    }
});


server.listen(3001, function () {
    console.log("Starting on 0.0.0.0:3001...             " + ("  OK  ".black.greenBG));
});

server.on('error', function(e) {
    console.log("Starting on 0.0.0.0:3001...             " + (" FAIL ".black.redBG));
    console.log(e.message.red);
})