//
//  DBManager.m
//  BlankProject
//
//   Created by truongvv on 10/14/11.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "DBManager.h"
#import "sqlite3.h"


@implementation DBManager

// Open Database 
- (BOOL) openDB { 
	//---create database--- 
	
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES); 
	NSString *documentsDir = [paths objectAtIndex:0];
	NSString *data = [documentsDir stringByAppendingPathComponent: SQL_FILE_NAME];
	
	if (sqlite3_open([data UTF8String], &db) != SQLITE_OK ) {
		sqlite3_close(db); 
		//NSAssert(0, @"Database failed to open.");
		NSLog(@"Open database is failed.");
		return false;
	}else {
		NSLog(@"Open database.");
		return true;
	}
}

// Creates a writable copy of the bundled default database in the application Documents directory.
+ (void) initialDataBase {
    // First, test for existence.
    BOOL success;
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *error;
	
	
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES); 
	NSString *documentsDir = [paths objectAtIndex:0];
	NSString *writableDBPath = [documentsDir stringByAppendingPathComponent: SQL_FILE_NAME];
	
	
	
    //NSString *writableDBPath = [Common dataBaseFilePath];
    success = [fileManager fileExistsAtPath:writableDBPath];
    if (success) return;
    // The writable database does not exist, so copy the default to the appropriate location.
    NSString *defaultDBPath = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:SQL_FILE_NAME];
    success = [fileManager copyItemAtPath:defaultDBPath toPath:writableDBPath error:&error];
    if (!success) {
        NSAssert1(0, @"Failed to create writable database file with message '%@'.", [error localizedDescription]);
    }else{
		NSLog(@"");
	}
}


//------------------------------------------------------------------------------------
// SAMPLE ABOUT INSERT, DELETE, UPDATE database
//------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------
// SAMPLE ABOUT INSERT
//------------------------------------------------------------------------------------

/*
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
*/
//------------------------------------------------------------------------------------
// SAMPLE ABOUT UPDATE
//------------------------------------------------------------------------------------

/*
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
 
 
 */
//------------------------------------------------------------------------------------
// SAMPLE ABOUT DELETE
//------------------------------------------------------------------------------------

/*
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
 
 */
//------------------------------------------------------------------------------------
// SAMPLE ABOUT SELECT
//------------------------------------------------------------------------------------

/*
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
 
 */


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


- (int) getMaxlevel{
	int maxLevel = 0;
	const char *sql = "SELECT max(level) FROM _level1";
	
	sqlite3_stmt *statement;
	if (sqlite3_prepare_v2( db, sql, -1, &statement, nil) == SQLITE_OK) {
		
		if(sqlite3_step(statement) == SQLITE_ROW) {
			maxLevel =sqlite3_column_int(statement, 0); 
		}
		
		sqlite3_finalize(statement);
	}else{
		NSLog(@"SELECT IS FAILED!");
	}
	
	return maxLevel;
}



@end
