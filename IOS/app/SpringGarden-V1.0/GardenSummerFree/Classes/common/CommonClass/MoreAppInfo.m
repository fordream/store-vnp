//
//  MoreAppInfo.m
//  VietAirline
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "MoreAppInfo.h"


@implementation MoreAppInfo

@synthesize idValue, appName, appLink, appIcon, appImage, appIntro;

- (void)dealloc {
	
	[appName release];
	[appLink release];
	[appIcon release];
	[appImage release];
	[appIntro release];

    [super dealloc];
	
}


@end
