//
//  Common.m
//  Calonancabe
//
//  Created by Truong Vuong on 10/2/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Common.h"


@implementation Common
+(BOOL) compare:(double) d1 D2:(double)d2{
	return 0 <= d2 - d1 &&d2 - d1 <= 1 || 0<= d1 - d2 &&d1 - d2 <= 1;
}
@end
