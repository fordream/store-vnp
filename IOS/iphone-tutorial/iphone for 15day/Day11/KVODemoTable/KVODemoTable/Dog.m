//
//  Animal.m
//  KVODemoTable
//
//  Created by cuong minh on 4/4/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Dog.h"

@implementation Dog
@synthesize name;
@synthesize type;

-(id) init :(NSString*) _name withType: (DogType) _type {
    if (self = [super init]) {
        self.name = _name;
        self.type = _type;
    }
    return self;
}
@end
