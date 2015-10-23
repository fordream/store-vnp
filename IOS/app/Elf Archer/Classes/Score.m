//
//  Score.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/11/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "Score.h"


@implementation Score
-(void)create{
	for(int i = 0; i < 50; i ++){
		p[i] = nil;
	}
}
-(void)add:(Point1 *)p1{
	for(int i = 0; i < 50; i ++){
		if(p[i] == nil){
			p[i] = p1;
			return;
		}
	}
}
-(Point1 *)get:(int)index{
	if(0<= index && index < 50){
		return p[index];
	}
	
	return nil;
}
@end
