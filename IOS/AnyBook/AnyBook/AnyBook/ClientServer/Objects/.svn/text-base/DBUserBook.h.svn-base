//
//  DBUserBook.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/11/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DBFriendlyObject.h"
#import "DBTable.h"
#import <sqlite3.h>

@interface DBUserBook : DBFriendlyObject {
    
}

+ (NSString*) getDBTableName;
+ (DBTable*) declareDBTable;
+ (DBUserBook*) createDBUserBookFromDatabase:(sqlite3_stmt*) compiled;

+ (NSString*) getSqlSelectUserBookShelf:(NSString*)msisdn;
+ (NSString*) getSqlSelectUserMarkRemovedBook:(NSString*)msisdn;
+ (NSString*) getSqlSelectUserBookByBookId:(NSString*) msisdn wBookId:(int) bookId;
+ (NSString*) getSqlSelectAllUserBook:(NSString*)msisdn;

+ (NSString*) getSqlInsertUserBook:(DBUserBook*) userbook;
+ (NSString*) getSqlUpdateUserBook:(DBUserBook*) userbook;
+ (NSString*) getSqlDeteteUserBook:(DBUserBook*) userbook;

+ (NSString*) getSqlCountUserBookForBookId:(int)bookId;

#pragma mark - Member access

- (NSString*) getUserMsisdn;
- (void) setUserMsisdn:(NSString*) msisdn;

- (int) getBookId;
- (NSString*) getBookIdAsString;
- (void) setBookId:(int) bookId;

- (BOOL) isMarkRemoved;
- (void) markRemoved:(BOOL) removed;

- (NSString*) getCachedPath;
- (void) setCachedPath:(NSString*) path;

- (NSString*) getRightObject;
- (void) setRightObject:(NSString*) object;

- (NSString*) getServerKey;
- (void) setServerKey:(NSString*) key;

@end
