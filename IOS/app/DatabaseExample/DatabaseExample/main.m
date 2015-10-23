//
//  main.m
//  DatabaseExample
//
//  Created by Truong Vuong on 10/13/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DBManager.h"

int main(int argc, char *argv[]) {
    // 1. init database . copy database
	[DBManager initialDataBase];
	
	// 2. open database
	DBManager *dBManager = [[DBManager alloc]init];
	[dBManager openDB];
	
	
    NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
    int retVal = UIApplicationMain(argc, argv, nil, nil);
    [pool release];
    return retVal;
}
