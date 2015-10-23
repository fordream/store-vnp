//
//  MySubClass.m
//  HelloWorld
//
//  Created by cuong minh on 3/22/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MySubClass.h"

@implementation MySubClass
- (id) init
{
    if (self = [super init])
    {
        self->protectNumber = 100;
        self.text = @"The world is changing";
    }
    return self;
}
- (void) logNumbers
{
    //NSLog(@"Private Number %f", self->secretNumber);
    NSLog(@"Public Number %f", self->publicNumber);
    NSLog(@"Protect Number %f", self->protectNumber);
    //NSLog(@"Protect Number %f", super->protectNumber);
}
@end
