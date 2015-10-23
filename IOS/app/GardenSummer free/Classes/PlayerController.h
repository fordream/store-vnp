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

#import "Common1.h"
#import <math.h>
#import "Score_item.h"
#import <QuartzCore/CADisplayLink.h>
#import "Seven.h"

#define TOP						20
#define LEFT					80
#define WIDTH					60
#define HEIGHT					50

#define TOP_END						195
#define LEFT_END					370
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
	
	UIImageView * uiImageVIew;
	UIImageView * uiImageVIew1;
	int w1,h1;
	//Audio
	AVAudioPlayer* player;
	
	BOOL canRun;
	
	CADisplayLink *theTimer1;
	CADisplayLink *theTimer2;
	CADisplayLink *theTimer3;
	Point1 * pNext;
	
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

-(void)	ButtonPressedManager:(id)sender;
-(void)initBoard:(int)mode;
-(void) viewLevelAndTurns;
-(void) DrawBoard:(int)aStyle;
-(void)rontate0:(int)position Direction:(BOOL)direction;
-(void) rontate1:(int[])list Direction:(BOOL)direction;
-(int) getIndex:(Point1*)p;
-(UIImageView *)  addViewImage :(int)x y:(int)y status:(int) status;


@end
