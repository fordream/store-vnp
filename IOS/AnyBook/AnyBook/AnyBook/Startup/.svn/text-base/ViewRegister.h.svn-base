//
//  ViewRegister.h
//  AnyBook
//
//  Created by Vuong Truong on 4/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JsonLogin.h"

@interface ViewRegister : UIViewController <UITextFieldDelegate> {
    IBOutlet UIButton *btnRegister;
    IBOutlet UIButton *btnBoqua;
    IBOutlet UIButton *btnHome;
    
    IBOutlet UIScrollView*scrollView;
    IBOutlet UITextField *tfFullName;
    IBOutlet UITextField *tfMobile;
    IBOutlet UITextField *tfEmail;
    IBOutlet UITextField *tfAddress;
    IBOutlet UITextField *tfPassword;
    IBOutlet UITextField *tfRePassword;
    
    UITextField* activeField;
}

- (IBAction) onRegister:(id)sender;
- (BOOL) checkForms;
- (void) hiddenKeyboard;
- (IBAction)backgroundTouched:(id)sender;
- (IBAction)nextPressed:(id) sender;

@end
