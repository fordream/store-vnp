//
//  JSON_Base.h
//  Copyright 2011 Vega. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface JsonBase : NSObject {
	int iCode;
	NSString *sMessage;
}

@property (nonatomic,retain) NSString* sMessage;
@property (readwrite) int iCode;


#pragma mark - Error codes

+ (JsonBase*) createParseErrorJsonBase;
- (void) setNetworkError;
- (void) setParseError;
- (void) setAuthenticateError;
- (void) setIdentifyUserError;
- (void) setLoginError;
- (BOOL) isSuccess;
- (BOOL) isNetworkError;
- (BOOL) isParseError;


- (BOOL) isClientNotAuthenticated;
- (BOOL) isUserNotAuthenticated;
- (BOOL) isPaymentRequired;

- (BOOL) isBookInEncodeProgress;
- (BOOL) isDeviceAlreadyExisted;

#pragma mark - Utilities

- (BOOL)requireBoolean:(NSDictionary*)json skey:(NSString*) skey;
- (BOOL)getBoolean:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require;

- (NSString*)requireString:(NSDictionary*)json skey:(NSString*) skey;
- (NSString*)getString:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require;

- (int)requireNumber:(NSDictionary*)json skey:(NSString*) skey;
- (int)getNumber:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require;
- (int)getNumber:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require def:(int) def;

- (float)requireFloat:(NSDictionary*)json skey:(NSString*) skey;
- (float)getFloat:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require;
- (float)getFloat:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require def:(int) def;

- (NSArray*)requireArray:(NSDictionary*)json skey:(NSString*) skey;
- (NSArray*)getArray:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require;

- (NSDictionary*)requireObject:(NSDictionary*)json skey:(NSString*) skey;
- (NSDictionary*)getObject:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require;


#pragma mark - Basic stuff

+ (JsonBase*)parseBasicJson:(NSString*)input;
- (BOOL)parseActionStatus:(NSDictionary*)jsonObjects;
- (NSDictionary*)requestSynchronousWithURLString:(NSString*)urlString withShowAlert:(BOOL)showAlert;


#pragma mark - Generic API
+ (JsonBase*) removeBookFromUserBookShelf:(NSString*) user_msisdn wBookId:(int) bookId;
+ (JsonBase*) registerDevice:(NSString*) user_msisdn :(NSString*) device_profile :(NSString*) device_imei;

@end
