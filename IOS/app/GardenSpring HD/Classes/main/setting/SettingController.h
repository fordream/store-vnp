//
//  SettingController.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MenuController.h"


@interface SettingController : UIViewController {
	UIButton *btnSoundBackground;
	UIButton *btnSoundEffect;
	UIButton *btnTrainning;
	UIButton *btnTrainning1;
	UIButton *btnHome;
}
@property (nonatomic, retain) IBOutlet UIButton *btnSoundBackground;
@property (nonatomic, retain) IBOutlet UIButton *btnSoundEffect;
@property (nonatomic, retain) IBOutlet UIButton *btnTrainning;
@property (nonatomic, retain) IBOutlet UIButton *btnTrainning1;
@property (nonatomic, retain) IBOutlet UIButton *btnHome;
-(void) config;

-(IBAction) buttonPressed:(id)sender;
@end
