//
//  JSON_Base.m
//  Copyright 2011 Vega. All rights reserved.
//
#import "JsonBase.h"
#import "JSON/JSON.h"
#import "AppDelegate.h"
#import "Utility.h"
#import "ClientServer.h"

/***************************************************************************************************
 *
 *                                     JSON_Song
 *
 ***************************************************************************************************/

#define NETWORK_ERROR_CODE              -1001
#define PARSE_ERROR_CODE                -1002
#define INVALID_REQUEST_ERROR_CODE      -1

#define DATA_MALFORM_ERROR_CODE         98
#define SERVICE_ERROR_CODE              99
#define BOOK_IN_ENCODE_PROGRESS         3
#define DEVICE_ALREADY_EXISTED          3


#define IDENTIFY_USER_ERROR_CODE        401
#define PAYMENT_REQUIRED_ERROR_CODE     402
#define AUTHENTICATE_ERROR_CODE         403
#define CANNOT_IDENTIFY_ERROR_CODE      1

@implementation JsonBase

@synthesize sMessage, iCode;

//------------------------------------------------------------------------------------
// ERROR CODES

#pragma mark - Error codes

+ (JsonBase*) createParseErrorJsonBase
{
    JsonBase* base = [[JsonBase alloc] init];
    [base setParseError];
    return base;
}

- (void) setNetworkError
{
    self.iCode = NETWORK_ERROR_CODE;
    self.sMessage = @"Không thể kết nối dịch vụ, vui lòng kiểm tra lại kết nối mạng!";
}

- (void) setParseError
{
    self.iCode = PARSE_ERROR_CODE;
    self.sMessage = @"Dịch vụ không trả lời, vui lòng thử lại sau!";
}

- (void) setAuthenticateError
{
    self.iCode = AUTHENTICATE_ERROR_CODE;
    self.sMessage = @"Không thể xác thực client, vui lòng thử lại sau!";
}

- (void) setIdentifyUserError
{
    self.iCode = IDENTIFY_USER_ERROR_CODE;
    self.sMessage = @"Lỗi đăng nhập, vui lòng thử lại sau!";
}

- (void) setLoginError
{
    if (self.iCode == AUTHENTICATE_ERROR_CODE) {
        [self setAuthenticateError];
    } 
    else if (self.iCode == IDENTIFY_USER_ERROR_CODE)
    {
        [self setIdentifyUserError];
    }
}

- (BOOL) isSuccess
{
    return (self.iCode == 0);
}
- (BOOL) isNetworkError
{
    return (self.iCode == NETWORK_ERROR_CODE);
}
- (BOOL) isParseError
{
    return (self.iCode == PARSE_ERROR_CODE);    
}

- (BOOL) isClientNotAuthenticated
{
    return (self.iCode == AUTHENTICATE_ERROR_CODE);
}

- (BOOL) isUserNotAuthenticated
{
    return (self.iCode == IDENTIFY_USER_ERROR_CODE);
}

- (BOOL) isPaymentRequired
{
    return (self.iCode == PAYMENT_REQUIRED_ERROR_CODE);
}

- (BOOL) isBookInEncodeProgress
{
    return (iCode == BOOK_IN_ENCODE_PROGRESS);
}

- (BOOL) isDeviceAlreadyExisted
{
    return (iCode == DEVICE_ALREADY_EXISTED);
}

//------------------------------------------------------------------------------------
// UTILITIES
#pragma mark - Utilities

- (BOOL)requireBoolean:(NSDictionary*)json skey:(NSString*) skey
{
    return [self getBoolean:json skey:skey require:TRUE];
}

- (BOOL)getBoolean:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require
{
    NSObject* msgo = [json objectForKey:skey];
    if ([msgo isKindOfClass:[NSNumber class]]) {
        return [(NSNumber*) msgo boolValue];
    } else {
        if (require) {
            self.iCode = PARSE_ERROR_CODE; //Parse error
        }
        return NO;
    }
}

- (NSString*)requireString:(NSDictionary*)json skey:(NSString*) skey
{
    return [self getString:json skey:skey require:TRUE];
}

- (NSString*)getString:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require
{
    NSObject* msgo = [json objectForKey:skey];
    if ([msgo isKindOfClass:[NSString class]]) {
        return (NSString*) msgo;
    } else {
        if (require) {
            self.iCode = PARSE_ERROR_CODE; //Parse error
        }
        return @"";
    }
}

- (int)requireNumber:(NSDictionary*)json skey:(NSString*) skey
{
    return [self getNumber:json skey:skey require:TRUE];
}

- (int)getNumber:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require
{
    return [self getNumber:json skey:skey require:require def:0];
}

- (int)getNumber:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require def:(int)def
{
    NSObject* msgo = [json objectForKey:skey];
    if ([msgo isKindOfClass:[NSNumber class]]) {
        return [(NSNumber*)msgo intValue];
    } else {
        if (require) {
            self.iCode = PARSE_ERROR_CODE; //Parse error
        }
        return def;
    }
}

- (float)requireFloat:(NSDictionary*)json skey:(NSString*) skey
{
    return [self getFloat:json skey:skey require:YES];
}

- (float)getFloat:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require
{
    return [self getFloat:json skey:skey require:YES def:0];
}

- (float)getFloat:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require def:(int) def
{
    NSObject* msgo = [json objectForKey:skey];
    if ([msgo isKindOfClass:[NSNumber class]]) {
        return [(NSNumber*)msgo floatValue];
    } else {
        if (require) {
            self.iCode = PARSE_ERROR_CODE; //Parse error
        }
        return def;
    }
}

- (NSArray*)requireArray:(NSDictionary*)json skey:(NSString*) skey
{
    return [self getArray:json skey:skey require:TRUE];
}

- (NSArray*)getArray:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require
{
    NSObject* msgo = [json objectForKey:skey];
    if ([msgo isKindOfClass:[NSArray class]]) {
        return (NSArray*) msgo;
    } else {
        if (require) {
            self.iCode = PARSE_ERROR_CODE; //Parse error
        }
        return [[NSArray alloc] init];
    }
}

- (NSDictionary*)requireObject:(NSDictionary*)json skey:(NSString*) skey
{
    return [self getObject:json skey:skey require:YES];
}

- (NSDictionary*)getObject:(NSDictionary*)json skey:(NSString*) skey require:(Boolean) require
{
    NSObject* msgo = [json objectForKey:skey];
    if ([msgo isKindOfClass:[NSDictionary class]]) {
        return (NSDictionary*) msgo;
    } else {
        if (require) {
            self.iCode = PARSE_ERROR_CODE; //Parse error
        }
        return [[NSDictionary alloc] init];
    }    
}

//------------------------------------------------------------------------------------
// BASIC STUFF
#pragma mark - Basic stuff

+(JsonBase*)parseBasicJson:(NSString*)input
{
	// Create a dictionary from the JSON string
	NSDictionary *jsonObjects = [input JSONValue];
    JsonBase* result = [[JsonBase alloc] init];
    [result parseActionStatus:jsonObjects];
    return result;
}

- (BOOL) parseActionStatus:(NSDictionary*) jsonObjects
{
    self.iCode = [self getNumber:jsonObjects skey:@"code" require:TRUE];
    self.sMessage = [self getString:jsonObjects skey:@"message" require:TRUE];
    
	return (self.iCode == 0);
}

-(NSDictionary*)requestSynchronousWithURLString:(NSString*)urlString withShowAlert:(BOOL)showAlert
{
	NSData* returnData = nil;
	NSString* parseString = nil;
    
	returnData = [Utility RequesSynchronous:[NSURL URLWithString:urlString]:showAlert];
	if(returnData == nil){
		[self setNetworkError];
        return FALSE;
	}
    
    parseString = [[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding];
    NSDictionary *jsonObjects = [parseString JSONValue];
    [self parseActionStatus:jsonObjects];
    return jsonObjects;
}

//------------------------------------------------------------------------------------
// GENERIC API
#pragma mark - Generic API

+ (JsonBase*) removeBookFromUserBookShelf:(NSString*) user_msisdn wBookId:(int) bookId
{
    NSString* url = [ClientServer getRemoveBookFromUserBookShelfurl:user_msisdn :bookId];
    JsonBase* js = [[JsonBase alloc] init];
    [js requestSynchronousWithURLString:url withShowAlert:NO];
    return js;
}

+ (JsonBase*) registerDevice:(NSString*) user_msisdn :(NSString*) device_profile :(NSString*) device_imei
{
    NSString* url = [ClientServer getRegisterDeviceUrl:user_msisdn :device_profile :device_imei];
    JsonBase* js = [[JsonBase alloc] init];
    [js requestSynchronousWithURLString:url withShowAlert:NO];
    return js;
}
@end