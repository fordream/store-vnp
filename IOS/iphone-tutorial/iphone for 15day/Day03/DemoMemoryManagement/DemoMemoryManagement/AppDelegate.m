//
//  AppDelegate.m
//  DemoMemoryManagement
//
//  Created by cuong minh on 2/29/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import "AppDelegate.h"

#import "ViewController.h"

@implementation AppDelegate

@synthesize window = _window;
@synthesize viewController = _viewController;

#pragma mark - Other functions
- (void) initViewControllerProperties {
    self.viewController.primitiveNumber = 10;
    
    UIImage* localImagePointer = [UIImage imageNamed: @"SteveJobs.png"];
    self.viewController.myImage = localImagePointer;
        
    NSLog(@"localImagePointer retain count %d", [localImagePointer retainCount]);
    
    NSLog(@"self.viewController.myImage retain count %d", [self.viewController.myImage retainCount]);
    
    /*Comment out this line, and run Analyze, XCode will complain "Incorrect decrement of the reference count of an object that is not owned ...."
     when deploy to hardware, app sometimes crashes !
     the reason we do not need to send release to localImagePointer because, when _viewController is released, it will send release to all its properties
    */
    //[localImagePointer release];
    
    NSLog(@"After [localImagePointer release], localImagePointer retain count %d", [localImagePointer retainCount]);
    
    NSLog(@"After [localImagePointer release], self.viewController.myImage retain count %d", [self.viewController.myImage retainCount]);
    
    
    
}

#pragma mark - Application Delegate functions

- (void)dealloc
{
    [_window release];
    [_viewController release];   
    [super dealloc];
}


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]] autorelease];
    // Override point for customization after application launch.
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        self.viewController = [[[ViewController alloc] initWithNibName:@"ViewController_iPhone" bundle:nil] autorelease];
    } else {
        self.viewController = [[[ViewController alloc] initWithNibName:@"ViewController_iPad" bundle:nil] autorelease];
    }
    
    [self initViewControllerProperties];
    
    self.window.rootViewController = self.viewController;
    [self.window makeKeyAndVisible];
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
}



@end
