
var Client = require('node-rest-client').Client;

client = new Client();

var QuizWhiz = {};

QuizWhiz.APIResponse = function() {};


QuizWhiz.REST = {
    ROOT: "http://localhost:8080/service",
    joinSession: function(authToken, sessionCode, callback) {
        var args = {
            data:{},
            headers:{"token":authToken}
        };
        client.get(this.ROOT+"/session/join/"+sessionCode, args, function (data, response) {
            callback(new APIResponse(true, null));
            return;
            if (response.statusCode != 200) {
                callback(new APIResponse(false, null));
                return;
            }
            callback(new APIResponse(true, JSON.parse(data)));
        });
    }
};

module.exports = QuizWhiz;