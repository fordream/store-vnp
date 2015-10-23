//
//  Setting.m
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/17/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Setting.h"

@implementation Setting

@synthesize photo = _photo;
@synthesize optionName = _optionName;
@synthesize mode = _mode;

-(id) init:(NSString *)OptionName mode:(NSInteger)Mode andPhoto:(NSString *)aPhoto
{
    if (self = [super init]) {
        _optionName = OptionName;
        _mode = Mode;
        _photo = [UIImage imageNamed:aPhoto];
    }
    return self;
}

@end
