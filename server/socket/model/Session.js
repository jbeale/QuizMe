function Session() {
    this.data = null;
    this.participants = null;
    this.currentQuestionIndex = -1;
    this.clientCount = 0;
    this.currentState = null;
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


module.exports = Session;