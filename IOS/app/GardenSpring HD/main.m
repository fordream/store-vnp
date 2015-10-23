//
//  main.m
//  GardenSpring HD
//
//  Created by truong vuong on 9/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Common.h"
#import "DBManager.h"
int main(int argc, char *argv[]) {
    // 1. Init & Open database
	NSAutoreleasePool * starterPool = [[NSAutoreleasePool alloc] init];
	
	// Creates a writable copy of the bundled default database in the application Documents directory.
	[Common initialDataBase];
	
	// Create an Install of Database Manager & sharing for all class through application
	[Common sharedInstance].dbManager = [[DBManager alloc] init];
	
	// Init a database manager object which handle all option iteract with database
	[[Common sharedInstance].dbManager openDB];
	
	//NSLog(@"%@", [[Common sharedInstance].dbManager getSettingValueForKey:SOUND_ENABLE]);
	// 1.1 check version and update value
	
    NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
    int retVal = UIApplicationMain(argc, argv, nil, nil);
    [pool release];
	[starterPool release];
    return retVal;
}
