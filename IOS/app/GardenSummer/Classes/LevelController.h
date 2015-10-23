//
//  LevelController.h
//  GardenSummer
//
//  Created by Truong Vuong on 8/30/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Point1.h"
#import "DBManager.h"
#import "Setting1.h"
#import "SoundCommon.h"

@interface LevelController : UIViewController {
	int top  ;
	int left ;
	int width ;
	int height ;
	UIButton *btnLevel[100];
	UIButton *btnHome;
	UIButton *btnReset;
	Point1 * position[100];
}
@property (nonatomic, retain) IBOutlet UIButton *btnHome;
@property (nonatomic, retain) IBOutlet UIButton *btnReset;

-(IBAction) buttonPessed:(id)sender;
-(void)config;

@end
