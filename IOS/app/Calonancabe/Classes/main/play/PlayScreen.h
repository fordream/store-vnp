//
//  PlayScreen.h
//  Calonancabe
//
//  Created by Truong Vuong on 10/1/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//


#import <UIKit/UIKit.h>
#import <AVFoundation/AVFoundation.h>
#import <QuartzCore/CADisplayLink.h>
#import "Phoint1.h"
#import <math.h>
#import "Common.h"
#import "CommonMusic.h"
@interface PlayScreen : UIViewController<AVAudioPlayerDelegate> {
	IBOutlet UIImageView * imgCharactor;
	IBOutlet UIImageView * imgMap;
	Phoint1 * postion;
	Phoint1 * newPosition;
	CADisplayLink *theTimer1;
	CADisplayLink *theTimer2;
	CADisplayLink *theTimer3;
	double d;
	double minHeight;
	
	UIImageView *imgBom;
	UIImageView *imgTomahoc;
	UIImageView *imgNormal[100];
	UIImageView *imgDich[100];
	int normal;
	
	IBOutlet UILabel *lbBom;
	IBOutlet UILabel *lbTomahoc;
	
	BOOL isBegin;
	
}
@property (nonatomic, retain) IBOutlet  UIImageView * imgCharactor;
@property (nonatomic, retain) IBOutlet  UIImageView * imgMap;
@property (nonatomic, retain) IBOutlet  UILabel *lbBom;
@property (nonatomic, retain) IBOutlet  UILabel *lbTomahoc;

-(void)gameLogic;
-(void)startGame;
@end
