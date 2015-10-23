//
//  Utility.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/7/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "Utility.h"
#import "proAlertView.h"
#import <CommonCrypto/CommonDigest.h>


@implementation Utility

#pragma mark - Utility

+ (NSString*)md5HexDigest:(NSString*)input
{
    const char* str = [input UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    CC_MD5(str, strlen(str), result);
    
    NSMutableString *ret = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH*2];
    for(int i = 0; i<CC_MD5_DIGEST_LENGTH; i++) {
        [ret appendFormat:@"%02x",result[i]];
    }
    return ret;
}


#pragma mark - Device info
+ (BOOL) isiPhone
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return YES;
    }
    return NO;
}

+ (BOOL) isiPad
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        return YES;
    }
    return NO;
}

#define KEY_DEVICE_PROFILE      @"device_profile"
#define KEY_DEVICE_IMEI         @"device_imei"
#define KEY_DEVICE_ACTIVATED    @"device_activated"

+ (NSString*) getDeviceProfile
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString* profile = [defaults objectForKey:KEY_DEVICE_PROFILE];
    if (profile == nil) {
        profile = @"iOS";
        [defaults setObject:profile forKey:KEY_DEVICE_PROFILE];
        [defaults synchronize];
    }

    return profile;
}

+ (NSString*) getDeviceImei
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString* imei = [defaults objectForKey:KEY_DEVICE_IMEI];
    if (imei == nil) {
        
//        imei = @"81421d7f9f40ddd85c6ef303369ae0a3";
        
        int r = arc4random() % 74;
        imei = [Utility md5HexDigest:[NSString stringWithFormat:@"iOS_%d", r]];
        [defaults setObject:imei forKey:KEY_DEVICE_IMEI];
        [defaults synchronize];
    }
    
    NSLog(@"Imei |%@|", imei);
    
    return imei;
}

+ (BOOL) isDeviceActivated
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString* activated = [defaults objectForKey:KEY_DEVICE_ACTIVATED];
    return ((activated != Nil) && [activated isEqualToString:@"YES"]);
}

+ (void) setDeviceActivated
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:@"YES" forKey:KEY_DEVICE_ACTIVATED];
    [defaults synchronize];
}

#pragma mark - Connection

+(NSData*)RequesSynchronous:(NSURL*)url:(BOOL)showAlert
{
    return [self RequesSynchronous:url:showAlert:20];
}

+(NSData*)RequesSynchronous:(NSURL*)url:(BOOL)showAlert:(NSTimeInterval)timeoutInterval{
    DLog_Low(@"INPUT = %@", [url absoluteString]);
    
	NSHTTPURLResponse   * response;
	NSError             * error;
	NSMutableURLRequest * request;
	request = [[NSMutableURLRequest alloc] initWithURL:url	cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:timeoutInterval];
	[request setValue:@"Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16" forHTTPHeaderField:@"User-Agent"];
	
	
	NSData* data = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];	
	
	NSInteger codeStatus = [response statusCode];
    DLog_Low(@"codeStatus = %d", codeStatus);
	
	switch (codeStatus) {
            //case 0:
		case 400:
		case 401:
		case 404:
		case 500:
			if (showAlert == YES) {
				[self showAlertOKWithTitle:STRING_ERROR message:[NSString stringWithFormat:@"Error code:%i",codeStatus]];
			}
			data = nil;
			break;
		case 402:
			if(showAlert == YES){
				[self showAlertOKWithTitle:STRING_NOTICE message:@"Payment Required"];
			}
			data = nil;
			break;
		case 403:
			if(showAlert == YES){
				[self showAlertOKWithTitle:STRING_NOTICE message:@"Forbidden"];	
			}
			data = nil;
			break;
		case 501:
			if(showAlert == YES){
				[self showAlertOKWithTitle:STRING_NOTICE message:@"Not implemented"];	
			}
			data = nil;
			break;
		case 502:
			if(showAlert == YES){
				[self showAlertOKWithTitle:STRING_ERROR message:@"Service temporarily overloaded"];	
			}
			data = nil;
			break;
		case 503:
			if(showAlert == YES){
				[self showAlertOKWithTitle:STRING_ERROR message:@"Gateway timeout 503"];	
			}
			data = nil;
			break;
		default:
			break;
	}
    
    // test
    if (data != nil) {
        //Parse
        NSString* rawJson = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
        if (rawJson != nil) {
            DLog_Low(@"OUTPUT = %@", rawJson);
        }
    }
	return data;	
}


#pragma mark - Alert

+(void)showAlertOKWithTitle:(NSString *)title message:(NSString *)message{
    proAlertView *alert = [[proAlertView alloc] initWithTitle:title message:message
                                                     delegate:self cancelButtonTitle:STRING_OK otherButtonTitles: nil];
    alert.alertType = TYPE_OK;
    [alert setBackgroundColor:[UIColor colorWithPatternImage:[UIImage imageNamed:@"bg_root.png"]] withStrokeColor:[UIColor clearColor] alpha:0.8f];
    [alert show];	
}

#pragma mark - String utils

+(BOOL) isEmptyOrNull:(NSString*) cached
{
    if (cached == Nil) {
        return YES;
    }
    
    NSString* trim = [cached stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
    if ([trim length] == 0) {
        return YES;
    }
    
    if ([trim isEqualToString:@"(null)"]) {
        return YES;
    }
    
    return NO;
}

@end
