
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    var token = "${authToken}";
    var sessionCode = "${sessionCode}";
    var serverUri = "${serverUri}";
</script>

<script src="https://cdn.socket.io/socket.io-1.3.4.js"></script>

<div id="quizTool">
    <div class="container">
        <div class="row">
            <div class="col-sm-3" id="sessionMetaCol">
                <span class="sessionName">Connecting</span>
                <span class="sessionHost">Please Wait...</span>
                <span class="separator"></span>
                <span class="status">Waiting to start</span>
                        <span class="instructorInfo" style="display:none;">
                            <span class="participants"><span class="numParticipants"></span> Participants</span>
                        </span>
                        <span class="actions">
                            <button class="btn btn-info" type="button" id="action-startSession" onClick="startSession()" style="display:none">Start Session</button>
                            <button class="btn btn-info" type="button" id="action-endQuestion" onClick="endQuestion()" style="display:none">Close Question</button>
                            <button class="btn btn-info" type="button" id="action-revealCorrectness" onClick="revealCorrectness()" style="display:none">Reveal Correctness</button>
                            <button class="btn btn-info" type="button" id="action-nextQuestion" onClick="nextQuestion()" style="display:none">Next Question</button>
                        </span>

            </div>
            <div class="col-sm-9" id="questionArea">
                <div class="statescreen" id="waitingScreen" style="display:none;">
                    Waiting for host to start the session...
                    Participants: <span class="numParticipants">0</span>
                </div>
                <div class="statescreen" id="waitingScreenInstructor" style="display:none">
                    <p>Give students unique session key:</p>
                    <span class="sessionKey"></span>
                </div>
                <div class="statescreen" id="questionDisplay" style="display:none;">
                    <div class="questionLabel">QUESTION 2</div>
                    <div class="correctnessLabel"></div>
                    <div class="question" id="questionTarget">
                        <p class="prompt">Put question text here</p>
                        <div class="choice radio">
                            <label><input type="radio" name="studentSelection" id="choice0">Answer A</label>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 2%">
                                    2
                                </div>
                            </div>
                        </div>
                        <div class="choice radio markedIncorrect">
                            <label><input type="radio" name="studentSelection" id="choice1">Answer B</label>
                            <div class="progress">
                                <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 12%">
                                    12
                                </div>
                            </div>
                        </div>
                        <div class="choice radio markedCorrect">
                            <label><input type="radio" name="studentSelection" id="choice2">Answer C</label>
                            <div class="progress">
                                <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 56%">
                                    56
                                </div>
                            </div>
                        </div>
                        <div class="choice radio">
                            <label><input type="radio" name="studentSelection" id="choice3">Answer D</label>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                    20
                                </div>
                            </div>
                        </div>
                        <div class="choice radio">
                            <label><input type="radio" name="studentSelection" id="choice4">Answer E</label>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 10%">
                                    10
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="statescreen" id="endOfSession" style="display:none;">
                    <p>The session has ended.</p>
                    <span id="finalScore-student" class="finalScore">Your Score: <span class="pctScore"></span>%</span>
                </div>
                <div class="statescreen" id="endOfSessionHost" style="display:none;">
                    <p>The session has ended.</p>
                    <span class="finalScore">Average Score: <span class="pctScore"></span>%</span>
                    <p>Additional data about this session will be available on your <a href="/dashboard">dashboard</a> momentarily.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
var socket = io(serverUri);
var user;
var session;
var currentRoom = null;
var numQuestions = 0;
var lastSentSelection = null;
var isHost = false;
var tkn = 0;
var cr = 0;
socket.connect(serverUri);
function joinSession(token, sessionKey) {
    socket.emit('join session', token, sessionKey);
};

function setStatusText(text) {
    $('.status').html(text);
};

function setQuestionLabel(text) {
    $('.questionLabel').html(text);
}

function showStatePage(id) {
    $('.statescreen').hide();
    $(id).show();
}

function roundToTenth(num) {
    return Math.round(num*10)/10;
}

function addDashes(num) {
    num = num.toString(); //lol types in JS
    return num.slice(0,3)+"-"+num.slice(3,6)+"-"+num.slice(6,15);
}

function disableInput() {
    $('input[name=studentSelection]').prop('disabled', true);
}

function setCorrectnessLabel(isCorrect) {
    if (isHost) return;
    if (isCorrect == null)  {
        $('.correctnessLabel').html("");
        $('.correctnessLabel').removeClass('correct incorrect');
        return;
    }
    $('.correctnessLabel').addClass(isCorrect?'correct':'incorrect');
    $('.correctnessLabel').html(isCorrect?'Correct!':'Incorrect!');
}

function setRoom(roomInfo) {
    currentRoom = roomInfo;
    $('.sessionName').html(roomInfo.sessionName);
    $('.sessionHost').html(roomInfo.ownerUser.firstname+" "+roomInfo.ownerUser.lastname);
    $('.sessionKey').html(addDashes(roomInfo.sessionCode));
    numQuestions = roomInfo.numQuestions;
    isHost = roomInfo.isHost;
    setStatusText('Waiting to start.');
    if (isHost) {
        showStatePage('#waitingScreenInstructor');
        $('.instructorInfo').show();
    } else {
        showStatePage('#waitingScreen');
    }
}

function getQuestionHtml(promptText, choiceArray) {
    var str = '';
    str += '<p class="prompt">'+promptText+'</p>';
    for(var i = 0; i<choiceArray.length; i++) {
        str += getAnswerHtml(choiceArray[i].text, i);
    }
    return str;
}

function getAnswerHtml(answerText, index) {
    var ansHtml= '<div class="choice radio" data-choiceindex="'+index+'">'+
            '<label><input type="radio" name="studentSelection" value="'+index+'" id="choice'+index+'" onchange="selectionUpdated()" >'+answerText+'</label>';
    if (isHost) {
        ansHtml+='<div class="progbar-target progress">'+
                '<div class="progress-bar" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="min-width:2em; width: 0%">'+
                '0'+
                '</div></div>';
    } else {
        ansHtml+='<div class="progbar-target"></div>';
    }
    ansHtml += '</div>';
    return ansHtml
}

function renderQuestion(questionData) {
    var questionHtml = getQuestionHtml(questionData.prompt, questionData.choices);
    $('#questionTarget').html(questionHtml);
    lastSentSelection = null;
    if (isHost) disableInput();
}

function getSelectedAnswerIndex() {
    var ans = $('input[name=studentSelection]:checked').val();
    return ans;
}

function selectionUpdated() {
    var studentAns = getSelectedAnswerIndex();
    if (studentAns == lastSentSelection) return; //button mashing filter
    socket.emit('select answer', studentAns);
    lastSentSelection = studentAns;
}

function startSession() {
    if (!isHost) return;
    socket.emit('start session');
}
function endQuestion() {
    if (!isHost) return;
    socket.emit('close question');
}
function revealCorrectness() {
    if (!isHost) return;
    socket.emit('reveal correctness');
}
function nextQuestion() {
    if (!isHost) return;
    socket.emit('next question');
}
function endSession() {
    if (!isHost) return;
    socket.emit('end session');
}

function setHostButtonVisibility(startSession, endQuestion, revealCorrect, nextQ) {
    if (!isHost) {
        startSession = false;
        endQuestion = false;
        revealCorrect = false;
        nextQ = false;
    };
    if (startSession) $('#action-startSession').show(); else $('#action-startSession').hide();
    if (endQuestion) $('#action-endQuestion').show(); else $('#action-endQuestion').hide();
    if (revealCorrect) $('#action-revealCorrectness').show(); else $('#action-revealCorrectness').hide();
    if (nextQ) $('#action-nextQuestion').show(); else $('#action-nextQuestion').hide();
}

socket.on('join result', function(success, roomInfo) {
    setHostButtonVisibility(true, false, false, false);
    if (!success) {
        alert ("Invalid Session Code. Please Try Again.");
        location.replace("/");
        return;
    }
    //console.log(roomInfo);
    //we are now in waiting room
    setRoom(roomInfo);
});

//todo: this is not used I don't think...
socket.on('start session', function(firstQuestion) {
    setHostButtonVisibility(false, true, false, false);
    renderQuestion(firstQuestion);
});

socket.on('display question', function (question, index) {
    setHostButtonVisibility(false, true, false, false);
    //console.log(question);
    setStatusText('Question '+(Number(index)+1)+'/'+numQuestions);
    setQuestionLabel('QUESTION '+(Number(index)+1));
    renderQuestion(question);
    setCorrectnessLabel(null);
    showStatePage('#questionDisplay');
});

socket.on('question closed', function() {
    tkn++;
    setHostButtonVisibility(false, false, true, true);
    disableInput();
});

socket.on('end session', function(pctscore, questionsCorrect, totalQuestions, classAvg) {
    if (isHost) {
        $('.pctScore').html(roundToTenth(classAvg));
        showStatePage('#endOfSessionHost');
    } else {
        //$('.pctScore').html(roundToTenth(pctscore));
        $('.pctScore').html(roundToTenth((cr/tkn)*100));
        showStatePage('#endOfSession');
    }
});
var respCounts = [0,0,0,0];
var respTotal = 0;
socket.on('update response counts', function(countArray, total) {
    for (var i = 0; i<countArray.length; i++) {
        $choice = $('div[data-choiceindex='+i+']');
        $progressBar = $choice.find('.progress-bar');
        $progressBar.html(total == 0? 0: roundToTenth((countArray[i]/total) * 100)+'%');
        $progressBar.css({width:((countArray[i]/total) * 100)+"%"});
    }
    respCounts = countArray;
    respTotal = total;
});

var participantCount = 0;
socket.on('participant count', function (count) {
    $('.numParticipants').html(count);
});

socket.on('reveal correct ans', function (answerIndex) {
    setHostButtonVisibility(false, false, false, true);
    var selectedIndex = getSelectedAnswerIndex();
    if (selectedIndex == answerIndex || isHost) {
        //nailed it
        $choice = $('div[data-choiceindex='+answerIndex+']');
        $choice.addClass('markedCorrect');
        setCorrectnessLabel(true);
        //if they're host we have to go color their progress bar
        cr++;
    } else {
        //loser lol
        $correct = $('div[data-choiceindex='+answerIndex+']');
        $correct.addClass('markedCorrect');

        $choice = $('div[data-choiceindex='+selectedIndex+']');
        $choice.addClass('markedIncorrect');
        setCorrectnessLabel(false);
    }

    //show progress barz if they're not host

    for (var i = 0; i<respCounts.length; i++) {
        var pct = respTotal == 0 ? 0 : roundToTenth((respCounts[i]/respTotal) * 100);
        var pbstyle = '';
        if (answerIndex == i) {
            pbstyle = 'progress-bar-success';
        } else if (selectedIndex == i) {
            pbstyle = 'progress-bar-danger';
        }
        var html = '<div class="progress-bar '+pbstyle+'" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="min-width:2em; width: '+pct+'%">'+
                pct+'%'+
                '</div></div>';
        $choice = $('div[data-choiceindex='+i+']');
        $choice.find('.progbar-target').addClass('progress');
        $choice.find('.progbar-target').html(html);
    }

});

socket.on('set owner', function() {
    console.log("YAY IM VERIFIED AS HOST!");
    isHost = true;

});
</script>

<script type="text/javascript">
    joinSession(token, sessionCode);
</script>