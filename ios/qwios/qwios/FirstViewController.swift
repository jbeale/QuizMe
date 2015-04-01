//
//  FirstViewController.swift
//  qwios
//
//  Created by Beale, Justin on 3/27/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController {

    @IBOutlet weak var sessionCodeInput: UITextField!
    @IBOutlet weak var joinBtn: UIButton!
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
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

    @IBAction func joinBtnPressed(sender: UIButton) {
        activityIndicator.startAnimating();
        
        
        activityIndicator.stopAnimating();
    }

}

