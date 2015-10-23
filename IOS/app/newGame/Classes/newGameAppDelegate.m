//
//  newGameAppDelegate.m
//  newGame
//
//  Created by mac on 8/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "newGameAppDelegate.h"
#import "newGameViewController.h"
#import "ImageView.h"

@implementation newGameAppDelegate

@synthesize window;
@synthesize viewController;


#pragma mark -
#pragma mark Application lifecycle

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {    
    [self.window addSubview:viewController.view];
    [self.window makeKeyAndVisible];

    return YES;
}


- (void)applicationWillResignActive:(UIApplication *)application {
	[viewController pauseGame];
	[viewController saveGameState];
}
- (void)applicationDidBecomeActive:(UIApplication *)application {
    [viewController startGame];
	[viewController loadGameState];
}
- (void)applicationWillTerminate:(UIApplication *)application {
    [viewController pauseGame];
	[viewController loadGameState];
}
- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application {
	[ImageView releaseCache];
}


- (void)dealloc {
    [viewController release];
    [window release];
    [super dealloc];
}


@end
