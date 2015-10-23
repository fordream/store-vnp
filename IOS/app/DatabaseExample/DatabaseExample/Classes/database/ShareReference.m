//
//  ShareReference.m
//  DatabaseExample
//
//  Created by Truong Vuong on 10/14/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import "ShareReference.h"


@implementation ShareReference
static ShareReference *myInstance = nil;

@synthesize defaults;


+ (ShareReference *)sharedInstance{
    // check to see if an instance already exists
    if (nil == myInstance) {
        myInstance  = [[[self class] alloc] init];
        // initialize variables here
    }
	
	if(myInstance.defaults == nil){
		myInstance.defaults = [NSUserDefaults standardUserDefaults];

	}
	
	
    // return the instance of this class
    return myInstance;
}

-(void)setObject :(NSString*)data forKey:(NSString*)key{
	if(defaults== nil){
		defaults = [NSUserDefaults standardUserDefaults];
	} 
	[defaults setObject:data forKey:key];
	[defaults synchronize];
}

-(NSString *)getString:(NSString*)key{
	if(defaults== nil){
		defaults = [NSUserDefaults standardUserDefaults];
	} 
	return [defaults stringForKey:key];
}
@end
