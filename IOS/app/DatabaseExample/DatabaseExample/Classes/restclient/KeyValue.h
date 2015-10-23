//
//  KeyValue.h
//  DatabaseExample
//
//  Created by Truong Vuong on 10/14/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface KeyValue : NSObject {
	NSString *_key;
	NSString *_value;
}

@property(retain) NSString *_key;
@property(retain) NSString *_value;


-(KeyValue*)init :(NSString*)__key _value:(NSString*)__value;

@end
