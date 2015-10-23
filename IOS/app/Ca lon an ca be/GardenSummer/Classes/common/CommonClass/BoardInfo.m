    //
//  BoardInfo.m
//  PikachuGameForIpad
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BoardInfo.h"


@implementation BoardInfo

@synthesize tag, enabled;

- (id) initWithTag : (NSInteger) theTag 
	andEnableState : (NSInteger) theState {
	
	self = [super init];
	
	if (self != nil) {
		tag = theTag;
		enabled = theState;
	}
	
	return self;
	
}

- (void)dealloc {
	
    [super dealloc];
	
}

@end
