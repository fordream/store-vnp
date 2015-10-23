//
//  DBManager.m
//  BlankProject
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DBManager.h"
#import "sqlite3.h"
#import "BoardInfo.h"
#import "InfoOther.h"
#import "NSString+AESCrypt.h"
#import "Point1.h"

@implementation DBManager

// Open Database 
- (BOOL) openDB { 
	//---create database--- 
	if (sqlite3_open([[Common dataBaseFilePath] UTF8String], &db) != SQLITE_OK ) {
		sqlite3_close(db); 
		NSAssert(0, @"Database failed to open.");
		return false;
	}
	else {
		return true;
	}
}

//------------------------------------------------------------------------------------
// SAMPLE ABOUT INSERT, DELETE, UPDATE database
//------------------------------------------------------------------------------------

// Binding data
/*
 sqlite3_bind_int(statement, 1, [strDishId intValue] );
 sqlite3_bind_text(statement, 2, [[fmt stringFromDate:toDate] UTF8String], -1, SQLITE_STATIC);
 */
/*
 - (void) updateFavoriteWithLevel : (int) level withDishId : (NSString *) strId {
 
 sqlite3_stmt *updateStmt;
 const char *sql = "UPDATE Dish SET dishFavoritesLevel = ? WHERE dishID = ?";
 
 if (sqlite3_prepare_v2(db, sql, -1, &updateStmt, NULL) != SQLITE_OK) {
 NSAssert1(0, @"Error while creating update statement. '%s'", sqlite3_errmsg(db));
 }
 
 // binding data Note : start from 1
 sqlite3_bind_int(updateStmt, 1, level);
 sqlite3_bind_int(updateStmt, 2, [strId intValue]);
 
 // execute update
 if(SQLITE_DONE != sqlite3_step(updateStmt))
 NSAssert1(0, @"Error while updating. '%s'", sqlite3_errmsg(db));
 
 sqlite3_reset(updateStmt);
 
 }
 
 
 - (MenuInfo*) getMenuInfoByMenuName : (NSString *) menuName {
 
 sqlite3_stmt *statement;
 const char *sql = "SELECT * FROM Menu WHERE menuName = ?";
 
 if (sqlite3_prepare_v2(db, sql, -1, &statement, NULL) != SQLITE_OK) {
 NSAssert1(0, @"Error while creating update statement. '%s'", sqlite3_errmsg(db));
 }
 
 // binding data Note : start from 1
 sqlite3_bind_text(statement, 1, [menuName UTF8String], -1, SQLITE_TRANSIENT);
 
 // execute command
 if(sqlite3_step(statement) == SQLITE_ROW)
 {
 
 // menuID
 char *field1 = (char *) sqlite3_column_text(statement, 0); 
 NSString *menuId = [[NSString alloc] initWithUTF8String: field1];
 
 // menuName
 char *field2 = (char *) sqlite3_column_text(statement, 1); 
 NSString *menuName = [[NSString alloc] initWithUTF8String: field2];
 
 // menuFavoritesLevel
 char *field3 = (char *) sqlite3_column_text(statement, 2); 
 NSString *favoriteLevel = [[NSString alloc] initWithUTF8String: field3];
 
 // create menu info object
 MenuInfo *info = [[MenuInfo alloc] initWithMenuId:menuId 
 name:menuName 
 favoriteLevel:favoriteLevel];
 
 [menuId release];
 [menuName release];
 [favoriteLevel release];
 
 //---deletes the compiled statement from memory--- 
 sqlite3_finalize(statement);
 
 return info;
 
 } else {
 
 //---deletes the compiled statement from memory--- 
 sqlite3_finalize(statement);
 
 return nil;
 
 }
 
 }
 
 - (void) deleteMenuWithId : (NSString *) menuId {
 
 sqlite3_stmt *statement;
 const char *sql = "DELETE FROM Menu WHERE menuID = ?";
 
 if(sqlite3_prepare_v2(db, sql, -1, &statement, NULL) != SQLITE_OK) {
 NSAssert1(0, @"Error while creating delete statement. '%s'", sqlite3_errmsg(db));
 } else {
 
 // binding data Note : start from 1
 sqlite3_bind_int(statement, 1, [menuId intValue]);
 
 if(SQLITE_DONE != sqlite3_step(statement)) {
 NSAssert1(0, @"Error while deleting data. '%s'", sqlite3_errmsg(db));
 }
 
 }
 
 //Reset the add statement.
 sqlite3_reset(statement);
 
 }
 
 */

//------------------------------------------------------------------------------------
// INSERT FUNCTION OF DATABASE HERE
//------------------------------------------------------------------------------------
- (NSMutableArray *) getKeyValueWithIdColumnName : (NSString *) idColumnName 
								 valueColumnName : (NSString *) valueColumnName
									   fromTable : (NSString *) tableName {
	
	NSMutableArray *result = [NSMutableArray arrayWithCapacity: 0];
	[result retain];
	
	//---retrieve rows--- 
	NSString *qsql = [[NSString alloc] initWithFormat:@"SELECT %@, %@ FROM %@ ORDER BY %@ ASC", 
					  idColumnName, valueColumnName, tableName, valueColumnName]; 
	sqlite3_stmt *statement;
	
	if (sqlite3_prepare_v2( db, [qsql UTF8String], -1, &statement, nil) == SQLITE_OK) {
		
		while (sqlite3_step(statement) == SQLITE_ROW) {
			
			char *field1 = (char *) sqlite3_column_text(statement, 0); 
			NSString *field1Str = [[NSString alloc] initWithUTF8String: field1];
			
			char *field2 = (char *) sqlite3_column_text(statement, 1); 
			NSString *field2Str = [[NSString alloc] initWithUTF8String: field2];
			
			KeyValue *info = [[KeyValue alloc] initWithKey:field1Str value:field2Str];
			
			// Add to array :
			[result addObject: info];
			
			[field1Str release]; 
			[field2Str release];
			[info release];
			
		} 
		
		//---deletes the compiled statement from memory--- 
		sqlite3_finalize(statement);
	}
	
	return result;
	
}

- (NSMutableArray *) getKeyValueNotOrderWithIdColumnName : (NSString *) idColumnName 
										 valueColumnName : (NSString *) valueColumnName
											   fromTable : (NSString *) tableName {
	
	NSMutableArray *result = [NSMutableArray arrayWithCapacity: 0];
	[result retain];
	
	//---retrieve rows--- 
	NSString *qsql = [[NSString alloc] initWithFormat:@"SELECT %@, %@ FROM %@", 
					  idColumnName, valueColumnName, tableName, valueColumnName]; 
	sqlite3_stmt *statement;
	
	if (sqlite3_prepare_v2( db, [qsql UTF8String], -1, &statement, nil) == SQLITE_OK) {
		
		while (sqlite3_step(statement) == SQLITE_ROW) {
			
			char *field1 = (char *) sqlite3_column_text(statement, 0); 
			NSString *field1Str = [[NSString alloc] initWithUTF8String: field1];
			
			char *field2 = (char *) sqlite3_column_text(statement, 1); 
			NSString *field2Str = [[NSString alloc] initWithUTF8String: field2];
			
			KeyValue *info = [[KeyValue alloc] initWithKey:field1Str value:field2Str];
			
			// Add to array :
			[result addObject: info];
			
			[field1Str release]; 
			[field2Str release];
			[info release];
			
		} 
		
		//---deletes the compiled statement from memory--- 
		sqlite3_finalize(statement);
	}
	
	
	return result;
	
}

- (void) setValue : (NSString*) strValue forSettingKey : (NSString*) strKey {
    
    sqlite3_stmt *updateStmt;
    const char *sql = "UPDATE Setting SET value = ? WHERE key = ?";
    
    if (sqlite3_prepare_v2(db, sql, -1, &updateStmt, NULL) != SQLITE_OK) {
        NSAssert1(0, @"Error while creating update statement. '%s'", sqlite3_errmsg(db));
    }
    
    // binding data Note : start from 1
    sqlite3_bind_text(updateStmt, 1, [strValue UTF8String], -1, SQLITE_TRANSIENT);
    sqlite3_bind_text(updateStmt, 2, [strKey UTF8String], -1, SQLITE_TRANSIENT);
    
    // execute update
    if(SQLITE_DONE != sqlite3_step(updateStmt))
        NSAssert1(0, @"Error while updating. '%s'", sqlite3_errmsg(db));
    
    sqlite3_reset(updateStmt);
    
}

- (NSString*) getSettingValueForKey : (NSString*) strKey {
    
    sqlite3_stmt *statement;
    NSString *strValue = nil;
    
    const char *sql = "SELECT value FROM Setting WHERE key = ?";
    
    if (sqlite3_prepare_v2(db, sql, -1, &statement, NULL) != SQLITE_OK) {
        NSAssert1(0, @"Error while creating update statement. '%s'", sqlite3_errmsg(db));
    }
    
    // binding data Note : start from 1
    sqlite3_bind_text(statement, 1, [strKey UTF8String], -1, SQLITE_TRANSIENT);
    
    // execute command
    if(sqlite3_step(statement) == SQLITE_ROW)
    {
        
        // menuID
        char *field1 = (char *) sqlite3_column_text(statement, 0); 
        strValue = [[NSString alloc] initWithUTF8String: field1];
        [strValue retain];
        
    }         
    
    //---deletes the compiled statement from memory--- 
    sqlite3_finalize(statement);
    
    return strValue;
    
}


- (NSArray*) getListMoreApp {
	
	NSMutableArray *result = [NSMutableArray arrayWithCapacity: 0];
	[result retain];
	
	//---retrieve rows--- 
	const char *sql = "SELECT id, appName, appLink, appIcon, appImage, introduction FROM MoreApp";
	sqlite3_stmt *statement;
	
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		while (sqlite3_step(statement) == SQLITE_ROW) {
			
			char *field1 = (char *) sqlite3_column_text(statement, 0); 
			NSString *idValue = [[NSString alloc] initWithUTF8String: field1];
			
			char *field2 = (char *) sqlite3_column_text(statement, 1); 
			NSString *appName = [[NSString alloc] initWithUTF8String: field2];
			
			char *field3 = (char *) sqlite3_column_text(statement, 2); 
			NSString *appLink = [[NSString alloc] initWithUTF8String: field3];
			
			char *field4 = (char *) sqlite3_column_text(statement, 3); 
			NSString *appIcon = [[NSString alloc] initWithUTF8String: field4];
			
			char *field5 = (char *) sqlite3_column_text(statement, 4); 
			NSString *appImage = [[NSString alloc] initWithUTF8String: field5];
			
			char *field6 = (char *) sqlite3_column_text(statement, 5); 
			NSString *appIntro = [[NSString alloc] initWithUTF8String: field6];
			
			MoreAppInfo *appInfo = [[MoreAppInfo alloc] init];
			appInfo.idValue = [idValue intValue];
			appInfo.appName = appName;
			appInfo.appLink = appLink;
			appInfo.appIcon = appIcon;
			appInfo.appImage = appImage;
			appInfo.appIntro = appIntro;
			
			// Add to array :
			[result addObject: appInfo];
			
			[idValue release]; 
			[appName release];
			[appLink release];
			[appIcon release];
			[appImage release];
			[appIntro release];
			
			[appInfo release];
			
		} 
		
		//---deletes the compiled statement from memory--- 
		sqlite3_finalize(statement);
	}
	
	return result;
	
}



//==================================== Create Tables in DB. ============================================================
-(void) createTableInfoOfButtonOnBoard:(NSString *) tableName withField1:(NSString *)tag withField2:(NSString *) enaled {
	char *err;
	NSString *sql = [NSString stringWithFormat:@"CREATE TABLE IF NOT EXISTS '%@' ('%@' TEXT , '%@' TEXT);", tableName, tag, enaled];
	
	if (sqlite3_exec(db, [sql UTF8String], NULL, NULL, &err) != SQLITE_OK)
	{
		sqlite3_close(db);
		NSAssert(0, @"Tabled failed to create.");
	}
}


//================================	Count row in table. ================================
- (int) CountRow:(NSString *)tableName
{
    int count = 0;
 //  if ([self openDB])
 //   {
		NSString * sqlSting = [NSString stringWithFormat:@"SELECT COUNT(*) FROM '%@' ",tableName];
		const char *sql = [sqlSting UTF8String];
		sqlite3_stmt *statement;
		
        if( sqlite3_prepare_v2(db, sql, -1, &statement, NULL) == SQLITE_OK )
        {
            //Loop through all the returned rows (should be just one)
            while( sqlite3_step(statement) == SQLITE_ROW )
            {
                count = sqlite3_column_int(statement, 0);
            }
        }
        else
        {
            NSLog( @"Failed from sqlite3_prepare_v2. Error is:  %s", sqlite3_errmsg(db) );
        }
		
//	}
	
    return count;
}

- (int) getGameMode	{
	
    int gameMode = -1;
	
	const char *sql = "SELECT cheDoChoi FROM InfoOther";
	sqlite3_stmt *statement;
	
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		while (sqlite3_step(statement) == SQLITE_ROW)
		{
			gameMode = sqlite3_column_int(statement, 0);
		}
		sqlite3_finalize(statement);
	}
		
    return gameMode;
}

- (void) deleteRowOfTable:(NSString *)tableName{
	
	NSString * sqlSting = [NSString stringWithFormat:@"DELETE FROM %@",tableName];
	const char *sql = [sqlSting UTF8String];
	sqlite3_exec(db,sql, NULL, NULL, NULL);
//	sqlite3_close(db);	

}										

//======================================================================================
- (void) insertSummaryInfo : (InfoOther*) input {
		
	NSMutableString *sql = [NSMutableString stringWithCapacity:0];
	[sql appendString:@"INSERT OR REPLACE INTO InfoOther "];
	[sql appendString:@"(Level, Score, Time, Life, Suggest, NumberStarEnabled, ValueNameImg, cheDoChoi, checkSum) "];
	[sql appendString:@"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "];
	
	sqlite3_stmt *statement;	
	
	if (sqlite3_prepare_v2(db, [sql UTF8String], -1, &statement, nil) == SQLITE_OK) {
		
		sqlite3_bind_int(statement, 1, input.level);
		sqlite3_bind_int(statement, 2, input.score);
		sqlite3_bind_int(statement, 3, input.time);
		sqlite3_bind_int(statement, 4, input.life);
		sqlite3_bind_int(statement, 5, input.suggest);
		sqlite3_bind_int(statement, 6, input.numberStarEnabled);
		sqlite3_bind_int(statement, 7, input.valueNameImg);
		sqlite3_bind_int(statement, 8, input.cheDoChoi);
		sqlite3_bind_text(statement, 9, [input.checkSum UTF8String], -1, NULL);
		
		if (sqlite3_step(statement) != SQLITE_DONE)
			NSAssert(0, @"Error updating table.");
		
	}
	
	sqlite3_finalize(statement);
		
}

- (InfoOther*) getSummaryInfoForMode : (NSInteger) gameMode {
	
	InfoOther *result = [[InfoOther alloc] init];
	
	const char *sql = "SELECT Level, Score, Time,Life, Suggest, NumberStarEnabled, ValueNameImg, cheDoChoi, checkSum FROM InfoOther";
	
	sqlite3_stmt *statement;
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		// TODO : binding game mode value
		
		// get info
		while (sqlite3_step(statement) == SQLITE_ROW) {
			
			result.level = sqlite3_column_int(statement, 0);
			result.score = sqlite3_column_int(statement, 1);
			result.time = sqlite3_column_int(statement, 2);
			result.life = sqlite3_column_int(statement, 3);
			result.suggest = sqlite3_column_int(statement, 4);
			result.numberStarEnabled = sqlite3_column_int(statement, 5);
			result.valueNameImg = sqlite3_column_int(statement, 6);
			result.cheDoChoi = sqlite3_column_int(statement, 7);
			
			char *field8 = (char *) sqlite3_column_text(statement, 8); 
			result.checkSum = [[NSString alloc] initWithUTF8String: field8];
			 		
		}
		
		sqlite3_finalize(statement);
	}
	
	
	
	return result;
	
}

- (void) insertBoardInfo : (NSMutableArray*) listInfoButtonOfBoard {

	
	NSMutableString *sql = [NSMutableString stringWithCapacity:0];
	[sql appendString:@"INSERT OR REPLACE INTO BoardInfo "];
	[sql appendString:@"(ValueTagOfBtn, IsEnabled) "];
	[sql appendString:@"VALUES (?, ?) "];
	
	sqlite3_stmt *statement;
	
	for (int i = 0; i < [listInfoButtonOfBoard count]; i++) {
		
		Point1 *bi = [listInfoButtonOfBoard objectAtIndex:i];
		
		if (sqlite3_prepare_v2(db, [sql UTF8String], -1, &statement, nil) == SQLITE_OK) {
			sqlite3_bind_int(statement, 1, bi.type);
			sqlite3_bind_int(statement, 2, 1);
			//sqlite3_bind_int(statement, 2, button.enabled ? 1 : 0);
		}
		
		if (sqlite3_step(statement) != SQLITE_DONE)
			NSAssert(0, @"Error updating table.");
		
		sqlite3_finalize(statement);
		
	}
	
	
}


//tvuong1
- (NSMutableArray *) getListStart:(int)level{

	NSMutableArray * alist = [[NSMutableArray alloc] initWithCapacity:0];
	//[alist retain];
	
	
	const char *sql = "SELECT * FROM _level1 WHERE level = ? and status = 0";
	
	sqlite3_stmt *statement;
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		sqlite3_bind_int(statement, 1, level);
		if(sqlite3_step(statement) == SQLITE_ROW) {
			
			for(int i = 0; i < 21; i ++){
				Point1 *temp = [[Point1 alloc] init];
				temp.type = sqlite3_column_int(statement, (i + 2));
				
				[alist addObject:temp];
			}
		}
		
		sqlite3_finalize(statement);
	}
		
	return alist;

}

- (NSMutableArray *) getListEnd:(int)level{
	
	NSMutableArray * alist = [[NSMutableArray alloc] initWithCapacity:0];
	//[alist retain];
	
	
	const char *sql = "SELECT * FROM _level1 WHERE level = ? and status = 1";
	
	sqlite3_stmt *statement;
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		sqlite3_bind_int(statement, 1, level);
		
		if(sqlite3_step(statement) == SQLITE_ROW) {
			
			for(int i = 0; i < 19; i ++){
				Point1 *temp = [[Point1 alloc] init];
				temp.type = sqlite3_column_int(statement, (i + 2));
				
				[alist addObject:temp];
			}
		}
		
		sqlite3_finalize(statement);
	}
	
	return alist;
	
}

- (int) getMaxlevel{
	int maxLevel = 0;
	const char *sql = "SELECT max(level) FROM _level1";
	
	sqlite3_stmt *statement;
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		if(sqlite3_step(statement) == SQLITE_ROW) {
			maxLevel =sqlite3_column_int(statement, 0); 
		}
		
		sqlite3_finalize(statement);
	}
	
	return maxLevel;
}


- (int) getMaxlevelStore{
	int maxLevel = 0;
	const char *sql = "SELECT max(level) FROM _store";
	
	sqlite3_stmt *statement;
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		if(sqlite3_step(statement) == SQLITE_ROW) {
			maxLevel =sqlite3_column_int(statement, 0); 
		}
		
		sqlite3_finalize(statement);
	}
	
	return maxLevel;
}


- (void) updateStore :(int)level status:(int)status score: (int)score{
	
	
	//const char *sql = "SELECT max(level) FROM _store";
	//finish level
	//status == 2 Win normal
	//status = 3 Win all
	//status == 1 store
	if(status == 2){
		[self updateStore1:2 level:level score:score];
		if(![self isExistStore:(level + 1)]){
			[self insertStore:(level + 1)];
		}
		
	}else if(status == 1){
		
	}else if(status == 0){
	}else if(status == 3){
		[self updateStore1:2 level:level score:score];
	}
}

- (void) updateStore1 :(int)status level: (int) level score:(int)score {
	sqlite3_stmt *updateStmt;
	
	const char *sql1 = "UPDATE _store SET status = ? , score = ? WHERE level = ?";
	
	if (sqlite3_prepare_v2(db, sql1, -1, &updateStmt, NULL) != SQLITE_OK) {
		NSAssert1(0, @"Error while creating update statement. '%s'", sqlite3_errmsg(db));
	}
	
	sqlite3_bind_int(updateStmt, 1, status);
	sqlite3_bind_int(updateStmt, 2, score);
	sqlite3_bind_int(updateStmt, 3, level);
	// execute update
	if(SQLITE_DONE != sqlite3_step(updateStmt))
		NSAssert1(0, @"Error while updating. '%s'", sqlite3_errmsg(db));
	
	sqlite3_reset(updateStmt);
	
}


- (void) insertStore : (int) level {
	
	NSMutableString *sql = [NSMutableString stringWithCapacity:0];
	[sql appendString:@"INSERT INTO _store "];
	[sql appendString:@"VALUES(?,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0) "];
	
	sqlite3_stmt *statement;	
	
	if (sqlite3_prepare_v2(db, [sql UTF8String], -1, &statement, nil) == SQLITE_OK) {
		
		sqlite3_bind_int(statement, 1, level);
		
		if (sqlite3_step(statement) != SQLITE_DONE){
			NSAssert(0, @"Error updating table.");
		}
		
	}
	
	sqlite3_finalize(statement);
	
}

- (BOOL) isExistStore : (int) level {
	
	BOOL isExisted = FALSE;
	
	sqlite3_stmt *statement;
	
	const char *sql = "SELECT * FROM _store WHERE level = ? ";
	
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		// binding data Note : start from 1
		sqlite3_bind_int(statement, 1, level);
		if (sqlite3_step(statement) == SQLITE_ROW) {
			
			isExisted = TRUE;
			
		}
		
	}
	
	//---deletes the compiled statement from memory--- 
	sqlite3_finalize(statement);
	
	return isExisted;
	
	
}

- (Score*) getSocre{
	Score *score = [[Score alloc]init];
	
	[score create];
	const char *sql = "SELECT * FROM _store where level >= 1";
	sqlite3_stmt *statement;
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		while (sqlite3_step(statement) == SQLITE_ROW) {
			
			int level =  sqlite3_column_int(statement, 0); 
			int _score1 =  sqlite3_column_int(statement, 2); 
			
			Point1 * temp = [[Point1 alloc]init];
			temp.x = level;
			temp.y = _score1;
			//NSLog(@"ddd %d",level);
			[score add:temp];
			
		} 
		
		//---deletes the compiled statement from memory--- 
		sqlite3_finalize(statement);
	}
	
	return score;
}

//tvuong1 end


- (BOOL) isExistPlayerId : (NSString*) playerId andGameMode : (int) gameMode {
	
	BOOL isExisted = FALSE;
	
	sqlite3_stmt *statement;
	
	const char *sql = "SELECT * FROM userData WHERE playerId = ? AND gameMode = ? ";
	
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		// binding data Note : start from 1
		sqlite3_bind_text(statement, 1, [[playerId AES256EncryptWithKey:AES_KEY] UTF8String], -1, SQLITE_TRANSIENT);
		sqlite3_bind_int(statement, 2, gameMode);
		
		if (sqlite3_step(statement) == SQLITE_ROW) {
			
			isExisted = TRUE;
			
		}
		
	}
	
	//---deletes the compiled statement from memory--- 
	sqlite3_finalize(statement);
	
	return isExisted;
	
	
}

- (void) insertScoreInfo : (ScoreInfo*) score {
	
	sqlite3_stmt *statement;
	const char *sql = "INSERT INTO userData(playerId, highestScore, checkSum, gameMode) VALUES( ? , ?, ?, ?)";
	
	if(sqlite3_prepare_v2(db, sql, -1, &statement, NULL) != SQLITE_OK) {
		
		NSAssert1(0, @"Error while creating add statement. '%s'", sqlite3_errmsg(db));
		
	} else {
		
		// binding data Note : start from 1
		sqlite3_bind_text(statement, 1, [score.playerId UTF8String], -1, SQLITE_TRANSIENT);
		sqlite3_bind_text(statement, 2, [score.strHighestScore UTF8String], -1, SQLITE_TRANSIENT);
		sqlite3_bind_text(statement, 3, [score.checkSum UTF8String], -1, SQLITE_TRANSIENT);
		sqlite3_bind_int(statement, 4, score.gameMode);
		
		if(SQLITE_DONE != sqlite3_step(statement)) {
			NSAssert1(0, @"Error while inserting data. '%s'", sqlite3_errmsg(db));
		}
		
	}
	
	//Reset the add statement.
	sqlite3_reset(statement);
	
}

- (void) updateScoreInfo : (ScoreInfo*) score {
	
	sqlite3_stmt *updateStmt;
	
	const char *sql = "UPDATE userData SET highestScore = ? AND checkSum = ? WHERE playerId = ? AND gameMode = ? ";
	
	if (sqlite3_prepare_v2(db, sql, -1, &updateStmt, NULL) != SQLITE_OK) {
		NSAssert1(0, @"Error while creating update statement. '%s'", sqlite3_errmsg(db));
	}
	
	sqlite3_bind_text(updateStmt, 1, [score.strHighestScore UTF8String], -1, SQLITE_TRANSIENT);
	sqlite3_bind_text(updateStmt, 2, [score.checkSum UTF8String], -1, SQLITE_TRANSIENT);
	sqlite3_bind_text(updateStmt, 3, [score.playerId UTF8String], -1, SQLITE_TRANSIENT);
	sqlite3_bind_int(updateStmt,  4, score.gameMode);
	
	// execute update
	if(SQLITE_DONE != sqlite3_step(updateStmt))
		NSAssert1(0, @"Error while updating. '%s'", sqlite3_errmsg(db));
	
	sqlite3_reset(updateStmt);
	
}

- (void) deleteSocreInfoForId : (int) scoreId {
	
	sqlite3_stmt *statement;
	const char *sql = "DELETE FROM userData WHERE id = ?";
	
	if(sqlite3_prepare_v2(db, sql, -1, &statement, NULL) != SQLITE_OK) {
		NSAssert1(0, @"Error while creating delete statement. '%s'", sqlite3_errmsg(db));
	} else {
		
		// binding data Note : start from 1
		sqlite3_bind_int(statement, 1, scoreId);
		
		if(SQLITE_DONE != sqlite3_step(statement)) {
			NSAssert1(0, @"Error while deleting data. '%s'", sqlite3_errmsg(db));
		}
		
	}
	
	//Reset the add statement.
	sqlite3_reset(statement);
	
}

- (void) deleteSocreInfoForPlayerId : (NSString*) playerId andGameMode : (int) gameMode {
	
	sqlite3_stmt *statement;
	const char *sql = "DELETE FROM userData WHERE playerId = ? AND gameMode = ?";
	
	if(sqlite3_prepare_v2(db, sql, -1, &statement, NULL) != SQLITE_OK) {
		NSAssert1(0, @"Error while creating delete statement. '%s'", sqlite3_errmsg(db));
	} else {
		
		// binding data Note : start from 1
		sqlite3_bind_text(statement, 1, [[playerId AES256EncryptWithKey:AES_KEY] UTF8String], -1, SQLITE_TRANSIENT);
		sqlite3_bind_int(statement, 2, gameMode);
		
		if(SQLITE_DONE != sqlite3_step(statement)) {
			NSAssert1(0, @"Error while deleting data. '%s'", sqlite3_errmsg(db));
		}
		
	}
	
	//Reset the add statement.
	sqlite3_reset(statement);
	
}

- (ScoreInfo*) getScoreInfoForPlayerId : (NSString*) playerId andGameMode : (int) gameMode {
	
	ScoreInfo *result = nil;
	
	//---retrieve rows--- 
	const char *sql = "SELECT id, playerId, highestScore, checkSum FROM userData WHERE playerId = ? AND gameMode = ? ";
	sqlite3_stmt *statement;
	
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		sqlite3_bind_text(statement, 1, [[playerId AES256EncryptWithKey:AES_KEY] UTF8String], -1, SQLITE_TRANSIENT);
		sqlite3_bind_int(statement, 2, gameMode);
		
		if (sqlite3_step(statement) == SQLITE_ROW) {
			
			char *field1 = (char *) sqlite3_column_text(statement, 0); 
			NSString *idValue = [[NSString alloc] initWithUTF8String: field1];
			
			char *field2 = (char *) sqlite3_column_text(statement, 1); 
			NSString *strPlayerId = [[NSString alloc] initWithUTF8String: field2];
			
			char *field3 = (char *) sqlite3_column_text(statement, 2); 
			NSString *strHighest = [[NSString alloc] initWithUTF8String: field3];
			
			char *field4 = (char *) sqlite3_column_text(statement, 3); 
			NSString *strCheckSum = [[NSString alloc] initWithUTF8String: field4];
			
			int gameMode = sqlite3_column_int(statement, 4);
			
			result = [[ScoreInfo alloc] initWithScoreId:[idValue intValue] 
											   playerId:strPlayerId 
										   highestScore:strHighest  
											   gameMode:gameMode
											andCheckSum:strCheckSum];
			
			[idValue release]; 
			[strPlayerId release];
			[strHighest release];
			[strCheckSum release];
			
		} 
		
		//---deletes the compiled statement from memory--- 
		sqlite3_finalize(statement);
	}
	
	return result;
	
}

- (NSArray*) getlistScoreForPlayerId : (NSString*) playerId {
	
	NSMutableArray *listResult = [[NSMutableArray arrayWithCapacity:0] retain];
	
	//---retrieve rows--- 
	const char *sql = "SELECT id, playerId, highestScore, checkSum FROM userData WHERE playerId = ?";
	sqlite3_stmt *statement;
	
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		sqlite3_bind_text(statement, 1, [[playerId AES256EncryptWithKey:AES_KEY] UTF8String], -1, SQLITE_TRANSIENT);
		
		while (sqlite3_step(statement) == SQLITE_ROW) {
			
			char *field1 = (char *) sqlite3_column_text(statement, 0); 
			NSString *idValue = [[NSString alloc] initWithUTF8String: field1];
			
			char *field2 = (char *) sqlite3_column_text(statement, 1); 
			NSString *strPlayerId = [[NSString alloc] initWithUTF8String: field2];
			
			char *field3 = (char *) sqlite3_column_text(statement, 2); 
			NSString *strHighest = [[NSString alloc] initWithUTF8String: field3];
			
			char *field4 = (char *) sqlite3_column_text(statement, 3); 
			NSString *strCheckSum = [[NSString alloc] initWithUTF8String: field4];
			
			int gameMode = sqlite3_column_int(statement, 4);
			
			ScoreInfo *result = [[ScoreInfo alloc] initWithScoreId:[idValue intValue] 
														  playerId:strPlayerId 
													  highestScore:strHighest  
														  gameMode:gameMode
													   andCheckSum:strCheckSum];
			
			
			[listResult addObject:result];
			
			[idValue release]; 
			[strPlayerId release];
			[strHighest release];
			[strCheckSum release];
			
		} 
		
		//---deletes the compiled statement from memory--- 
		sqlite3_finalize(statement);
	}
	
	return listResult;
	
	
}



@end
