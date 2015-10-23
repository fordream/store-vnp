//
//  GetResponseXMLFromRequestString.m
//  SeeTickets
//
//  Created by Kent2508 on 7/5/11.
//  Copyright 2011 __CNC Corporation__. All rights reserved.
//

#import "GetResponseXMLFromRequestString.h"


@implementation GetResponseXMLFromRequestString
@synthesize webData;
@synthesize subDelegate;
@synthesize soapMessage;
@synthesize soapActionName;
@synthesize tag;

-(void)getData {
	NSURL *url = [NSURL URLWithString:@"http://api.loxleycolour.com/loxleyservice.asmx"];
	NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:url];
	NSString *msgLength = [NSString stringWithFormat:@"%d", [soapMessage length]];
	[theRequest addValue:@"text/xml; charset=utf-8" forHTTPHeaderField:@"Content-Type"];

	[theRequest addValue:soapActionName forHTTPHeaderField:@"SOAPAction"];
	[theRequest addValue:msgLength forHTTPHeaderField:@"Content-Length"];
	[theRequest setHTTPMethod:@"POST"];
	[theRequest setHTTPBody:[soapMessage dataUsingEncoding:NSUTF8StringEncoding]];	
	
	theConnection = [[NSURLConnection alloc] initWithRequest:theRequest delegate:self];
	
	if(theConnection) {
		webData = [[NSMutableData alloc] init];
	}
	else {
		NSLog(@"theConnection is null");
	}
	
    isStillLoading = YES;
    
    // check internet time out after 10.0 seconds
    [self performSelector:@selector(checkInternetTimeOut) withObject:nil afterDelay:15.0];
}

- (void)checkInternetTimeOut {
    if (isStillLoading == YES) {
        isStillLoading = NO;
        if (self.subDelegate != nil) {
            [self.subDelegate connection:self failedLoadResponseDataFromServer:nil];
        }
        [theConnection cancel];
        [theConnection release];
        theConnection = nil;
    }
}

#pragma mark -
#pragma mark connection delegate methods


-(void)connection:(NSURLConnection*)connection didReceiveResponse:(NSURLResponse*)response {
	[webData setLength:0];
	NSHTTPURLResponse * httpResponse;
	httpResponse = (NSHTTPURLResponse *) response;	
	NSLog(@"HTTP error %zd", (ssize_t) httpResponse.statusCode);
	
}

-(void)connection:(NSURLConnection *)connection didReceiveData:(NSData*)data {
	[webData appendData:data];
}

-(void)connection:(NSURLConnection *)connection didFailWithError:(NSError*)error {
    isStillLoading = NO;
	if (self.subDelegate != nil) {
		[self.subDelegate connection:self failedLoadResponseDataFromServer:error];
	}
	[theConnection release];
}

-(void)connectionDidFinishLoading:(NSURLConnection *)connection {
    isStillLoading = NO;
	if (self.subDelegate != nil) {
		[self.subDelegate connection:self finishLoadResponseDataFromServer:webData];
	}
	[theConnection release];
}

- (void)dealloc {
	[soapMessage release];
	[soapActionName release];
	[webData release];
	[subDelegate release];
    [super dealloc];
}

@end
