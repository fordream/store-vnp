//
//  ScoreInfo.m
//  DauTruongViet
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "ScoreInfo.h"
#import "Common.h"
#import "NSString+AESCrypt.h"


@implementation ScoreInfo

@synthesize scoreId, gameMode, playerId, strHighestScore, checkSum;


- (ScoreInfo*) initWithScoreId : (int) nScoreId 
					  playerId : (NSString*) strPlayerId 
				  highestScore : (NSString*) theScore 
					  gameMode : (int) nGameMode
				   andCheckSum : (NSString*) strCheckSum {
	
	self = [super init];
	
	if (self != nil) {
		
		// get data from database
		self.scoreId = nScoreId;
		self.gameMode = nGameMode;
		self.playerId = strPlayerId;		// encoded data
		self.strHighestScore = theScore;	// encoded data
		self.checkSum = strCheckSum;		// encoded data
		
		// check validate of data
		NSString *decodePlayerId = [strPlayerId AES256DecryptWithKey:AES_KEY];
		NSString *decodeScore	= [theScore AES256DecryptWithKey:AES_KEY];
		NSArray *arrScore = [decodeScore componentsSeparatedByString:@"+"];
		NSString *strExtractScore = [arrScore objectAtIndex:1];
		
		// recreate checksum
		NSString *recreateCheckSum = [Common createCheckSumForString:[NSString stringWithFormat:@"%@+%@", decodePlayerId, strExtractScore]];
		
		// compare checksum
		if (![recreateCheckSum isEqualToString:strCheckSum]) {
			// NSLog(@"not correct data");
			[Common showInvalidateDataMessage];
		} else {
			NSLog(@"verify data correct");
		}
		
	}
	
	return self;
}

- (ScoreInfo *) initWithPlayerId : (NSString*) strPlayerId  
						gameMode : (int) nGameMode
				 andHighestScore : (int64_t) nHighestScore {

	self = [super init];
	
	if (self != nil) {
		
		self.playerId = [strPlayerId AES256EncryptWithKey:AES_KEY];
		self.gameMode = nGameMode;
		NSString *strScore = [NSString stringWithFormat:@"Score+%ld", nHighestScore];
		self.strHighestScore = [strScore AES256EncryptWithKey:AES_KEY];
		
		// TODO : caculate check sume
		self.checkSum = [Common createCheckSumForString:[NSString stringWithFormat:@"%@+%i+%ld", strPlayerId, nGameMode, nHighestScore]];
		
	}
	
	return self;
	
}

- (void) updateHighestScore : (int64_t) nHighestScore {

	NSString *strScore = [NSString stringWithFormat:@"Score+%ld", nHighestScore];
	self.strHighestScore = [strScore AES256EncryptWithKey:AES_KEY];
	// recaculate check sume
	NSString *strPlayerId = [playerId AES256DecryptWithKey:AES_KEY];
	self.checkSum = [Common createCheckSumForString:[NSString stringWithFormat:@"%@+%i+%ld", strPlayerId, gameMode, nHighestScore]];
	
}

- (int64_t) getLongLongScore {
	
	NSString *decodeScore = [strHighestScore AES256DecryptWithKey:AES_KEY];
	NSArray *arrScore = [decodeScore componentsSeparatedByString:@"+"];
	
	return [[arrScore objectAtIndex:1] longLongValue];
	
}

- (void)dealloc {
	
	[strHighestScore release];
	[playerId release];
	[checkSum release];
    [super dealloc];
	
}


@end
