//
//  LoginViewController.swift
//  qwios
//
//  Created by Beale, Justin on 3/30/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit
import SwiftyJSON

class LoginViewController: UIViewController {

    @IBOutlet weak var usernameInput: UITextField!
    @IBOutlet weak var passwordInput: UITextField!
    
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        var username = NSUserDefaults.standardUserDefaults().stringForKey("defaultUsername");
        
        usernameInput.text = username;
        
        var tap:UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: "dismissKeyboard")
        self.view.addGestureRecognizer(tap);
    }
    
    func dismissKeyboard() {
        self.view.endEditing(true);
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func signInButtonTapped(sender: AnyObject) {
        let username = usernameInput.text;
        let password = passwordInput.text;
        self.activityIndicator.startAnimating();
        if (username.isEmpty || password.isEmpty) {
            alertMsg("Invalid username or password. Please try again.");
            return;
        }
        
        NSUserDefaults.standardUserDefaults().setObject(username, forKey: "defaultUsername");
        NSUserDefaults.standardUserDefaults().synchronize();
        
        let myUrl = NSURL(string: "http://s-quizme.justinbeale.com/service/auth/login");
        let request = NSMutableURLRequest(URL:myUrl!);
        request.HTTPMethod = "POST";
        
        // Compose a query string
        let postString = "username="+username+"&password="+password;
        
        request.HTTPBody = postString.dataUsingEncoding(NSUTF8StringEncoding);
        
        let task = NSURLSession.sharedSession().dataTaskWithRequest(request) {
            data, response, error in
            
            if error != nil
            {
                println("error=\(error)")
                return
            }
            
            // You can print out response object
            println("response = \(response)")
            
            // Print out response body
            let responseString = NSString(data: data, encoding: NSUTF8StringEncoding)
            println("responseString = \(responseString)")
            
            var jsonStr : String! = "";
            if (responseString != nil) {
                jsonStr = responseString;
            }
            
            var err: NSError?
            //var myJSON = NSJSONSerialization.JSONObjectWithData(data, options: .MutableLeaves, error:&err) as? NSDictionary
            
            let json = JSON(data: data, options:nil, error:&err);
            
            
            
            dispatch_async(dispatch_get_main_queue()) {
                self.processLoginResponse(json);
            }
            
        }
        
        task.resume()
    }
    
    func processLoginResponse(data : JSON) -> Void {
        self.activityIndicator.stopAnimating();
        let success = data["success"].boolValue;
        if (success) {
            let authToken = data["body"]["authToken"].stringValue;
            let userId = data["body"]["user"]["id"].intValue;
            let firstName = data["body"]["user"]["firstname"].stringValue;
            let lastName = data["body"]["user"]["lastname"].stringValue;
            let username = data["body"]["user"]["username"].stringValue;
            let email = data["body"]["user"]["email"].stringValue;
            let user = User(id: userId, firstName: firstName, lastName: lastName, username: username, email: email);
            storeUserData(authToken, user: user);
            
            self.performSegueWithIdentifier("showMainApp", sender: self);
            
        } else {
            alertMsg("Invalid username or password. Please try again.");
        }
    }
    
    func storeUserData(authToken:String, user:User) -> Void
    {
        NSUserDefaults.standardUserDefaults().setObject(authToken, forKey: "authToken");
        let data = NSKeyedArchiver.archivedDataWithRootObject(user);
        NSUserDefaults.standardUserDefaults().setObject(data, forKey: "user");
        NSUserDefaults.standardUserDefaults().synchronize();
    }
    
    func alertMsg(someMessage:String) {
        var alertBox = UIAlertController(title:"Error", message:someMessage, preferredStyle:UIAlertControllerStyle.Alert);
        let okAction = UIAlertAction(title:"Ok", style:UIAlertActionStyle.Default, handler:nil);
        
        alertBox.addAction(okAction);
        
        self.presentViewController(alertBox, animated: true, completion: nil);
    }
    

    

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
    }
    

}
