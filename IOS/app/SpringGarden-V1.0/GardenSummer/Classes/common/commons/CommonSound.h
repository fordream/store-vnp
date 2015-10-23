//
//  CommonSound.h
//  GardenSummer
//
//  Created by Truong Vuong on 8/30/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AudioToolbox/AudioServices.h>
#import <AVFoundation/AVFoundation.h>

@interface CommonSound : NSObject {
	SystemSoundID volleyFileID;
	SystemSoundID clappingFileID;

}
@property(nonatomic) SystemSoundID volleyFileID;
@property(nonatomic) SystemSoundID clappingFileID;


-(void) soundRun;
@end
