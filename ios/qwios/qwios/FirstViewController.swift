//
//  FirstViewController.swift
//  qwios
//
//  Created by Beale, Justin on 3/27/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit
import Socket_IO_Client_Swift

class FirstViewController: UIViewController {

    @IBOutlet weak var sessionCodeInput: UITextField!
    @IBOutlet weak var joinBtn: UIButton!
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    
    var socket : SocketIOClient?;
    var socketSessionInfo : SocketSessionInfo?;
    var synchedQuestion : NSDictionary?;
    var synchedQuestionIndex : Int?;
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationItem.title = "Join Session";
        self.joinBtn.enabled = false;
        
        var tap:UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: "dismissKeyboard")
        self.view.addGestureRecognizer(tap);
    }
    
    func dismissKeyboard() {
        self.view.endEditing(true);
    }

    @IBAction func sessionCodeEdited(sender: UITextField, forEvent event: UIEvent) {
        let newText = formatText(sessionCodeInput.text);
        sessionCodeInput.text = newText;
        joinBtn.enabled = countElements(sessionCodeInput.text) == 11;
    }
    
    @IBAction func sessionCodeValueChanged(sender: UITextField) {
        sessionCodeInput.text = formatText(sessionCodeInput.text);
    }
    
    func formatText(text: String) -> String {
        let components = text.componentsSeparatedByCharactersInSet(NSCharacterSet.decimalDigitCharacterSet().invertedSet);
        var filtered : NSMutableString;
        filtered = NSMutableString(string: join("", components));

        if (filtered.length <= 3) {
            return filtered;
        } else if (filtered.length <= 6) {
             filtered.insertString("-", atIndex: 3);
        } else if (filtered.length <= 9) {
            filtered.insertString("-", atIndex:3);
            filtered.insertString("-", atIndex:7);
        }
        return filtered;
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func buildSocketConnForSessionCode(sessionCode : String) -> Bool {
        let authToken = NSUserDefaults.standardUserDefaults().objectForKey("authToken") as String;
        let socket = SocketIOClient(socketURL: "http://take.justinbeale.com:3001");
        socket.onAny {println("Got event: \($0.event), with items: \($0.items)")}
        socket.on("connect") {[weak self] data, ack in
            if (socket.connected == false) {
                self?.alertMsg("Could not connect to quiz server. Please check your connection.");
            }
            socket.emit("join session", authToken, sessionCode);
        }
        
        socket.on("join result") {data, ack in
            let success = data?[0] as? Int;
            
            self.activityIndicator.stopAnimating();
            if (success == 1) {
                let roomInfo = data?[1] as? NSDictionary;
                self.handleJoinResult(roomInfo!);
            } else {
                self.alertMsg("Session not found. Please check number and try again.");
            }
        }
        
        socket.on("display question") {data, ack in
            //Used to handle when, the moment we join, The server synchronizes a question. Just save it and hand it off to the quiz view right before we segue to it.
            let question = data?[0] as? NSDictionary;
            let index = data?[1] as? Int;
            self.synchedQuestion = question;
            self.synchedQuestionIndex = index;
        }
        socket.connect();
        self.socket = socket;
        
        return true;
    }
    
    func handleJoinResult(roomInfo : NSDictionary) -> Void {
        let sessionName = roomInfo.objectForKey("sessionName") as String;
        
        let sessionInfo = SocketSessionInfo();
        sessionInfo.sessionName = roomInfo.objectForKey("sessionName") as String;
        sessionInfo.sessionCode = roomInfo.objectForKey("sessionCode") as Int;
        sessionInfo.isHost = roomInfo.objectForKey("isHost") as Int;
        sessionInfo.numQuestions = roomInfo.objectForKey("numQuestions") as Int;
        
        let userDic = roomInfo.objectForKey("ownerUser") as NSDictionary;
        let ownerUser = User(id: userDic.objectForKey("id") as Int, firstName: userDic.objectForKey("firstname") as String, lastName: userDic.objectForKey("lastname") as String, username: userDic.objectForKey("username") as String, email: userDic.objectForKey("email") as String);
        sessionInfo.ownerUser = ownerUser;
        self.socketSessionInfo = sessionInfo;
        
        self.performSegueWithIdentifier("startQuiz", sender: self);
    }

    @IBAction func joinBtnPressed(sender: UIButton) {
        activityIndicator.startAnimating();
        self.synchedQuestionIndex = nil;
        self.synchedQuestion = nil;
        let success = buildSocketConnForSessionCode(sessionCodeInput.text);
        //that's all we do here. The socket callbacks have to handle any errors.
    }

    func alertMsg(someMessage:String) {
        var alertBox = UIAlertController(title:"Error", message:someMessage, preferredStyle:UIAlertControllerStyle.Alert);
        let okAction = UIAlertAction(title:"Ok", style:UIAlertActionStyle.Default, handler:nil);
        
        alertBox.addAction(okAction);
        
        self.presentViewController(alertBox, animated: true, completion: nil);
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let view = segue.destinationViewController as TakeQuizViewController;
        view.socket = self.socket;
        view.roomInfo = self.socketSessionInfo;
        view.hidesBottomBarWhenPushed = true;
        if (self.synchedQuestion != nil) {
            view.displayQuestion(self.synchedQuestion!, index:self.synchedQuestionIndex!);
        }
    }
}

