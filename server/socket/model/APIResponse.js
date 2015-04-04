/*
APIResponse
Mirrors the Java server's RestResponse object, containing the HTTP status code, op success boolean, and response body.

Author: Justin Beale
 */

function APIResponse(jsonResponse) {
    this.code = jsonResponse.code;
    this.success = jsonResponse.success;
    this.body = jsonResponse.body;
}

APIResponse.prototype.wasSuccessful = function() {
    return this.success;
};

APIResponse.prototype.getData = function () {
    return this.body;
};

APIResponse.prototype.getCode = function () {
    return this.code;
};

module.exports = APIResponse;