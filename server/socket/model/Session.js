function Session() {
    this.data = null;
    this.participants = null;
    this.currentQuestionIndex = -1;
    this.clientCount = 0;
    this.currentState = null;
    this.responses = {};
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
}
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
}

module.exports = Session;