    //
//  InfoOther.m
//  PikachuGameForIpad
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "InfoOther.h"
#import "BoardInfo.h"
#import "Common.h"

@implementation InfoOther

@synthesize level, score, time, life, suggest, numberStarEnabled, valueNameImg, cheDoChoi, checkSum;

- (void) createCheckSumForGameBoard : (NSArray*) listButton {
	
	NSMutableString *strValidate = [NSMutableString stringWithCapacity:0];
	[strValidate appendFormat:@"%i%i%i%i%i%i%i%i", level, score, time, life, suggest, numberStarEnabled, valueNameImg, cheDoChoi];
	for (int i = 0; i < [listButton count]; i++) {
		BoardInfo *info = [listButton objectAtIndex:i];
		[strValidate appendFormat:@"%i%i", info.tag, info.enabled];
	}
	
	self.checkSum = [Common createMD5ForString:strValidate];
	
}

- (void) dealloc {
	
	[checkSum release];
    [super dealloc];
	
}


@end
