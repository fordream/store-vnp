//
//  ClientServer.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ClientServer : NSObject

#pragma mark - Interfaces

+ (NSString*) getServerBaseUrl;

// Users
+ (NSString*) getLoginUrl:(NSString*)username wPassword:(NSString*) password;
+ (NSString*) getRegisterUrl:(NSString*)fullname 
                      mobile:(NSString*)mobile
                       email:(NSString*)email
                     address:(NSString*)address
                    password:(NSString*)password;


// Users Book Shelf
+ (NSString*) getUserBookShelfInfoUrl:(NSString*) user_msisdn;
+ (NSString*) getRemoveBookFromUserBookShelfurl:(NSString*) user_msisdn :(int) bookId;


// Book Store 
+ (NSString*) getBookUrl:(NSString*) user_msisdn :(int) book_id :(NSString*) device_profile :(NSString*) device_imei;
+ (NSString*) getDownloadUrl:(NSString*) user_msisdn :(NSString*) device_profile :(NSString*) device_imei :(int) book_id;

// Devices
+ (NSString*) getRegisterDeviceUrl:(NSString*) user_msisdn :(NSString*) device_profile :(NSString*) device_imei;

#pragma mark - Utilities

+ (NSString*) generateSecurityCode:(id) first, ...; // This method takes a nil-terminated list of objects.

@end
