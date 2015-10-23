//
//  kim_kardashianAppDelegate.h
//  kim_kardashian
//
//  Created by Jimit on 06/02/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class kim_kardashianViewController;

@interface kim_kardashianAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    kim_kardashianViewController *viewController;
	UINavigationController *navController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet kim_kardashianViewController *viewController;
@property (nonatomic, retain) UINavigationController *navController;

@end

