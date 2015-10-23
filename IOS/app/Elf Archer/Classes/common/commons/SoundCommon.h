//
//  SoundCommon.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>
#import "Setting1.h"
@interface SoundCommon : NSObject <AVAudioPlayerDelegate> {
	
}

+(AVAudioPlayer *)	soundBackground;
+(void)	setSoundBackground:(BOOL)sound1;

+(AVAudioPlayer *)	soundLevelup;
+(AVAudioPlayer *)	soundRontate;
+(AVAudioPlayer *)	soundMenu;
+(AVAudioPlayer *)	soundGameOver;
+(void) createSoundBackground;
+(void) createSoundLevelUp;
+(void) createSoundRontate;
+(void) createSoundMenu;
+(void) createSoundGameOver;

+(void) createBee;
+(AVAudioPlayer *)	soundBee;

+(void) createBird;
+(AVAudioPlayer *)	soundBird:(BOOL)isSing;

@end
