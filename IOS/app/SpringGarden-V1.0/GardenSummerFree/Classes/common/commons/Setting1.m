//
//  Setting1.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "Setting1.h"

static BOOL canSoundBackgrond;
static BOOL canSoundEffect;
static BOOL isTranning;
//static BOOL soundBackground;
@implementation Setting1



+(BOOL)	canSoundBackgrond{
	return canSoundBackgrond;
}
+(void)	setCanSoundBackgrond:(BOOL)sound1{
	canSoundBackgrond = sound1;
}

+(BOOL)	canSoundEffect{
	return canSoundEffect;
}

+(void)	setSoundEffect:(BOOL)sound1{
	canSoundEffect = sound1;
}


+(BOOL)	isTranning{
	return isTranning;
}
+(void)	setTrainning:(BOOL)sound1{
	isTranning = sound1;
}

-(void) dealloc{
	[super dealloc];
}
@end
