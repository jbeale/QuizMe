//
//  SecondViewController.swift
//  qwios
//
//  Created by Beale, Justin on 3/27/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController {

    @IBOutlet weak var startSessionBtn: UIBarButtonItem!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        startSessionBtn.enabled = false;
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

