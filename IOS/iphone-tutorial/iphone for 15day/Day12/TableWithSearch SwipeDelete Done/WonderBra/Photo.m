//
//  Photo.m
//  BackgroundChooser
//
//  Created by cuong minh on 3/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Photo.h"

@implementation Photo
@synthesize image = _image;
@synthesize description = _description;

- (id) initWithImage: (NSString *) imageName andDescription: (NSString *) description {
    if (self = [super init]) {
        self.image = [UIImage imageNamed:imageName];
        self.description = description;
    }
    return self;
}
@end
