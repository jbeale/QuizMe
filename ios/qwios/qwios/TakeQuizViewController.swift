//
//  TakeQuizViewController.swift
//  qwios
//
//  Created by Beale, Justin on 4/1/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit
import Socket_IO_Client_Swift


class TakeQuizViewController: UIViewController {
    
    var socket : SocketIOClient?;
    var roomInfo : SocketSessionInfo?;
    var questionWasSet : Bool = false;
    var questionOpen : Bool = false;
    var lastSentSelection : Int = -1;
    
    let selectedBgColor = UIColor(red:74/255,green:203/255,blue:253/255,alpha:1);
    let standardBgColor = UIColor(red:239/255,green:239/255,blue:244/255,alpha:1);
    let correctBgColor = UIColor(red:0,green:100/255,blue:0,alpha:1);
    let incorrectBgColor = UIColor(red:139/255,green:0,blue:0,alpha:1);

    @IBOutlet weak var waitingForInstructorText: UILabel!
    @IBOutlet weak var waitingForInstructorIndicator: UIActivityIndicatorView!
    @IBOutlet weak var questionIndexLabel: UILabel!
    @IBOutlet weak var promptLabel: UILabel!
    @IBOutlet weak var answer0Btn: UIButton!
    @IBOutlet weak var answer1Btn: UIButton!
    @IBOutlet weak var answer2Btn: UIButton!
    @IBOutlet weak var answer3Btn: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        if (!questionWasSet) {
            waitingToStartState();
        }
        patchSocketDefs(self.socket!); //The socket must be set by whoever segues to this view
        self.navigationItem.title = roomInfo?.sessionName;
        self.navigationItem.backBarButtonItem = nil;
    }
    
    func patchSocketDefs(socket: SocketIOClient) {
        socket.on("display question") {data, ack in
            let question = data?[0] as? NSDictionary;
            let index = data?[1] as? Int;
            self.displayQuestion(question!, index: index!);
        }
        socket.on("question closed") {data, ack in
            self.questionOpen = false;
        }
        socket.on("reveal correct ans") {data, ack in
            let correctIndex = data?[0] as? Int;
            self.setCorrectBtn(correctIndex!)
            if (self.lastSentSelection != correctIndex) {
                //lost
                self.setIncorrectBtn(self.lastSentSelection);
            }
        }
    }
    
    func setCorrectBtn(index: Int) {
        if (index == 0) {
            self.answer0Btn.backgroundColor = self.correctBgColor;
        } else if (index == 1) {
            self.answer1Btn.backgroundColor = self.correctBgColor;
        } else if (index == 2) {
            self.answer2Btn.backgroundColor = self.correctBgColor;
        } else if (index == 3) {
            self.answer3Btn.backgroundColor = self.correctBgColor;
        }
    }
    func setIncorrectBtn(index: Int) {
        if (index == 0) {
            self.answer0Btn.backgroundColor = self.incorrectBgColor;
        } else if (index == 1) {
            self.answer1Btn.backgroundColor = self.incorrectBgColor;
        } else if (index == 2) {
            self.answer2Btn.backgroundColor = self.incorrectBgColor;
        } else if (index == 3) {
            self.answer3Btn.backgroundColor = self.incorrectBgColor;
        }
    }
    
    func selectAns(index: Int) {
        
        if (self.questionOpen) {
            if (self.lastSentSelection != index) {
                //button mashing socket flood filter lolz
                socket!.emit("select answer", index);
                self.lastSentSelection = index;
            }
            if (index == 0) {
                resetButtonColors();
                self.answer0Btn.backgroundColor = self.selectedBgColor;
            } else if (index == 1) {
                resetButtonColors();
                self.answer1Btn.backgroundColor = self.selectedBgColor;
            } else if (index == 2) {
                resetButtonColors();
                self.answer2Btn.backgroundColor = self.selectedBgColor;
            } else if (index == 3) {
                resetButtonColors();
                self.answer3Btn.backgroundColor = self.selectedBgColor;
            }
        }
    }
    
    func waitingToStartState() {
        questionIndexLabel.hidden = true;
        promptLabel.hidden = true;
        answer0Btn.hidden = true;
        answer1Btn.hidden = true;
        answer2Btn.hidden = true;
        answer3Btn.hidden = true;
        waitingForInstructorText.hidden = false;
        waitingForInstructorIndicator.startAnimating();
    }
    
    func takingQuizState() {
        questionIndexLabel.hidden = false;
        promptLabel.hidden = false;
        answer0Btn.hidden = false;
        answer1Btn.hidden = false;
        answer2Btn.hidden = false;
        answer3Btn.hidden = false;
        waitingForInstructorText.hidden = true;
        waitingForInstructorIndicator.stopAnimating();
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func answer0BtnPressed(sender: UIButton) {
        selectAns(0);
    }
    @IBAction func answer1BtnPressed(sender: UIButton) {
        selectAns(1);
    }
    @IBAction func answer2BtnPressed(sender: UIButton) {
        selectAns(2);
    }
    @IBAction func answer3BtnPressed(sender: UIButton) {
        selectAns(3);
    }
    func resetButtonColors() {
        self.answer0Btn.backgroundColor = self.standardBgColor;
        self.answer1Btn.backgroundColor = self.standardBgColor;
        self.answer2Btn.backgroundColor = self.standardBgColor;
        self.answer3Btn.backgroundColor = self.standardBgColor;
    }
    func displayQuestion(rawQuestion : NSDictionary, index:Int) {
        self.questionOpen = true;
        self.lastSentSelection = -1;
        resetButtonColors();
        let choices = rawQuestion.objectForKey("choices") as NSArray;
        
        let prompt = rawQuestion.objectForKey("prompt") as String;
        self.promptLabel.text = prompt;
        
        let answer0 = choices[0] as NSDictionary;
        self.answer0Btn.setTitle(answer0.objectForKey("text") as String, forState: .Normal);
        
        let answer1 = choices[1] as NSDictionary;
        self.answer1Btn.setTitle(answer1.objectForKey("text") as String, forState: .Normal);
        
        let answer2 = choices[2] as NSDictionary;
        self.answer2Btn.setTitle(answer2.objectForKey("text") as String, forState: .Normal);
        
        let answer3 = choices[3] as NSDictionary;
        self.answer3Btn.setTitle(answer3.objectForKey("text") as String, forState: .Normal);
        
        
        let stringIndex = String(index+1);
        let stringTotal = String(self.roomInfo!.numQuestions);
        
        self.questionIndexLabel.text = "QUESTION " + stringIndex + "/" + stringTotal;
        
        self.takingQuizState();
        self.questionWasSet = true;
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
