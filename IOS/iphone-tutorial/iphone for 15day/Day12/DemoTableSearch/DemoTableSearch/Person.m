//
//  Person.m
//  DemoTableSearch
//
//  Created by cuong minh on 4/19/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Person.h"

@implementation Person

@synthesize fullName, age, photo;

- (id) init: (NSString*)_fullName age: (int) _age withPhoto: (NSString*) _photo
{
    if (self = [super init])
    {
        self.fullName = _fullName;
        self.age = _age;
        self.photo = [UIImage imageNamed:_photo];
    }
    return self;
}
-(void) dealloc {
    fullName = nil;
    photo = nil;
}

@end
