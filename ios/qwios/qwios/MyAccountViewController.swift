//
//  MyAccountViewController.swift
//  qwios
//
//  Created by Beale, Justin on 3/30/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit

class MyAccountViewController: UIViewController {

    @IBOutlet weak var profilePic: UIImageView!
    @IBOutlet weak var fullnameLabel: UILabel!
    @IBOutlet weak var emailLabel: UILabel!
    @IBOutlet weak var usernameLabel: UILabel!
    @IBOutlet weak var changePasswordBtn: UIButton!
    @IBOutlet weak var signOutBtn: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        var user = User();
        if let data = NSUserDefaults.standardUserDefaults().objectForKey("user") as? NSData {
            user = NSKeyedUnarchiver.unarchiveObjectWithData(data) as User
        }
        fullnameLabel.text = user.fullname();
        emailLabel.text = user.email;
        usernameLabel.text = user.username;
        self.tabBarItem.title = user.firstName;
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBAction func changePasswordPrompt(sender: UIButton) {
        
        
    }
    
    @IBAction func signOutPressed(sender: UIButton) {
        signoutPrompt();
    }
    
    func signoutPrompt() {
        var alertBox = UIAlertController(title:"Sign out", message:"Are you sure?", preferredStyle:UIAlertControllerStyle.Alert);
        let okAction = UIAlertAction(title:"Yes", style:UIAlertActionStyle.Default, handler: {(alert: UIAlertAction!) in self.logout()});
        let cancelAction = UIAlertAction(title:"No", style:UIAlertActionStyle.Default, handler:nil);
        alertBox.addAction(okAction);
        alertBox.addAction(cancelAction);
        
        self.presentViewController(alertBox, animated: true, completion: nil);
    }
    
    func logout() {
        //do something.
        println("logout");
        NSNotificationCenter.defaultCenter().postNotificationName(logoutNotificationKey, object: nil);
        
        
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
