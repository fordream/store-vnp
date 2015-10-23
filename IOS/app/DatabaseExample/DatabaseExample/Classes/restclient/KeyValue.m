//
//  KeyValue.m
//  DatabaseExample
//
//  Created by Truong Vuong on 10/14/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import "KeyValue.h"


@implementation KeyValue
@synthesize _key,_value;

-(KeyValue*)init :(NSString*)__key _value:(NSString*)__value{
	self =[super init];
	
	self._key = __key;
	self._value = __value;
	return self;
}

@end
