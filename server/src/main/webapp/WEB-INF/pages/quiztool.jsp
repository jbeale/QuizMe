
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.socket.io/socket.io-1.3.4.js"></script>

<div id="quizTool">
    <div class="container">
        <div class="row">
            <div class="col-md-5" id="sessionMetaCol">
                <span class="sessionName">Test Session Name</span>
                <span class="sessionHost">Host: Will Smith</span>
                <span class="separator"></span>
                <span class="status">Waiting to start</span>
            </div>
            <div class="col-md-7" id="questionArea">
                <div class="statescreen" id="waitingScreen">
                    Waiting for host to start the session, please wait.
                    Participants: <span class="numParticipants">0</span>
                </div>
                <div class="statescreen" id="questionDisplay">
                    <div class="questionLabel">QUESTION 2</div>
                    <div class="question">
                        <p class="prompt">Put question text here</p>
                        <div class="choice">
                            <input type="radio" name="studentSelection" id="choice0"><label for="choice1">Answer A</label>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 2%">
                                    2
                                </div>
                            </div>
                        </div>
                        <div class="choice markedIncorrect">
                            <input type="radio" name="studentSelection" id="choice1"><label for="choice2">Answer B</label>
                            <div class="progress">
                                <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 12%">
                                    12
                                </div>
                            </div>
                        </div>
                        <div class="choice markedCorrect">
                            <input type="radio" name="studentSelection" id="choice2"><label for="choice3">Answer C</label>
                            <div class="progress">
                                <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 56%">
                                    56
                                </div>
                            </div>
                        </div>
                        <div class="choice">
                            <input type="radio" name="studentSelection" id="choice3"><label for="choice4">Answer D</label>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                    20
                                </div>
                            </div>
                        </div>
                        <div class="choice">
                            <input type="radio" name="studentSelection" id="choice4"><label for="choice5">Answer E</label>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 10%">
                                    10
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var socket = io('http://localhost:3001');
    var user;
    var session;
    var token = "afca3484a7d7ae99d8c";
    var currentRoom = null;
    socket.connect('http://localhost:3001');
    function joinSession(sessionKey) {
        socket.emit('join session', sessionKey, token);
    };

    socket.on('join result', function(success, roomInfo) {
        if (!success) {
            alert ("Invalid Session Code. Please Try Again.");
            return;
        }
        //we are now in waiting room
        currentRoom = roomInfo;
    });

    socket.on('display question', function (question) {

    });

</script>

