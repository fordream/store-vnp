//
//  TACAppDelegate.h
//  NavigationTutorial
//
//  Created by kent franks on 12/29/11.
//  Copyright (c) 2011 TheAppCodeBlog. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TACAppDelegate : UIResponder <UIApplicationDelegate, UITabBarControllerDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) UITabBarController *tabBarController;

@end
