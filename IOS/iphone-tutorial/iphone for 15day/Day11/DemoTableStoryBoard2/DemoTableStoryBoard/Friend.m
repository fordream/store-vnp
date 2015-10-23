//
//  Friend.m
//  DemoTableStoryBoard
//
//  Created by cuong minh on 6/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Friend.h"

@implementation Friend
@synthesize fullName=_fullName, notes=_notes, photo=_photo;

-(id)init: (NSString *) FullName withNotes: (NSString *)Notes andPhoto: (NSString *)aPhoto 
{
    if (self = [super init]){
        self.fullName  = FullName;
        self.notes = Notes;
        self.photo = [UIImage imageNamed: aPhoto];
    }
    return self;
}
@end
