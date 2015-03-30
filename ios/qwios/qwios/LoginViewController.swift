//
//  LoginViewController.swift
//  qwios
//
//  Created by Beale, Justin on 3/30/15.
//  Copyright (c) 2015 Justin Beale. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {

    @IBOutlet weak var usernameInput: UITextField!
    @IBOutlet weak var passwordInput: UITextField!
    
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        var username = NSUserDefaults.standardUserDefaults().stringForKey("defaultUsername");
        
        usernameInput.text = username;
        
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
            
            var err: NSError?
            var myJSON = NSJSONSerialization.JSONObjectWithData(data, options: .MutableLeaves, error:&err) as? NSDictionary
            
            if let parseJSON = myJSON {
                var success = parseJSON["success"] as? String
          
                dispatch_async(dispatch_get_main_queue()) {
                    self.updateUI(parseJSON);
                }
            }
        }
        
        task.resume()
    }
    
    func updateUI(data : NSDictionary) {
        self.activityIndicator.stopAnimating();
        if (String(data["success"] as String) == "true") {
            alertMsg("SUCCESS!! Hi"+String(data["body"] as String));
        } else {
            alertMsg("u fail");
        }
    }
    
    func alertMsg(someMessage:String) {
        var alertBox = UIAlertController(title:"Error", message:someMessage, preferredStyle:UIAlertControllerStyle.Alert);
        let okAction = UIAlertAction(title:"Ok", style:UIAlertActionStyle.Default, handler:nil);
        
        alertBox.addAction(okAction);
        
        self.presentViewController(alertBox, animated: true, completion: nil);
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
