//
//  ViewLogin.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ViewRegister.h"
#import "ViewLibrary.h"
#import "ViewTabLibrary.h"
@interface ViewLogin : UIViewController <UITextFieldDelegate> {
    
    IBOutlet UITextField *txtUsername;
    IBOutlet UITextField *txtPassword;
    IBOutlet UIButton *btnLogin;
    IBOutlet UIButton *btnRegister;
    IBOutlet UIButton *btnRememberPassword;
    CachedDataStore* dataStore;
    
}

- (IBAction)onRegister:(id)sender;
- (IBAction)onLogin:(id)sender;
- (IBAction)onRememberPassword:(id)sender;

@end
