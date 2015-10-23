//
//  ViewController.h
//  TimerDemo
//
//  Created by cuong minh on 3/11/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController
{
    float dblElapsedSeconds;
    NSTimer * tmrElapsedTime;
}
@property (strong, nonatomic) IBOutlet UILabel *labelCountDown;

@end
