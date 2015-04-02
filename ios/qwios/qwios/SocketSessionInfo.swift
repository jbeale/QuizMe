//
//  SocketSessionInfo.swift
//  qwios
//
//  Created by Beale, Justin on 4/1/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import Foundation

class SocketSessionInfo : NSObject {
    var sessionCode : Int = 0;
    var sessionName : String = "";
    var isHost : Int = 0;
    var numQuestions : Int = 0;
    var ownerUser : User = User();
    
    override init() {}
    
    
}
