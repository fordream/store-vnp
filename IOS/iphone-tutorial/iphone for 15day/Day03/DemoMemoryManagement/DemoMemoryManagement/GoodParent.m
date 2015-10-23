//
//  GoodParent.m
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "GoodParent.h"

@implementation GoodParent
@synthesize goodChild;

- (void) giveChildAToy
{
    [goodChild receiveToyFromParent];
}

- (void) receiveThanksFromChild
{
    NSLog(@"Hey baby, you are always welcome !");
}

@end
