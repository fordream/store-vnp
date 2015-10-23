//
//  MenuViewController.h
//  Bubble Fantasy
//
//  Created by Truong Vuong on 8/20/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface MenuViewController : UIViewController {
	UIButton *btnStart;
	UIButton *btnContinue;
	UIButton *btnOptions;
	UIButton *btnHelp;
	UIButton *btnRanking;
	UIButton *btnExit;
}

//add set get
@property (nonatomic, retain) IBOutlet UIButton *btnStart;
@property (nonatomic, retain) IBOutlet UIButton *btnContinue;
@property (nonatomic, retain) IBOutlet UIButton *btnOptions;
@property (nonatomic, retain) IBOutlet UIButton *btnHelp;
@property (nonatomic, retain) IBOutlet UIButton *btnRanking;
@property (nonatomic, retain) IBOutlet UIButton *btnExit;

-(IBAction) buttonPressed:(id)sender;
@end
