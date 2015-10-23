//
//  GoodChild.m
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "GoodChild.h"
#import "GoodParent.h"

@implementation GoodChild
@synthesize goodParent;
@synthesize photo;
- (void) sayThanksToParent
{
    [goodParent receiveThanksFromChild];
}

- (void) receiveToyFromParent
{
    NSLog(@"Thanks Dad giving me cool toy");
    [self sayThanksToParent];
}
@end
