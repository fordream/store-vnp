//
//  Seven.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/10/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "Seven.h"


@implementation Seven
-(void)create{
	for(int i = 0; i < 9; i ++){
		a[i] = -1;
	}
}
-(void)set:(int)index value:(int)value{
	if(0<= index && index <= 6){
		a[index] = value;
	}
}
-(int)get:(int)index {
	if(0 <= index && index <= 6){
		return a[index];
	}
	
	return - 1;
}

-(void)setCount:(int)count1{
	count = count1;
}
-(int)getCount{
	return count;
}
@end
