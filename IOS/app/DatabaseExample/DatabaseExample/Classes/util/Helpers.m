//
//  Helpers.m
//  DatabaseExample
//
//  Created by Truong Vuong on 10/14/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import "Helpers.h"

static AVAudioPlayer* soundBackground;
static Helpers* helpers;

@implementation Helpers

+(Helpers*) shareInstance{
	if(helpers == nil){
		helpers = [[Helpers alloc]init];
	}
	
	return helpers;
}
+(AVAudioPlayer *)	soundBackground{
	return soundBackground;
}

+(void)	setSoundBackground:(BOOL)sound1{
	if(soundBackground == nil){
		
		NSString *strSoundPath = [[NSBundle mainBundle] pathForResource:@"springGarden_backGround1" ofType:@"mp3"];
		soundBackground = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath] error:NULL];
		soundBackground.numberOfLoops = -1;
	}
}
@end
