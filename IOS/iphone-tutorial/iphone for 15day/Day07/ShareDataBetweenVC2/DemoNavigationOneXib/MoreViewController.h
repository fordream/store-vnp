//
//  MoreViewController.h
//  DemoNavigationOneXib
//
//  Created by cuong minh on 2/25/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AnotherViewController.h"
@interface MoreViewController : UIViewController <UITextFieldDelegate>
@property (strong, nonatomic) IBOutlet UITextField *myText;
@property (strong, nonatomic) IBOutlet UISlider *mySlider;
@property (strong, nonatomic) NSString *text;
@property (assign, nonatomic) float value;
@property (nonatomic, weak) AnotherViewController *anotherViewController;
@end
