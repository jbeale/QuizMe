function Session() {
    this.data = null;
    this.participants = null;
    this.currentQuestionIndex = -1;
    this.clientCount = 0;
    this.currentState = null;
    this.responses = {};
    this.avgCalcNumCommits = 0;
    this.avgCalcSumCommits = 0;
}

Session.prototype.getData = function() {
    return this.data;
};

Session.prototype.getParticipants = function() {
    return this.participants;
};

Session.prototype.getCurrentQuestionIndex = function() {
    return this.currentQuestionIndex;
};
Session.prototype.nextQuestion = function() {
    if (this.currentQuestionIndex != -1) {
        this.commitResponses(this.getCorrectAnswerIndex(this.getCurrentQuestion().data));
    }
    if (this.hasNextQuestion())  {
        this.currentQuestionIndex++;
    }

    this.responses = {}; //reset responses
    return this.getCurrentQuestion();
};
Session.prototype.hasNextQuestion = function() {
    return this.currentQuestionIndex < this.data.activity.questions.length-1;
}
Session.prototype.getCurrentQuestion = function() {
    return this.data.activity.questions[this.currentQuestionIndex];
};
Session.prototype.getTotalQuestions = function() {
    return this.data.activity.questions.length;
};
Session.prototype.getCorrectAnswerIndex = function(questionData) {
    for (var i = 0; i < questionData.choices.length; i++){
        if (questionData.choices[i].correct) {
            return i;
        }
    }
}

Session.prototype.incrementClientCount = function() {
    return ++this.clientCount;
}

Session.prototype.decrementClientCount = function() {
    return --this.clientCount;
}

Session.prototype.getClientCount = function() {
    return this.clientCount;
}

Session.prototype.setState = function(state) {
    this.state = state;
}

Session.prototype.getState = function() {
    return this.state;
}
Session.prototype.addResponse = function(clientId, answerIndex) {
    this.responses[clientId] = answerIndex;
    //this.responses.set(clientId, answerIndex);
};
Session.prototype.getResponseTotal = function() {
    var size = 0;
    for (var key in this.responses) {
        if (this.responses.hasOwnProperty(key)) size++;
    }
    return size;
};
Session.prototype.getResponseTallies = function() {
    var tallies = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
    /*for (var i = 0; i<this.responses.size; i++) {
        if (tallies[this.responses.get(i)] == null) {
            tallies[this.responses.get(i)] = 1;
        } else {
            tallies[this.responses.get(i)]++;
        }
    }
    for (var value of this.responses.values()) {
        if (tallies[value] == null)
            tallies[value] = 1;
        else
            tallies[value]++;
    }*/
    for (var clientId in this.responses) {
        var response = this.responses[clientId];
        if (tallies[response] == null) {
            tallies[response] = 1;
        } else {
            tallies[response]++;
        }
    }
    return tallies;
};
Session.prototype.commitResponses = function(correctIndex) {
    //what percentage of students got it right?
    var tallies = this.getResponseTallies();
    var totalResponses = 0;
    for (var i = 0; i<tallies.length; i++) {
        totalResponses += tallies[i];
    }
    var correctResponses = tallies[correctIndex];
    var avg = (correctResponses/totalResponses)*100;
    this.avgCalcNumCommits++;
    this.avgCalcSumCommits += avg;
    console.log("Question responses committed. Total responses: "+totalResponses+", Total correct: "+correctResponses+", Question Avg "+avg+", Session avg "+this.getScoreAvg());
};
Session.prototype.getScoreAvg = function() {
    return this.avgCalcSumCommits/this.avgCalcNumCommits;
}

module.exports = Session;