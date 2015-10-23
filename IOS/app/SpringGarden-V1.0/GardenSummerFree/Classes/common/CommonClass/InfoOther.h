//
//  InfoOther.h
//  PikachuGameForIpad
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface InfoOther : NSObject {
	
	int level;
	int score;
	int time;
	int life;
	int suggest;
	int numberStarEnabled;
	int valueNameImg;
	int cheDoChoi;
	NSString *checkSum;
	
}

@property(nonatomic, readwrite) int level;
@property(nonatomic, readwrite) int score;
@property(nonatomic, readwrite) int time;
@property(nonatomic, readwrite) int life;
@property(nonatomic, readwrite) int suggest;
@property(nonatomic, readwrite) int numberStarEnabled;
@property(nonatomic, readwrite) int valueNameImg;
@property(nonatomic, readwrite)	int cheDoChoi;
@property(nonatomic, retain) NSString *checkSum;

- (void) createCheckSumForGameBoard : (NSArray*) listButton;

@end
