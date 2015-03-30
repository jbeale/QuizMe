//
//  InitViewController.swift
//  qwios
//
//  Created by Beale, Justin on 3/29/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit
class InitViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewDidAppear(animated: Bool) {
        
        self.performSegueWithIdentifier("loginView", sender: self);
    }
    
}

