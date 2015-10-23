//
//  GetResponseXMLFromRequestString.h
//  SeeTickets
//
//  Created by Kent2508 on 7/5/11.
//  Copyright 2011 __CNC Corporation__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class GetResponseXMLFromRequestString;
@protocol GetResponseXMLFromRequestStringDelegate <NSObject>

- (void)connection:(GetResponseXMLFromRequestString*)conn finishLoadResponseDataFromServer:(NSData*)data;
- (void)connection:(GetResponseXMLFromRequestString*)conn failedLoadResponseDataFromServer:(NSError*)err;

@end


@interface GetResponseXMLFromRequestString : NSObject {
	id <GetResponseXMLFromRequestStringDelegate> subDelegate;
	NSMutableData *webData;
	NSString *soapMessage;
	NSString *soapActionName;
    int tag;
    NSURLConnection *theConnection;
    BOOL isStillLoading;
}

@property(nonatomic, retain) NSMutableData *webData;
@property(nonatomic, retain) id <GetResponseXMLFromRequestStringDelegate> subDelegate;

@property(nonatomic, retain) NSString *soapMessage;
@property(nonatomic, retain) NSString *soapActionName;
@property(nonatomic) int tag;

-(void)getData;

@end
