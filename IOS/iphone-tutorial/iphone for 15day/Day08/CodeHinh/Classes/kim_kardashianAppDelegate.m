//
//  kim_kardashianAppDelegate.m
//  kim_kardashian
//
//  Created by Jimit on 06/02/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "kim_kardashianAppDelegate.h"
#import "kim_kardashianViewController.h"

@implementation kim_kardashianAppDelegate

@synthesize window;
@synthesize viewController;
@synthesize navController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {    
    [application setStatusBarHidden:TRUE];
	if(viewController==nil){
		kim_kardashianViewController *nvc = [[kim_kardashianViewController alloc] initWithNibName:@"kim_kardashianViewController" bundle:[NSBundle mainBundle]];
		self.viewController = nvc;
		[nvc release];
	}
	if(navController==nil){
		UINavigationController *nc = [[UINavigationController alloc] initWithRootViewController:viewController];
		self.navController = nc;
		[nc release];
	}
    // Override point for customization after app launch    
    [window addSubview:navController.view];
    [window makeKeyAndVisible];
}


- (void)dealloc {
    [viewController release];
    [window release];
    [super dealloc];
}


@end
