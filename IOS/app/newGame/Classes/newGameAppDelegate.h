//
//  newGameAppDelegate.h
//  newGame
//
//  Created by mac on 8/23/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class newGameViewController;

@interface newGameAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    newGameViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet newGameViewController *viewController;

@end

