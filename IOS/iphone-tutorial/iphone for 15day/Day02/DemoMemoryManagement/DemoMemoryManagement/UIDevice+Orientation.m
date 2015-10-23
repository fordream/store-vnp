//
//  UIDevice+Orientation.m
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/2/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import "UIDevice+Orientation.h"

@implementation UIDevice (Orientation)
- (BOOL) isLandscape
{
    return  (self.orientation == UIDeviceOrientationLandscapeLeft) ||
    (self.orientation == UIDeviceOrientationLandscapeRight);
}

- (void) doMagicThing
{
    NSLog(@"device does magic thing !");
}

@end
