//
//  Common1.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/9/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "Common1.h"


@implementation Common1

+(BOOL)isWin :(Point1*[19])board1 end:(Point1*[19])boardEnd{
	for(int i = 0; i < 19; i ++){
		
		if(board1[i].type != boardEnd[i].type){
			return NO;
		}
	}
	return YES;
}


+(NSMutableArray *)getList:(int)position{
	NSMutableArray* list = [[NSMutableArray alloc]initWithCapacity:0];
	Point1 *p[6];
	p[0] = [[Point1 alloc]init];
	p[1] = [[Point1 alloc]init];
	p[2] = [[Point1 alloc]init];
	p[3] = [[Point1 alloc]init];
	p[4] = [[Point1 alloc]init];
	p[5] = [[Point1 alloc]init];
	if(position == 4){
		p[0].x = 0;
		p[1].x = 1;
		p[2].x = 5;
		p[3].x = 9;
		p[4].x = 8;
		p[5].x = 3;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 5){
		p[0].x = 1;
		p[1].x = 2;
		p[2].x = 6;
		p[3].x = 10;
		p[4].x = 9;
		p[5].x = 4;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 8){
		
		p[0].x = 3;
		p[1].x = 4;
		p[2].x = 9;
		p[3].x = 13;
		p[4].x = 12;
		p[5].x = 7;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 9){
		p[0].x = 4;
		p[1].x = 5;
		p[2].x = 10;
		p[3].x = 14;
		p[4].x = 13;
		p[5].x = 8;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 10){
		p[0].x = 5;
		p[1].x = 6;
		p[2].x = 11;
		p[3].x = 15;
		p[4].x = 14;
		p[5].x = 9;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 13){
		
		p[0].x = 8;
		p[1].x = 9;
		p[2].x = 14;
		p[3].x = 17;
		p[4].x = 16;
		p[5].x = 12;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}else if(position == 14){
		p[0].x = 9;
		p[1].x = 10;
		p[2].x = 15;
		p[3].x = 18;
		p[4].x = 17;
		p[5].x = 13;
		
		[list addObject:p[0]];
		[list addObject:p[1]];
		[list addObject:p[2]];
		[list addObject:p[3]];
		[list addObject:p[4]];
		[list addObject:p[5]];
	}
	
	
	return list;
}

+(Point1*)getIndex:(int)i1 i2:(int)i2{
	Point1 *p = [[Point1 alloc]init];
	p.x = -1;
	p.y = -1;
	
	if(i1 == 0 && i2 == 1){
		p.x = 4;
		p.y = 1;
	}else if(i1 == 1 && i2 == 0){
		p.x = 4;
		p.y = 0;
	}if(i1 == 1 && i2 == 5){
		p.x = 4;
		p.y = 1;
	}else if(i1 == 5 && i2 == 1){
		p.x = 4;
		p.y = 0;
	}if(i1 == 3 && i2 == 0){
		p.x = 4;
		p.y = 1;
	}else if(i1 == 0 && i2 == 3){
		p.x = 4;
		p.y = 0;
	}if(i1 == 8 && i2 == 3){
		p.x = 4;
		p.y = 1;
	}else if(i1 == 3 && i2 == 8){
		p.x = 4;
		p.y = 0;
	}
	
	if(i1 == 4 && i2 == 1){
		p.x = 5;
		p.y = 1;
	}else if(i1 == 1 && i2 == 4){
		p.x = 5;
		p.y = 0;
	}
	
	if(i1 == 1 && i2 == 2){
		p.x = 5;
		p.y = 1;
	}else if(i1 == 2 && i2 == 1){
		p.x = 5;
		p.y = 0;
	}
	
	if(i1 == 2 && i2 == 6){
		p.x = 5;
		p.y = 1;
	}else if(i1 == 6 && i2 == 2){
		p.x = 5;
		p.y = 0;
	}
	if(i1 == 6 && i2 == 10){
		p.x = 5;
		p.y = 1;
	}else if(i1 == 10 && i2 == 6){
		p.x = 5;
		p.y = 0;
	}
	
	if(i1 == 13 && i2 == 12){
		p.x = 8;
		p.y = 1;
	}else if(i1 == 12 && i2 == 13){
		p.x = 8;
		p.y = 0;
	}
	
	if(i1 == 12 && i2 == 7){
		p.x = 8;
		p.y = 1;
	}else if(i1 == 7 && i2 == 12){
		p.x = 8;
		p.y = 0;
	}
	
	if(i1 == 7 && i2 == 3){
		p.x = 8;
		p.y = 1;
	}else if(i1 == 3 && i2 == 7){
		p.x = 8;
		p.y = 0;
	}
	
	if(i1 == 3 && i2 == 4){
		p.x = 8;
		p.y = 1;
	}else if(i1 == 4 && i2 == 3){
		p.x = 8;
		p.y = 0;
	}
	
	//9
	if(i1 == 5 && i2 == 10){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 10 && i2 == 5){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 4 && i2 == 5){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 5 && i2 == 4){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 10 && i2 == 14){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 14 && i2 == 10){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 14 && i2 == 13){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 13 && i2 == 14){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 13 && i2 == 8){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 8 && i2 == 13){
		p.x = 9;
		p.y = 0;
	}
	
	if(i1 == 8 && i2 == 4){
		p.x = 9;
		p.y = 1;
	}else if(i1 == 4 && i2 == 8){
		p.x = 9;
		p.y = 0;
	}
	
	//10
	if(i1 == 5 && i2 == 6){
		p.x = 10;
		p.y = 1;
	}else if(i1 == 6 && i2 == 5){
		p.x = 10;
		p.y = 0;
	}
	
	
	if(i1 == 6 && i2 == 11){
		p.x = 10;
		p.y = 1;
	}else if(i1 == 11 && i2 == 6){
		p.x = 10;
		p.y = 0;
	}
	
	
	if(i1 == 11 && i2 == 15){
		p.x = 10;
		p.y = 1;
	}else if(i1 == 15 && i2 == 11){
		p.x = 10;
		p.y = 0;
	}
	
	
	if(i1 == 15 && i2 == 14){
		p.x = 10;
		p.y = 1;
	}else if(i1 == 14 && i2 == 15){
		p.x = 10;
		p.y = 0;
	}
	
	//13
	if(i1 == 14 && i2 == 17){
		p.x = 13;
		p.y = 1;
	}else if(i1 == 17 && i2 == 14){
		p.x = 13;
		p.y = 0;
	}
	
	
	if(i1 == 17 && i2 == 16){
		p.x = 13;
		p.y = 1;
	}else if(i1 == 16 && i2 == 17){
		p.x = 13;
		p.y = 0;
	}
	
	
	if(i1 == 16 && i2 == 12){
		p.x = 13;
		p.y = 1;
	}else if(i1 == 12 && i2 == 16){
		p.x = 13;
		p.y = 0;
	}
	
	if(i1 == 12 && i2 == 8){
		p.x = 13;
		p.y = 1;
	}else if(i1 == 8 && i2 == 12){
		p.x = 13;
		p.y = 0;
	}
	
	//14
	
	if(i1 == 10 && i2 == 15){
		p.x = 14;
		p.y = 1;
	}else if(i1 == 15 && i2 == 10){
		p.x = 14;
		p.y = 0;
	}
	
	if(i1 == 15 && i2 == 18){
		p.x = 14;
		p.y = 1;
	}else if(i1 == 18 && i2 == 15){
		p.x = 14;
		p.y = 0;
	}
	
	if(i1 == 18 && i2 == 17){
		p.x = 14;
		p.y = 1;
	}else if(i1 == 17 && i2 == 18){
		p.x = 14;
		p.y = 0;
	}
	
	if(i1 == 17 && i2 == 13){
		p.x = 14;
		p.y = 1;
	}else if(i1 == 13 && i2 == 17){
		p.x = 14;
		p.y = 0;
	}
	return p;
}

+(Point1*)checkPoint:(int)index i1:(int)i1 i2:(int)i2 i3:(int)i3{
	Point1 *p1 = [[Point1 alloc]init];
	p1.x = -1;
	p1.y = -1;
	NSMutableArray* list = [Common1 getList:index];//[self getList:index];
	
	int _list[6];
	for(int i = 0; i < 6; i ++){
		Point1 * p = [list objectAtIndex:i];
		_list[i] = p.x;
	}
	int index1 = 0;
	int count = 0;
	for(int i = 0 ; i < 6; i ++){
		if(i1 ==_list[i]||i2 ==_list[i]||i3 ==_list[i]){
			count ++;
		}
		
		if(i1 == _list[i]){
			index1 = i;
		}
	}
	
	if(count == 3){
		p1.x = index;
		p1.y = 0;
		
		for(int i = index1 ; i < 6; i ++){
			
			if(i2 == _list[i]){
				p1.y = 1;
				break;
			}
			
		}
		
		if(index1 == 5){
			if(i2 == _list[0]){
				p1.y = 1;
			}
		}
		
		if(index1 == 0){
			if(i2 == _list[5]){
				p1.y = 0;
			}
		}
		
	}
	
	return p1;
}

+(Point1*)getIndex:(int)i1 i2:(int)i2 i3:(int)i3{
	Point1 *p = [[Point1 alloc]init];
	p.x = -1;
	p.y = -1;
	
	p = [Common1 checkPoint: 4 i1:i1 i2:i2 i3:i3];
	
	if(p.x == -1){
		p = [Common1 checkPoint: 5 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [Common1 checkPoint: 8 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [Common1 checkPoint: 9 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [Common1 checkPoint: 10 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [Common1 checkPoint: 13 i1:i1 i2:i2 i3:i3];
	}
	
	if(p.x == -1){
		p = [Common1 checkPoint: 14 i1:i1 i2:i2 i3:i3];
	}
	
	//_list = [self getList:4];
	return p;
}

+(int) getCount:(int)count{
	if(count < 3)		return count - 1;
	else if(count < 7)	return count - 4;
	else if(count < 12) return count - 8;
	else if(count < 16) return count - 13;
	else				return count - 17;
	
}

+(int) getCount1:(int)count{
	if(count < 3)		return 2;
	else if(count < 7)	return 1;
	else if(count < 12) return 0;
	else if(count < 16) return 1;
	else				return 2;
	
}

+(int) getCount2:(int)count{
	if(count < 3)		return 0;
	else if(count < 7)	return 1;
	else if(count < 12) return 2;
	else if(count < 16) return 3;
	else				return 4;
	
}

+(Seven *)random{
	Seven *seven = [[Seven alloc]init];
	[seven create];
	
	for(int i = 0; i < 9; i ++){
		int ran = arc4random()%9 + 1;
		BOOL isExists = NO;
		for(int j = 0; j < i; j++){
			if([seven get:j] == ran){
				isExists = TRUE;
				break;
			}
		}
		
		if(!isExists){
			[seven set:i value:ran];
			
		}else{
			i --;
		}
	}
	return seven;
}

+(float) calculatorRontate : (float)dx dy:(float)dy{
	float rontate =  0;
	if(dx != 0){
		rontate =  atanf(dy/dx);
		if(dx < 0 && dy < 0){
			rontate =  -PI/2 +  rontate;
		}else if(dx < 0 && dy > 0){
			rontate = 3* PI/2  +   rontate;
		}else if(dx > 0 && dy < 0){
			rontate = PI/2  +   rontate;
		}else if(dx > 0 && dy > 0){
			rontate =  PI/2  +   rontate;
		}
		
		
	}else{
		if(dy < 0){
			rontate = 0;
		}else {
			rontate = PI ;
		}
	}
	
	if(dy ==0){
		if(dx < 0){
			rontate = -PI / 2;
		}else {
			rontate = PI / 2;
		}
	}
	
	return rontate;
}
@end
