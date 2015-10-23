//
//  MyClass.m
//  DemoGetSet
//
//  Created by cuong minh on 2/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
// 

#import "MyClass.h"

//MyClass.m file
@implementation MyClass

static NSString *companyName = @"Techmaster";

+ (NSString*)companyName
{
    return companyName;
}


- (id) init
{
    if (self = [super init]){
        //text = @"Hello World";
        self.text = @"Hello World";
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
    return text;  // Getter thuan tuy tra ve du lieu
}

-(void) setText:(NSString *)textValue
{
    if (textValue != text) //Kiem tra khac biet roi moi gan
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