//
//  SQLAppDelegate.h
//  SQL
//
//  Created by iPhone SDK Articles on 10/26/08.
//  Copyright 2008 www.iPhoneSDKArticles.com.
//

#import <UIKit/UIKit.h>

@class Coffee;

@interface SQLAppDelegate : NSObject <UIApplicationDelegate> {
    
    UIWindow *window;
    UINavigationController *navigationController;
	
	//To hold a list of Coffee objects
	NSMutableArray *coffeeArray;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet UINavigationController *navigationController;

@property (nonatomic, retain) NSMutableArray *coffeeArray;

- (void) copyDatabaseIfNeeded;
- (NSString *) getDBPath;

- (void) removeCoffee:(Coffee *)coffeeObj;
- (void) addCoffee:(Coffee *)coffeeObj;

@end

