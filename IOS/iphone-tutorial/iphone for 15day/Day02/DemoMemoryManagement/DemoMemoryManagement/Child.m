//
//  Child.m
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/2/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import "Child.h"
#import "Parent.h"

@implementation Child
@synthesize parent;

- (void)dealloc
{
    [parent release];
    [super dealloc];
}
@end
