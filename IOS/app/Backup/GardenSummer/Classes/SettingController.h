//
//  SettingController.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SettingController : UIViewController {
	UIButton *btnSoundBackground;
	UIButton *btnSoundEffect;
	UIButton *btnTrainning;
	UIButton *btnHome;
}
@property (nonatomic, retain) IBOutlet UIButton *btnSoundBackground;
@property (nonatomic, retain) IBOutlet UIButton *btnSoundEffect;
@property (nonatomic, retain) IBOutlet UIButton *btnTrainning;
@property (nonatomic, retain) IBOutlet UIButton *btnHome;


-(IBAction) buttonPressed:(id)sender;
@end
