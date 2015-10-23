//
//  SoundCommon.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>

@interface SoundCommon : NSObject <AVAudioPlayerDelegate> {
	
}

+(AVAudioPlayer *)	soundBackground;
+(void)	setSoundBackground:(BOOL)sound1;

+(AVAudioPlayer *)	soundLevelup;
+(AVAudioPlayer *)	soundRontate;
+(AVAudioPlayer *)	soundMenu;
+(AVAudioPlayer *)	soundGameOver;

@end
