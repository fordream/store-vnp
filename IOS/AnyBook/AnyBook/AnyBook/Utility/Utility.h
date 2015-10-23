//
//  Utility.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Utility : NSObject {
    
}

#pragma mark - Utilities
+ (NSString*)md5HexDigest:(NSString*)input;


#pragma mark - Device info
+ (BOOL) isiPhone;
+ (BOOL) isiPad;
+ (NSString*) getDeviceProfile;
+ (NSString*) getDeviceImei;
+ (BOOL) isDeviceActivated;
+ (void) setDeviceActivated;


#pragma mark - Connection
+(NSData*)RequesSynchronous:(NSURL*)url:(BOOL)showAlert;
+(NSData*)RequesSynchronous:(NSURL*)url:(BOOL)showAlert:(NSTimeInterval)timeoutInterval;




#pragma mark - Alert
+(void)showAlertOKWithTitle:(NSString *)title message:(NSString *)message;



#pragma mark - String utils
+(BOOL) isEmptyOrNull:(NSString*) string;

@end
