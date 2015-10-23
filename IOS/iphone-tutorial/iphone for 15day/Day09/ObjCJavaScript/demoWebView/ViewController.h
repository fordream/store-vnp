//
//  ViewController.h
//  demoWebView
//
//  Created by Techmaster on 7/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController<UITextFieldDelegate, UIGestureRecognizerDelegate,UIWebViewDelegate>
@property (strong, nonatomic) IBOutlet UIWebView *myWebView;
@property (strong, nonatomic) IBOutlet UIBarButtonItem *txtAddress;
- (IBAction)goHome:(id)sender;
- (IBAction)doLongpress:(id)sender;

@end
