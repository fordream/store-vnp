//
//  DataSource.h
//  BackgroundChooser
//
//  Created by cuong minh on 3/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DataSource : NSObject

@property (nonatomic, strong) NSArray *photos;
- (UIImage *) imageAtIndex : (NSUInteger) index;
- (NSString *) descriptionAtIndex : (NSUInteger) index;
@end
