//
//  MenuController.h
//  GardenSummer
//
//  Created by Truong Vuong on 8/26/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SoundCommon.h"

@interface MenuController : UIViewController {
	UIButton *btnNewGame;
	UIButton *btnExit;
	UIButton *btnSetting;
	
	UIButton *btnBesores;
	UIButton *btnHelp;
	UIButton *btnMoreApp;
}

-(IBAction) buttonPressed:(id)sender;

@property (nonatomic, retain) IBOutlet UIButton *btnNewGame;
@property (nonatomic, retain) IBOutlet UIButton *btnExit;
@property (nonatomic, retain) IBOutlet UIButton *btnSetting;

@property (nonatomic, retain) IBOutlet UIButton *btnBesores;
@property (nonatomic, retain) IBOutlet UIButton *btnHelp;
@property (nonatomic, retain) IBOutlet UIButton *btnMoreApp;
@end
