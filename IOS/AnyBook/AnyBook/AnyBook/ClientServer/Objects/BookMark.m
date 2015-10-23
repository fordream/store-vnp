//
//  Book.m
//  AnyBook
//
//  Created by Vuong Van Truong on 4/10/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "BookMark.h"
@implementation BookMark



static DBTable* book_declare = Nil;

- (id) init
{
    self = [super init];
    if (self != nil) {
        DBTable* table = [BookMark declareDBTable];
        [table initObjectData:data];
    }
    
    return self;
}

-(void)dealloc 
{
}

+ (NSString*) getDBTableName
{
    return TABLE_NAME_BOOK;
}

+ (DBTable*) declareDBTable
{    
//"CREATE TABLE book(book_id text primary key, title text, type text, isbn text, description text, author text, publisher text, publish_time text, translator text, language text, market_price text, our_price text, mark_removed integer)"
    
    if (book_declare == Nil) {
        book_declare = [[DBTable alloc] initWithTable:TABLE_NAME_BOOK];
        
        [book_declare addField:FIELD_BOOK_MARK_ID :@"integer" :@"none"];
        [book_declare setPrimaryFieldIndex:0]; //Field book id is primary key
        
        [book_declare addField:FIELD_BOOK_ID :@"integer" :@"none"];
        
        [book_declare addField:FIELD_BOOK_PAGE :@"integer" :@"none"];
        
        [book_declare addField:FIELD_BOOK_CHAPTER :@"integer" :@"none"];
        
        [book_declare addField:FIELD_BOOK_NUMBER_PHONE_USER :@"text"];
    }
    
    return book_declare;
}

+ (BookMark*) createBookFromDatabase:(sqlite3_stmt*) compiled
{
    BookMark* res = [[BookMark alloc] init];
    DBTable* table = [BookMark declareDBTable];
    [table parse:compiled wObject:res];
    
    return res;
}

+ (NSString*) getSqlSelectAllBook
{
    return [[BookMark declareDBTable] getSqlSelectAllRow];
}

+ (NSString*) getSqlSelectBookById:(int) bookMarkId
{
    return [[BookMark declareDBTable] getSqlSelectRowWithCondition:[NSString stringWithFormat:@"%@ = '%@'", FIELD_BOOK_MARK_ID, bookMarkId]];
}

+ (NSString*) getSqlUpdateBook:(BookMark*) book
{
    DBTable* table = [BookMark declareDBTable];
    return [table getSqlUpdateRow:book wCondition:[NSString stringWithFormat:@"%@ = '%@'", FIELD_BOOK_MARK_ID, [book getBookMarkId]]];
}

+ (NSString*) getSqlInsertBook:(BookMark*) book
{
    DBTable* table = [BookMark declareDBTable];
    return [table getSqlInsertRow:book];
}

+ (NSString*) getSqlDeteteBook:(int) bookMarkId
{
    return [NSString stringWithFormat:@"DELETE FROM %@ WHERE (%@ = %d)",
            TABLE_NAME_BOOK,
            FIELD_BOOK_MARK_ID,
            bookMarkId];
}

//----------------------------------------------------------------------------
//
//                         BOOK MARK MEMBERS ACCESS
//
//----------------------------------------------------------------------------
- (int) getBookMarkId{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_MARK_ID];
    return [self getFieldAsNumber:book_id_idx];
}

- (void) setBookMarkId:(int) bookMarkId{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_MARK_ID];
    [self setField:book_id_idx wInt:bookMarkId];
}

- (int) getBookMarkPage{
    int book_id_idx  = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_PAGE];
    return [self getFieldAsNumber:book_id_idx];
}

- (void) setBookMarkPage:(int) bookMarkPage{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_PAGE];
    [self setField:book_id_idx wInt:bookMarkPage];
}

- (int) getBookMarkChapter{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_CHAPTER];
    return [self getFieldAsNumber:book_id_idx];
}

- (void) setBookMarkChapter:(int) bookMarkChapter{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_CHAPTER];
    [self setField:book_id_idx wInt:bookMarkChapter];
}


- (int) getBookMarkPhone{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_NUMBER_PHONE_USER];
    return [self getFieldAsNumber:book_id_idx];
}
- (void) setBookMarkPhone:(NSString*) bookMarkPhone{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_NUMBER_PHONE_USER];
    [self setField:book_id_idx wString:bookMarkPhone];
}


- (int) getBookId
{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_ID];
    return [self getFieldAsNumber:book_id_idx];
}

- (NSString*) getBookIdAsString
{
    return [NSString stringWithFormat:@"%d", [self getBookMarkId]];
}

- (void) setBookId:(int) bookId
{
    int book_id_idx = [[BookMark declareDBTable] searchForFieldIndex:FIELD_BOOK_ID];
    [self setField:book_id_idx wInt:bookId];
}

@end