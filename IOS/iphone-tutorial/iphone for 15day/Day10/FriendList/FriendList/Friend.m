//
//  Friend.m
//  FriendList
//
//  Created by Techmaster on 7/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Friend.h"

@implementation Friend
@synthesize fullName =_fullName;
@synthesize photo = _photo;
@synthesize notes = _notes;

-(id)init: (NSString *) FullName withNotes: (NSString *)Notes andPhoto: (NSString *)aPhoto
{
    if (self = [super init]) {
        _fullName  = FullName;
        _notes = Notes;
        _photo = [UIImage imageNamed: aPhoto];
    }
    return self;
}
@end
