//
//  RestClient.h
//  DatabaseExample
//
//  Created by Truong Vuong on 10/14/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "KeyValue.h"

@interface RestClient : NSObject {
	
	NSString *server;
	// method is POST, GET, DELETE, PUT
	NSMutableArray *params;
	NSMutableArray *headers;
	
	//response
	NSURLResponse *response; 
	NSError *error; 
} 
@property(retain) NSURLResponse *response; 
@property(retain)NSError *error; 


-(RestClient*)init:(NSString*)_server;
-(void) addHeader :(NSString*)key value: (NSString*)value;
-(void) addParams :(NSString*)key value: (NSString*)value;

// for execute
-(void)executePOST;
-(void)executeGET;
-(void)executePUT;
-(void)executeDELETE;
@end
