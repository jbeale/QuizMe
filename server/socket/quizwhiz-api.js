
var Client = require('node-rest-client').Client;
var APIResponse = require('./model/APIResponse.js');

client = new Client();

var QuizWhiz = {};



QuizWhiz.REST = {
    ROOT: "http://localhost:8080/service",
    joinSession: function(authToken, sessionCode, callback) {
        var args = {
            data:{},
            headers:{"token":authToken}
        };
        client.get(this.ROOT+"/session/join/"+sessionCode, args, function (data, response) {

            //if (response.statusCode != 200)

            callback(new APIResponse(data));
        });
    },
    getUser: function (authToken, callback) {
        var args = {
            data:{},
            headers:{}
        };
        client.get(this.ROOT+"/auth/token/"+authToken, args, function(data, response) {
            callback(new APIResponse(data));
        });
    }
};

module.exports = QuizWhiz;