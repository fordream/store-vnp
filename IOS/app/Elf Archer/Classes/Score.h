//
//  Score.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/11/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Point1.h"

@interface Score : NSObject {
	Point1 *p [50];
}
-(void)create;
-(void)add:(Point1 *)p1;
-(Point1 *)get:(int)index;
@end
