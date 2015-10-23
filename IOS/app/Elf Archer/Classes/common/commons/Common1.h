//
//  Common1.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/9/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Point1.h"
#import "Seven.h"
#define PI						3.14159265
@interface Common1 : NSObject {

}
+(NSMutableArray *)getList:(int)position;
+(BOOL)isWin :(Point1*[19])board1 end:(Point1*[19])boardEnd;
+(Point1*)getIndex:(int)i1 i2:(int)i2;
+(Point1*)checkPoint:(int)index i1:(int)i1 i2:(int)i2 i3:(int)i3;
+(Point1*)getIndex:(int)i1 i2:(int)i2 i3:(int)i3;

+(int) getCount :(int)count;
+(int) getCount1 :(int)count;

+(int) getCount2 :(int)count;

+(Seven*)random;
+(float) calculatorRontate : (float)dx dy:(float)dy;
@end
