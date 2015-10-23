//
//  KeyValue.m
//  VietAirline
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "KeyValue.h"


@implementation KeyValue

@synthesize strKey, strValue;

- (KeyValue *) initWithKey : (NSString *) keyName value : (NSString *) valueContent {
	
	self = [super init];
	
	if (self != nil) {
		
		self.strKey = keyName;
		self.strValue = valueContent;
		
	}
	
	return self;
	
}

- (NSComparisonResult)compare:(KeyValue *)otherKeyValue {
	
	return [self.strValue localizedCaseInsensitiveCompare:otherKeyValue.strValue];
	
}

- (void)dealloc {
	
	[strKey release];
	[strValue release];
    [super dealloc];
	
}

@end
