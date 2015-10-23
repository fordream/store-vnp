//
//  MyClass.m
//  DemoGetSet
//
//  Created by cuong minh on 2/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
// 

#import "MyClass.h"


@implementation MyClass

- (id) init
{
    if (self = [super init]){
        text = @"Hello World";
        secretNumber = 3.14156;
        publicNumber = 10.0;
        protectNumber = 25.5;
    }
    return self;
}


- (void)logText
{
    NSLog(@"%@ %f", text, publicNumber);
}

-(NSString*) text
{
    return text;
}

-(void) setText:(NSString *)textValue
{
    if (textValue != text)
    {
        [textValue retain];
        [text release];
        text = textValue;
    }
}

-(void)dealloc
{
    [text release];
    [super dealloc];
}

@end