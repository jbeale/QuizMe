//
//  User.swift
//  qwios
//
//  Created by Beale, Justin on 3/30/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit

class User : NSObject, NSCoding {
    var id : Int = -1;
    var firstName : String = "";
    var lastName : String = "";
    var username : String = "";
    var email : String = "";
    
    init(id : Int, firstName: String, lastName:String, username:String, email:String) {
        self.id = id;
        self.firstName = firstName;
        self.lastName = lastName;
        self.username = username;
        self.email = email;
    }
    
    required convenience init(coder decoder: NSCoder) {
        self.init()
        self.id = decoder.decodeObjectForKey("id") as Int;
        self.firstName = decoder.decodeObjectForKey("firstName") as String;
        self.lastName = decoder.decodeObjectForKey("lastName") as String;
        self.username = decoder.decodeObjectForKey("username") as String;
        self.email = decoder.decodeObjectForKey("email") as String;
    }
    
    func encodeWithCoder(coder: NSCoder) {
        coder.encodeObject(self.id, forKey: "id");
        coder.encodeObject(self.firstName, forKey: "firstName");
        coder.encodeObject(self.lastName, forKey: "lastName");
        coder.encodeObject(self.username, forKey: "username");
        coder.encodeObject(self.email, forKey: "email");
    }
    
    override init() {}
}

extension User {
    func fullname() -> String {
        return firstName + " " + lastName;
    }
}