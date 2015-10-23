//
//  AppDelegate.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "AppDelegate.h"
#import "UserAccount.h"
#import "ViewLogin.h"


@implementation AppDelegate

@synthesize window = _window;
@synthesize navigation;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    CachedDataStore* cds = [CachedDataStore getInstance];
    UserAccount* account = [cds getCurrentUserAccount];
    
    BOOL autoLogin = NO;
    if (![Utility isEmptyOrNull:account.user_mobile] && ![Utility isEmptyOrNull:account.user_password]) {
        JsonLogin* jl = [JsonLogin loginWithUsername:account.user_mobile password:account.user_password];
        autoLogin = [jl isSuccess];
    }
    
    if (!autoLogin) {
        // open Login view
        ViewLogin* vl = [[ViewLogin alloc] init];
        navigation = [[UINavigationController alloc] initWithRootViewController:vl];
        self.window.rootViewController = navigation;
    }
    else {
        // open Library view
        ViewTabLibrary* vc = [[ViewTabLibrary alloc] init];
        self.window.rootViewController = vc;
    }   
    
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
