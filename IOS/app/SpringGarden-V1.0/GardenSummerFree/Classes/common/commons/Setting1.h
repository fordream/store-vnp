//
//  Setting1.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Setting1 : NSObject {

}

+(BOOL)	canSoundBackgrond;
+(void)	setCanSoundBackgrond:(BOOL)sound1;

+(BOOL)	canSoundEffect;
+(void)	setSoundEffect:(BOOL)sound1;


+(BOOL)	isTranning;
+(void)	setTrainning:(BOOL)sound1;


//+(BOOL) soundBackground;
//+(void)setSoundBackground:(BOOL)sound1;
@end
