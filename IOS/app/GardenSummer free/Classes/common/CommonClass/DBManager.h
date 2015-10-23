//
//  DBManager.h
//  BlankProject
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "sqlite3.h"
#import "Common.h"
#import "KeyValue.h"
#import "MoreAppInfo.h"

#import "BoardInfo.h"
#import "InfoOther.h"
#import "ScoreInfo.h"
#import "Score.h"

@interface DBManager : NSObject {
	NSMutableArray *arrBoard;
	
	//InfoOther *infoOther;
	
@private
	sqlite3 *db;

}

- (BOOL) openDB;

- (NSMutableArray *) getKeyValueWithIdColumnName : (NSString *) idColumnName 
								 valueColumnName : (NSString *) valueColumnName
									   fromTable : (NSString *) tableName;

- (NSMutableArray *) getKeyValueNotOrderWithIdColumnName : (NSString *) idColumnName 
										 valueColumnName : (NSString *) valueColumnName
											   fromTable : (NSString *) tableName;

- (NSArray*) getListMoreApp;


//===================================================================
- (int) CountRow:(NSString *)tableName;
- (void) deleteRowOfTable:(NSString *)tableName ;

-(void) createTableInfoOfButtonOnBoard:(NSString *) tableName 
							withField1:(NSString *)tag 
							withField2:(NSString *) enaled;

//===================================================================
- (void) insertSummaryInfo : (InfoOther*) input;
- (InfoOther*) getSummaryInfoForMode : (NSInteger) gameMode;

- (void) insertBoardInfo : (NSMutableArray*) listInfoButtonOfBoard;

- (NSString*) getSettingValueForKey : (NSString*) strKey;
- (void) setValue : (NSString*) strValue forSettingKey : (NSString*) strKey;

- (int) getGameMode;

// - (BOOL) isExistPlayerId : (NSString*) playerId;
- (BOOL) isExistPlayerId : (NSString*) playerId andGameMode : (int) gameMode;
- (void) insertScoreInfo : (ScoreInfo*) score;
- (void) updateScoreInfo : (ScoreInfo*) score;
- (void) deleteSocreInfoForId : (int) scoreId;
// - (void) deleteSocreInfoForPlayerId : (NSString*) playerId;
// - (ScoreInfo*) getScoreInfoForPlayerId : (NSString*) playerId;
- (void) deleteSocreInfoForPlayerId : (NSString*) playerId 
						andGameMode : (int) gameMode;

- (ScoreInfo*) getScoreInfoForPlayerId : (NSString*) playerId 
						   andGameMode : (int) gameMode;

- (NSArray*) getlistScoreForPlayerId : (NSString*) playerId;
//tvuong 1

- (NSMutableArray *) getListStart:(int)level;
- (NSMutableArray *) getListEnd:(int)level;
- (int) getMaxlevel;
- (int) getMaxlevelStore;
- (void) updateStore :(int)level status:(int)status score: (int)score;
- (void) updateStore1 :(int)status level: (int) level score:(int)score;
- (BOOL) isExistStore : (int) level;
- (void) insertStore : (int) level;
- (Score*) getSocre;

@end
