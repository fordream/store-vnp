//
//  ViewController.h
//  DemoUISwitch
//
//  Created by cuong minh on 3/29/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
@property (weak, nonatomic) IBOutlet UISwitch *aSwitch;
@property (weak, nonatomic) IBOutlet UILabel *switchLabel;
@property (weak, nonatomic) IBOutlet UIImageView *bulbImage;
- (void) switchON_OFF: (UISwitch *)sender;
@end
