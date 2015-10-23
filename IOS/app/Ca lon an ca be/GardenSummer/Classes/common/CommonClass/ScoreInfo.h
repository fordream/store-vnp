//
//  ScoreInfo.h
//  DauTruongViet
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface ScoreInfo : NSObject {

	int scoreId;
	int gameMode;
	NSString *playerId;
	NSString *strHighestScore;
	NSString *checkSum;
	
}

@property int scoreId;
@property int gameMode;
@property(nonatomic, retain) NSString *playerId;
@property(nonatomic, retain) NSString *strHighestScore;
@property(nonatomic, retain) NSString *checkSum;

- (ScoreInfo*) initWithScoreId : (int) nScoreId 
					  playerId : (NSString*) strPlayerId 
				  highestScore : (NSString*) theScore 
					  gameMode : (int) nGameMode
				   andCheckSum : (NSString*) strCheckSum;

- (ScoreInfo *) initWithPlayerId : (NSString*) strPlayerId  
						gameMode : (int) nGameMode
				 andHighestScore : (int64_t) nHighestScore;

- (void) updateHighestScore : (int64_t) nHighestScore;

- (int64_t) getLongLongScore;

@end
