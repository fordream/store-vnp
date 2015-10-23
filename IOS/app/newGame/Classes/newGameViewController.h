//
//  newGameViewController.h
//  newGame
//
//  Created by mac on 8/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/CADisplayLink.h>

@interface newGameViewController : UIViewController <UIAccelerometerDelegate> {
	IBOutlet UIImageView *ball;
	
	UIImageView *ball1[1000];
	int count1;
	
	CGPoint ballMovement;
	CGPoint ballMovement1;

	IBOutlet UILabel *scoreLable;
	int score;
	IBOutlet UIImageView *paddle;
	IBOutlet UIImageView *paddle1;
	IBOutlet UIImageView *paddle2;
	float touchOffset;
	int lives;
	int ok;
	BOOL isPlaying;
	CADisplayLink *theTimer;
	CADisplayLink *theTimer1;
	//NSTimer *theTimer;
	IBOutlet UILabel *livesLable;
	IBOutlet UILabel *mesageLable;
	IBOutlet UIImageView *you_win;
	IBOutlet UIImageView *you_win1;
	
#define BRICKS_WIDTH 5
#define BRICKS_HEIGH 4
	UIImageView *bricks[BRICKS_WIDTH][BRICKS_HEIGH];
	NSString *brickTypes[4];
	
}

@property (nonatomic, retain) UIImageView *ball;
@property (nonatomic, retain) UILabel *scoreLable;
@property (nonatomic, retain) UIImageView *paddle;
@property (nonatomic, retain) UIImageView *paddle1;
@property (nonatomic, retain) UIImageView *paddle2;
@property (nonatomic, retain) UILabel *livesLable;
@property (nonatomic, retain) UILabel *mesageLable;
@property (nonatomic, retain) UIImageView *you_win;
@property (nonatomic, retain) UIImageView *you_win1;
-(void) initializeTimer;
//-(void) animateBall:(NSTimer *)theTimer;
-(void) processPolision:(UIImageView *)brick;
-(void) initializeBricks;
-(void) startGame;
-(void) pauseGame;
-(void) saveGameState;
-(void) loadGameState;
-(void)aMethod :(id)param;
//-(void) showImgNextLevel;
//-(void) showImgNextLevel1;
//-(void) move;
@end

