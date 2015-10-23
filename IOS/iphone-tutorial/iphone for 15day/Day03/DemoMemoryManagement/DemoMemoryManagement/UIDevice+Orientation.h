//
//  UIDevice+Orientation.h
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/2/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIDevice (Orientation) //Orientation is category name
@property (nonatomic, readonly) BOOL isLandscape;

- (void) doMagicThing;
@end
