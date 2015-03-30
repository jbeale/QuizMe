var System = require('es6-module-loader').System;

System.import('./quizServer').then(function(server) {
    server.run(__dirname);
}).catch(function(err) {
    console.log('err', err);
});