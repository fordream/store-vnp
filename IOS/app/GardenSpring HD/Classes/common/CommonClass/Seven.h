//
//  Seven.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/10/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Seven : NSObject {
	int a[100];
	int count;
}
-(void)create;
-(void)set:(int)index value:(int)value;
-(int)get:(int)index ;

-(void)setCount:(int)count1;
-(int)getCount;
@end
