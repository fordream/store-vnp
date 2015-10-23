//
//  RestClient.m
//  DatabaseExample
//
//  Created by Truong Vuong on 10/14/12.
//  Copyright 2012 Hung Yen. All rights reserved.
//

#import "RestClient.h"


@implementation RestClient
@synthesize error,response;
-(RestClient*)init:(NSString*)_server{
	self = [super init];
	params = [[NSMutableArray alloc]init];
	headers =[[NSMutableArray alloc]init]; 
	server = _server;
	return self;
}

-(void) addHeader :(NSString*)key value: (NSString*)value{
	KeyValue *keyValue = [[KeyValue alloc]init:key _value:value];
	[headers addObject:keyValue];
}

-(void) addParams :(NSString*)key value: (NSString*)value{
	KeyValue *keyValue = [[KeyValue alloc]init:key _value:value];
	[params addObject:keyValue];
}

//for execute
-(void)executePOST{
	NSString*url = [NSString stringWithFormat:@"%@",server];
	NSURL *myURL = [NSURL URLWithString:url];
	
	NSMutableURLRequest *myRequest = [NSMutableURLRequest requestWithURL:myURL];
	[myRequest setValue:@"text/xml" forHTTPHeaderField:@"Content-type"]; 
	[myRequest setHTTPMethod:@"POST"]; 
	//[myRequest setHTTPBody:myData];

	NSData *myReturn = [NSURLConnection sendSynchronousRequest:myRequest 
										returningResponse:&response 
	 									error:&error];
	
}

-(void)executeGET{

}

-(void)executePUT{

}

-(void)executeDELETE{

}
@end
