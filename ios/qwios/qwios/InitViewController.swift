//
//  InitViewController.swift
//  qwios
//
//  Created by Beale, Justin on 3/29/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit

let logoutNotificationKey = "com.quizwhiz.qwios.logoutNotify"

class InitViewController: UIViewController {
    
    var pushedViewController : AnyObject?;
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "actOnLogoutNotification", name: logoutNotificationKey, object: nil);
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func viewDidAppear(animated: Bool) {
        var authToken = NSUserDefaults.standardUserDefaults().stringForKey("authToken");
        //for now just pretend that if there's an authToken in storage, we're logged in.
        if (true || authToken == nil) {
            self.performSegueWithIdentifier("loginView", sender: self);
        } else {
            self.performSegueWithIdentifier("openMainApp", sender: self);
        }
    }
    
    func actOnLogoutNotification() {
        //dismiss modals
        //let viewConts = self.navigationController;
        //self.childViewControllers[0].popViewControllerAnimated(true);
        pushedViewController?.dismissViewControllerAnimated(true, completion: {});
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        self.pushedViewController = segue.destinationViewController;
        
    }
}