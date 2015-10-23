//
//  NSLogUtils.m
//  Untitled
//
//  Created by Truong Vuong on 10/12/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import "NSLogUtils.h"


@implementation NSLogUtils
@synthesize tag;
-(NSLogUtils*)init : (NSString*)_tag{
	self = [super init];
	tag = _tag;
	return self;
	
}

// truong vuong add
-(void) log :(NSString*)message{
	NSLog(@"TAG %@ : %@", tag, message);
}


-(void) dealloc{
	[super dealloc];
}

@end
