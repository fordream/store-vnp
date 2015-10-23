//
//  WinGameController.h
//  DiamondGame
//
//  Created by Truong Vuong on 8/18/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface WinGameController : UIViewController {
	UIButton *btnBack;
}
@property(nonatomic, retain) IBOutlet UIButton *btnBack;

-(IBAction) back:(id)sender;
@end
