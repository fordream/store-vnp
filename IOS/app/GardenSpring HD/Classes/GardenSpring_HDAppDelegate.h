//
//  GardenSpring_HDAppDelegate.h
//  GardenSpring HD
//
//  Created by truong vuong on 9/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Setting1.h"
#import "SoundCommon.h"
@class GardenSpring_HDViewController;

@interface GardenSpring_HDAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    GardenSpring_HDViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet GardenSpring_HDViewController *viewController;

@end

