//
//  Photo.h
//  BackgroundChooser
//
//  Created by cuong minh on 3/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Photo : NSObject
@property (nonatomic, strong) UIImage *image;
@property (nonatomic, strong) NSString* description;

- (id) initWithImage: (NSString *) imageName andDescription: (NSString *) description ;
@end
