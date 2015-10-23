//
//  Parent.m
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/2/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import "Parent.h"
#import "Child.h"

@implementation Parent
@synthesize child;

- (void)dealloc
{
    [child release];
    [super dealloc];
}

@end
