//
//  KeyValue.h
//  VietAirline
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface KeyValue : NSObject {
	
	NSString *strKey;
	NSString *strValue;
	
}

@property(nonatomic, copy) NSString *strKey;
@property(nonatomic, copy) NSString *strValue;

- (KeyValue *) initWithKey : (NSString *) keyName value : (NSString *) valueContent;

- (NSComparisonResult)compare:(KeyValue *)otherKeyValue;


@end
