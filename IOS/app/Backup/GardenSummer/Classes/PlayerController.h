//
//  PlayerController.h
//  GardenSummer
//
//  Created by Truong Vuong on 8/28/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Point1.h"
#import "DBManager.h"
#import <AVFoundation/AVFoundation.h>
#import "SoundCommon.h"
#import "Setting1.h"
#import <math.h>

#define TOP						30
#define RIGHT					15
#define WIDTH					60
#define HEIGHT					50

#define TOP_END						10
#define LEFT_END					350
#define WIDTH_END					27
#define HEIGHT_END					23

@interface PlayerController : UIViewController <AVAudioPlayerDelegate> {
	int maxLevel;
	int minRound;
	int maxRound;
	BOOL isClockwise;
	
	UIButton * imgEnd[19];
	
	Point1 *boardFirst[19];
	Point1 *boardEnd[19];
	Point1 * board1[19];
	UIButton * boardButton1[19];
	UIImage *boardImage[19];
	
	NSMutableArray * array;
	NSMutableArray * arrayView;
	
	int score;
	int numberOfTurns;
	int level;
	UILabel *tVLevel;
	UILabel *tVNumberOfTurns;
	UILabel *tVScore;
	
	//Manager
	UIButton *btnRight;
	UIButton *btnLeft;
	UIButton *btnBack;
	UIButton *btnSetting;
	UIButton *btnReplay;
	
	//Audio
	AVAudioPlayer* player;
	
	BOOL canRun;
}

@property (nonatomic, retain) IBOutlet UIButton *btnRight;
@property (nonatomic, retain) IBOutlet UIButton *btnLeft;
@property (nonatomic, retain) IBOutlet UIButton *btnBack;
@property (nonatomic, retain) IBOutlet UIButton *btnSetting;
@property (nonatomic, retain) IBOutlet UIButton *btnReplay;
@property (nonatomic, retain) IBOutlet UILabel *tVLevel;
@property (nonatomic, retain) IBOutlet UILabel *tVNumberOfTurns;
@property (nonatomic, retain) IBOutlet UILabel *tVScore;

//

- (id)initWithNibName:(NSString *)nibNameOrNil level :(int) _level bundle:(NSBundle *)nibBundleOrNil ;
-(void)	ButtonPressed:(id)sender;
-(void)	ButtonPressedManager:(id)sender;

@end
