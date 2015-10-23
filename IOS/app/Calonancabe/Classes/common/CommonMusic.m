//
//  CommonMusic.m
//  Calonancabe
//
//  Created by Truong Vuong on 10/1/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "CommonMusic.h"
static AVAudioPlayer* soundBackground;
static AVAudioPlayer* soundBang;
static AVAudioPlayer* soundnormal;
@implementation CommonMusic
+(AVAudioPlayer *)	soundBackground{
	if(soundBackground == nil){
		NSString *strSoundPath = [[NSBundle mainBundle] pathForResource:@"background1" ofType:@"mp3"];
		soundBackground = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath] error:NULL];
		soundBackground.numberOfLoops = -1;
	}
	
	return soundBackground;
}

+(AVAudioPlayer *)	soundBang{
	if(soundBang == nil){
		NSString *strSoundPath = [[NSBundle mainBundle] pathForResource:@"menu" ofType:@"mp3"];
		soundBang = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath] error:NULL];
		soundBang.numberOfLoops = 0;
	}
	
	return soundBang;
}

+(AVAudioPlayer *)	soundnormal{
	if(soundnormal == nil){
	NSString *strSoundPath = [[NSBundle mainBundle] pathForResource:@"gunnormal" ofType:@"mp3"];
	soundnormal = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath] error:NULL];
	soundnormal.numberOfLoops = 0;
	}
	
	return soundnormal;
}
@end
