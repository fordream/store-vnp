//
//  Common.m
//  BlankProject
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Common.h"
#include <netdb.h>
#import <CommonCrypto/CommonDigest.h>
#import <SystemConfiguration/SystemConfiguration.h>
#import "KeyValue.h"

@implementation Common

@synthesize dbManager;
@synthesize highestScore;
@synthesize currentPlayerId;
@synthesize restorePlayerId;
@synthesize isSoundOn, isBackgroundSoundOn;
@synthesize languageType;


+ (Common *)sharedInstance {
	
	// the instance of this class is stored here
    static Common *myInstance = nil;
	
    // check to see if an instance already exists
    if (nil == myInstance) {
        myInstance  = [[[self class] alloc] init];
        // initialize variables here
    }
	
    // return the instance of this class
    return myInstance;
	
}


// Return system file file.
+ (NSString *) appFilePath { 
	
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES); 
	NSString *documentsDir = [paths objectAtIndex:0];
	return documentsDir;
	
}

// Return database file path 
+ (NSString *) dataBaseFilePath { 
	return [Common getFullPath : SQL_FILE_NAME];	
}

// Return full file path 
+ (NSString *) getFullPath : (NSString *) filename {
	return [[Common appFilePath] stringByAppendingPathComponent: filename];
}

// Creates a writable copy of the bundled default database in the application Documents directory.
+ (void) initialDataBase {
    // First, test for existence.
    BOOL success;
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *error;
    NSString *writableDBPath = [Common dataBaseFilePath];
    success = [fileManager fileExistsAtPath:writableDBPath];
    if (success) return;
    // The writable database does not exist, so copy the default to the appropriate location.
    NSString *defaultDBPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:SQL_FILE_NAME];
    success = [fileManager copyItemAtPath:defaultDBPath toPath:writableDBPath error:&error];
    if (!success) {
        NSAssert1(0, @"Failed to create writable database file with message '%@'.", [error localizedDescription]);
    }
}

//---check network---
+ (BOOL) isCanConnectToNetWork {
	
	// sample code for checking network from Apple
	// Create zero addy 
	struct sockaddr_in zeroAddress; 
	bzero(&zeroAddress, sizeof(zeroAddress)); 
	zeroAddress.sin_len = sizeof(zeroAddress); 
	zeroAddress.sin_family = AF_INET;
	
	// Recover reachability flags 
	SCNetworkReachabilityRef defaultRouteReachability =	SCNetworkReachabilityCreateWithAddress(NULL,(struct sockaddr *)&zeroAddress);	
	SCNetworkReachabilityFlags flags;
	
	BOOL didRetrieveFlags = SCNetworkReachabilityGetFlags( defaultRouteReachability, &flags);
	CFRelease(defaultRouteReachability);
	
	if (!didRetrieveFlags) {
		
		return NO;
	}
	BOOL isReachable = flags & kSCNetworkFlagsReachable; 
	BOOL needsConnection = flags & kSCNetworkFlagsConnectionRequired; 
	return (isReachable && !needsConnection) ? YES : NO;
	
}

+ (NSString *) createMD5ForString : (NSString*) inputString {
	
	const char *cStr = [inputString UTF8String];
	
	unsigned char result[16];
	CC_MD5( cStr, strlen(cStr), result );
	return [NSString stringWithFormat:
			@"%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X",
			result[0], result[1], result[2], result[3], 
			result[4], result[5], result[6], result[7],
			result[8], result[9], result[10], result[11],
			result[12], result[13], result[14], result[15]
			];   
	
}

+ (NSString *) createCheckSumForString : (NSString*) inputString {
	
	NSString *convertString = [NSString stringWithFormat:@"%@%@", CHECKSUM_KEY, inputString];
	return [Common createMD5ForString:convertString];
	
}

+ (void) showInvalidateDataMessage {
	
	UIAlertView *anAlert = [[UIAlertView alloc] initWithTitle:@"Lỗi Chương Trình" 
													  message:@"Dữ liệu chương trình không đúng !" 
													 delegate:self 
											cancelButtonTitle:nil 
											otherButtonTitles:nil];
	[anAlert show];
	[anAlert release];
	
}

+ (NSString *) getGameModeCategoryName : (int) gameMode {
	
	switch (gameMode) {
		case GAME_MODE_EASY:
			return LEADER_BOARD_EASY;
		case GAME_MODE_NORMAL:
			return LEADER_BOARD_NORMAL;
			break;
		case GAME_MODE_HARD:
			return LEADER_BOARD_HARD;
			break;
		case GAME_MODE_EXPERT:
			return LEADER_BOARD_EXPERT;
			break;
		default:
			return nil;
	}
	
}

+ (void) setViewToLandscape : (UIView*) viewObject {
	
	[viewObject setCenter:CGPointMake(384, 512)];
	CGAffineTransform cgCTM = CGAffineTransformMakeRotation(DEGREES_TO_RADIANS(90));
	viewObject.transform = cgCTM;
	viewObject.bounds = CGRectMake(0, 0, 1024, 768);
	
}

@end
