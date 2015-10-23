//
//  DBUserBook.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/11/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "DBUserBook.h"

#define FIELD_BOOK_ID       @"book_id"
#define FIELD_USER_MSISDN   @"user_msisdn"
#define FIELD_MARK_REMOVED  @"mark_removed"
#define FIELD_USER_CACHED   @"cached_path"
#define FIELD_RIGHT_OBJECT  @"right_object"
#define FIELD_SERVER_KEY    @"server_key"

@implementation DBUserBook

#define TABLE_NAME_DBUSERBOOK @"user_book"

static DBTable* dbuserbook_declare = Nil;

- (id) init
{
    self = [super init];
    if (self != nil) {
        [[DBUserBook declareDBTable] initObjectData:data];
    }
    return self;
}

-(void)dealloc 
{
}

+ (NSString*) getDBTableName
{
    return TABLE_NAME_DBUSERBOOK;
}

+ (DBTable*) declareDBTable
{
    if (dbuserbook_declare == Nil) {
        dbuserbook_declare = [[DBTable alloc] initWithTable:TABLE_NAME_DBUSERBOOK];
        [dbuserbook_declare addField:FIELD_BOOK_ID :@"integer"];
        [dbuserbook_declare addField:FIELD_USER_MSISDN :@"text"];
        [dbuserbook_declare addField:FIELD_MARK_REMOVED :@"integer"];
        [dbuserbook_declare addField:FIELD_USER_CACHED :@"text"];
        [dbuserbook_declare addField:FIELD_RIGHT_OBJECT :@"text"];
        [dbuserbook_declare addField:FIELD_SERVER_KEY :@"text"];
    }
    return dbuserbook_declare;
}

+ (DBUserBook*) createDBUserBookFromDatabase:(sqlite3_stmt*) compiled
{
    DBUserBook* res = [[DBUserBook alloc] init];
    DBTable* table = [DBUserBook declareDBTable];
    [table parse:compiled wObject:res];
    
    return res;
}

+ (NSString*) getSqlSelectUserBookShelf:(NSString*)msisdn
{
    return [[DBUserBook declareDBTable] getSqlSelectRowWithCondition:
            [NSString stringWithFormat:@"(%@ = '%@') AND (%@ = %d)", 
             FIELD_USER_MSISDN,
             msisdn, 
             FIELD_MARK_REMOVED,
             SQL_BOOL_FALSE]];
}

+ (NSString*) getSqlSelectUserMarkRemovedBook:(NSString*)msisdn
{
    return [[DBUserBook declareDBTable] getSqlSelectRowWithCondition:
            [NSString stringWithFormat:@"(%@ = '%@') AND (%@ = %d)", 
             FIELD_USER_MSISDN,
             msisdn, 
             FIELD_MARK_REMOVED, SQL_BOOL_TRUE]];
}

+ (NSString*) getSqlSelectUserBookByBookId:(NSString*) msisdn wBookId:(int) bookId
{
    return [[DBUserBook declareDBTable] getSqlSelectRowWithCondition:
            [NSString stringWithFormat:@"(%@ = '%@') AND (%@ = %d)",
             FIELD_USER_MSISDN,
             msisdn,
             FIELD_BOOK_ID,
             bookId]];
}

+ (NSString*) getSqlInsertUserBook:(DBUserBook*) userbook
{
    DBTable* table = [DBUserBook declareDBTable];
    return [table getSqlInsertRow:userbook];
}

+ (NSString*) getSqlUpdateUserBook:(DBUserBook*) userbook
{
    return [[DBUserBook declareDBTable] getSqlUpdateRow:userbook wCondition:
            [NSString stringWithFormat:@"(%@ = '%@') AND (%@ = %d)",
             FIELD_USER_MSISDN,
             [userbook getUserMsisdn],
             FIELD_BOOK_ID,
             [userbook getBookId]]];
}

+ (NSString*) getSqlDeteteUserBook:(DBUserBook*) userbook
{
    return [NSString stringWithFormat:@"DELETE FROM %@ WHERE (%@ = '%@') AND (%@ = %d)",
            TABLE_NAME_DBUSERBOOK,
            FIELD_USER_MSISDN,
            [userbook getUserMsisdn],
            FIELD_BOOK_ID,
            [userbook getBookId]];
}

+ (NSString*) getSqlSelectAllUserBook:(NSString*)msisdn
{
    return [[DBUserBook declareDBTable] getSqlSelectRowWithCondition:
            [NSString stringWithFormat:@"(%@ = '%@')",
             FIELD_USER_MSISDN,
             msisdn]];
}

+ (NSString*) getSqlCountUserBookForBookId:(int)bookId
{
    return [NSString stringWithFormat:@"SELECT count(*) FROM %@ WHERE %@ = %d",
            TABLE_NAME_DBUSERBOOK,
            FIELD_BOOK_ID,
            bookId];
}

#pragma mark - Member access

static int user_msisdn_idx = -1;

- (NSString*) getUserMsisdn
{
    if (user_msisdn_idx < 0) {
        user_msisdn_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_USER_MSISDN];
    }
    return [self getFieldAsString:user_msisdn_idx];

}

- (void) setUserMsisdn:(NSString*) msisdn
{
    if (user_msisdn_idx < 0) {
        user_msisdn_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_USER_MSISDN];
    }
    [self setField:user_msisdn_idx wString:msisdn];
}

static int book_id_idx = -1;

- (int) getBookId
{
    if (book_id_idx < 0) {
        book_id_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_BOOK_ID];
    }
    return [self getFieldAsNumber:book_id_idx];
}

- (NSString*) getBookIdAsString
{
    return [NSString stringWithFormat:@"%d", [self getBookId]];
}

- (void) setBookId:(int) bookId
{
    if (book_id_idx < 0) {
        book_id_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_BOOK_ID];
    }
    [self setField:book_id_idx wInt:bookId];
}


static int mark_removed_idx = -1;

- (BOOL) isMarkRemoved
{
    if (mark_removed_idx < 0) {
        mark_removed_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_MARK_REMOVED];
    }
    return [self getFieldAsBoolean:mark_removed_idx];
}

- (void) markRemoved:(BOOL) removed
{
    if (mark_removed_idx < 0) {
        mark_removed_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_MARK_REMOVED];
    }
    return [self setField:mark_removed_idx wBoolean:removed];
}

static int cached_path_idx = -1;

- (NSString*) getCachedPath
{
    if (cached_path_idx < 0) {
        cached_path_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_USER_CACHED];
    }
    return [self getFieldAsString:cached_path_idx];
}

- (void) setCachedPath:(NSString*) path
{
    if (cached_path_idx < 0) {
        cached_path_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_USER_CACHED];
    }
    [self setField:cached_path_idx wString:path];
}

static int right_object_idx = -1;

- (NSString*) getRightObject
{
    if (right_object_idx < 0) {
        right_object_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_RIGHT_OBJECT];
    }
    return [self getFieldAsString:right_object_idx];
}

- (void) setRightObject:(NSString*) object
{
    if (right_object_idx < 0) {
        right_object_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_RIGHT_OBJECT];
    }
    [self setField:right_object_idx wString:object];
}

static int server_key_idx = -1;

- (NSString*) getServerKey
{
    if (server_key_idx < 0) {
        server_key_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_SERVER_KEY];
    }
    return [self getFieldAsString:server_key_idx];
}

- (void) setServerKey:(NSString*) key
{
    if (server_key_idx < 0) {
        server_key_idx = [[DBUserBook declareDBTable] searchForFieldIndex:FIELD_SERVER_KEY];
    }
    [self setField:server_key_idx wString:key];
}


@end
