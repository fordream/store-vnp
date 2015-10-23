//
//  ClientServer.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "ClientServer.h"
#import "Utility.h"

@implementation ClientServer

#define PRIVATE_KEY @"VepMk@4231"
#define CLIENT_KEY  @"VeP@4123#"

#pragma mark - Interfaces

+ (NSString*) getServerBaseUrl
{
    return @"http://api.anybook.vn/VEP";
}

+ (NSString*) getLoginUrl:(NSString*)username wPassword:(NSString*) password
{
    NSString* md5pass = [Utility md5HexDigest:password];
    NSString* seccode = [ClientServer generateSecurityCode:username, md5pass, Nil]; //Must end with Nil parameter
    return [NSString stringWithFormat:@"%@/getAccountInfo?mobile=%@&password=%@&secure_code=%@", 
            [ClientServer getServerBaseUrl],
            [username stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [md5pass stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [seccode stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]
            ];
}

+ (NSString*) getRegisterUrl:(NSString*)fullname 
                      mobile:(NSString*)mobile
                       email:(NSString*)email
                     address:(NSString*)address
                    password:(NSString*)password
{
    NSString* md5pass = [Utility md5HexDigest:password];
    NSString* seccode = [ClientServer generateSecurityCode:fullname, mobile,email,address,md5pass, Nil]; 
    //Must end with Nil parameters
    return [NSString stringWithFormat:@"%@/registerAccount?fullname=%@&mobile=%@&email=%@&address=%@&password=%@&secure_code=%@", 
            [ClientServer getServerBaseUrl],
            [fullname stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [mobile stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [email stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [address stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [md5pass stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [seccode stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]
            ];
}





+ (NSString*) getUserBookShelfInfoUrl:(NSString*) user_msisdn
{
    NSString* seccode = [ClientServer generateSecurityCode:user_msisdn, Nil]; //Must end with Nil parameter
    return [NSString stringWithFormat:@"%@/getBookShelfInfo?mobile=%@&secure_code=%@", 
            [ClientServer getServerBaseUrl],
            [user_msisdn stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [seccode stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]
            ];    
}

+ (NSString*) getRemoveBookFromUserBookShelfurl:(NSString*) user_msisdn :(int) bookId
{
    NSString* seccode = [ClientServer generateSecurityCode:user_msisdn, bookId, Nil]; //Must end with Nil parameter
    return [NSString stringWithFormat:@"%@/removeBookFromShelf?mobile=%@&book_id=%@&secure_code=%@", 
            [ClientServer getServerBaseUrl],
            [user_msisdn stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            bookId,
            [seccode stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]
            ];
}



+ (NSString*) getBookUrl:(NSString*) user_msisdn :(int) book_id :(NSString*) device_profile :(NSString*) device_imei
{
    NSString* sBookId = [NSString stringWithFormat:@"%d", book_id];
    NSString* seccode = [ClientServer generateSecurityCode:user_msisdn, sBookId, device_profile, device_imei, Nil]; //Must end with Nil parameter
    return [NSString stringWithFormat:@"%@/getBookInfo?mobile=%@&book_id=%d&device_profile=%@&imei=%@&secure_code=%@", 
            [ClientServer getServerBaseUrl],
            [user_msisdn stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            book_id,
            [device_profile stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [device_imei stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [seccode stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]
            ];    
}


+ (NSString*) getDownloadUrl:(NSString*) user_msisdn :(NSString*) device_profile :(NSString*) device_imei :(int) book_id
{
    NSString* clientKey = [NSString stringWithFormat:@"%@", CLIENT_KEY];
    NSString* sBookId = [NSString stringWithFormat:@"%d", book_id];
    NSString* seccode = [ClientServer generateSecurityCode:user_msisdn, device_profile, device_imei, sBookId, clientKey, Nil]; //Must end with Nil parameter
    return [NSString stringWithFormat:@"%@/downloadBook?mobile=%@&device_profile=%@&imei=%@&book_id=%d&client_key=%@&secure_code=%@", 
            [ClientServer getServerBaseUrl],
            [user_msisdn stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],            
            [device_profile stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [device_imei stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            book_id,
            [clientKey stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [seccode stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]
            ];    
}


+ (NSString*) getRegisterDeviceUrl:(NSString*) user_msisdn :(NSString*) device_profile :(NSString*) device_imei
{
    NSString* action = @"reg";
    NSString* seccode = [ClientServer generateSecurityCode:user_msisdn, device_profile, device_imei, action, Nil]; //Must end with Nil parameter
    return [NSString stringWithFormat:@"%@/updateDevice?mobile=%@&device_profile=%@&imei=%@&action=%@&secure_code=%@", 
            [ClientServer getServerBaseUrl],
            [user_msisdn stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [device_profile stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [device_imei stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [action stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding],
            [seccode stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]
            ];
}





#pragma mark - Utilities

+ (NSString*) generateSecurityCode:(id) firstObject, ...
{
    BOOL InternalDebug = NO;
    
    id eachObject;
    va_list argumentList;

    if (InternalDebug) NSLog(@"List of parameters");
    NSMutableArray* list = [[NSMutableArray alloc] init];
    if (firstObject)
    {
        va_start(argumentList, firstObject); // Scanning for arguments after firstObject.
        eachObject = firstObject; //Use first object first
        while (eachObject)
        {
            if ([eachObject isKindOfClass:[NSString class]]) {
                [list addObject: (NSString*) eachObject];
                if (InternalDebug) NSLog(@"|%@|", (NSString*) eachObject);
            } else if ([eachObject isKindOfClass:[NSNumber class]]) {
                NSString* param = [((NSNumber*) eachObject) stringValue];
                [list addObject: param];
                if (InternalDebug) NSLog(@"|%@|", param);
            } else {
                DLog_Error(@"Fatal, unsupported type in generating security code, only string and number supported !");
            }
            
            eachObject = va_arg(argumentList, id);
        }
        va_end(argumentList);
    }
    
    //Add private key
    [list addObject:PRIVATE_KEY];
    if (InternalDebug) NSLog(@"|%@|", PRIVATE_KEY);

    //Combine them all
    NSString* combined = [list componentsJoinedByString:@" "];
    if (InternalDebug) NSLog(@"Combined |%@|", combined);
    
    //Calculate MD5 hash
    NSString* ret = [Utility md5HexDigest:combined];
    if (InternalDebug) NSLog(@"Seccode |%@|", ret);
    return ret;
}


@end
