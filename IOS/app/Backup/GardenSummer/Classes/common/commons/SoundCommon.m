//
//  SoundCommon.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "SoundCommon.h"

static AVAudioPlayer* soundBackground;
static AVAudioPlayer* soundLevelup;
static AVAudioPlayer* soundRontate;
static AVAudioPlayer* soundMenu;
static AVAudioPlayer* soundGameOver;

@implementation SoundCommon


+(AVAudioPlayer *)	soundBackground{
	return soundBackground;
}

+(AVAudioPlayer *)	soundLevelup{
	return soundLevelup;
}

+(AVAudioPlayer *)	soundRontate{
	return soundRontate;
}

+(AVAudioPlayer *)	soundMenu{
	return soundMenu;
}

+(AVAudioPlayer *)	soundGameOver{
	return soundGameOver;
}

+(void)	setSoundBackground:(BOOL)sound1{
	[self createSoundBackground];
	[self createSoundLevelUp];
	[self createSoundRontate];	
	[self createSoundMenu];
	[self createSoundGameOver];

}

+(void) createSoundBackground{
	if(soundBackground == nil){
		
		NSString *strSoundPath = [[NSBundle mainBundle] pathForResource:@"springGarden_backGround1" ofType:@"mp3"];
		soundBackground = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath] error:NULL];
		soundBackground.numberOfLoops = -1;
	}
}
+(void) createSoundLevelUp{
	if(soundLevelup == nil){
		NSString *strSoundPath1 = [[NSBundle mainBundle] pathForResource:@"springGarden_levelUp_Sound" ofType:@"mp3"];
		soundLevelup = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath1] error:NULL];
		soundLevelup.numberOfLoops = 0;
	}
}

+(void) createSoundRontate{
	if(soundRontate == nil){
		NSString *strSoundPath2 = [[NSBundle mainBundle] pathForResource:@"springGarden_movingSound" ofType:@"mp3"];
		soundRontate = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath2] error:NULL];
		soundRontate.numberOfLoops = 0;
	}
}


+(void) createSoundMenu{
	if(soundMenu == nil){
		NSString *strSoundPath3 = [[NSBundle mainBundle] pathForResource:@"springGarden_stuckSound-2" ofType:@"mp3"];
		soundMenu = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath3] error:NULL];
		soundMenu.numberOfLoops = 0;
	}
}

+(void) createSoundGameOver{
	if(soundGameOver == nil){
		NSString *strSoundPath4 = [[NSBundle mainBundle] pathForResource:@"springGarden_GameOver_Sound" ofType:@"mp3"];
		soundGameOver = [[AVAudioPlayer alloc] initWithContentsOfURL:[NSURL fileURLWithPath: strSoundPath4] error:NULL];
		soundGameOver.numberOfLoops = 0;
	}
}

@end
