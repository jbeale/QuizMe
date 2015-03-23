function User(id, firstname, lastname, username) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
};

User.prototype.getId = function() {
    return this.id;
}

User.prototype.getFirstname = function() {
    return this.firstname;
}

User.prototype.getLastname = function() {
    return this.lastname;
}

User.prototype.getUsername = function() {
    return this.username;
}

module.exports = User;