//
//  DatabaseExampleAppDelegate.h
//  DatabaseExample
//
//  Created by Truong Vuong on 10/13/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import <UIKit/UIKit.h>

@class DatabaseExampleViewController;

@interface DatabaseExampleAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    DatabaseExampleViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet DatabaseExampleViewController *viewController;

@end

