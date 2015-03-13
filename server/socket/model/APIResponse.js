//Created by jbeale

function APIResponse(success, data) {
    this.success = success;
    this.data = data;
}

APIResponse.prototype.wasSuccessful = function() {
    return this.success;
}

APIResponse.prototype.getData = function () {
    return this.data;
}