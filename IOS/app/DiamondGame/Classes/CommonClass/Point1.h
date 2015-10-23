//
//  Point1.h
//  DiamondGame
//
//  Created by Truong Vuong on 8/9/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Point1 : NSObject {
	int x;
	int y;
	int type;
	int magic;
}

@property(nonatomic, readwrite) int x;
@property(nonatomic, readwrite) int y;
@property(nonatomic, readwrite) int type;
@property(nonatomic, readwrite) int magic;

@end
