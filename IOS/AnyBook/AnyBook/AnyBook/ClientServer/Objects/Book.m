//
//  Book.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/10/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "Book.h"

@implementation Book

#define TABLE_NAME_BOOK @"book"

#define FIELD_BOOK_ID       @"book_id"
#define FIELD_TITLE         @"title"
#define FIELD_TYPE          @"type"
#define FIELD_ISBN          @"isbn"
#define FIELD_DESCRIPTION   @"description"
#define FIELD_AUTHOR        @"author"
#define FIELD_PUBLISHER     @"publisher"
#define FIELD_PUBLISH_TIME  @"publish_time"
#define FIELD_TRANSLATOR    @"translator"
#define FIELD_LANGUAGE      @"language"
#define FIELD_MARKET_PRICE  @"market_price"
#define FIELD_OUR_PRICE     @"our_price"
#define FIELD_THUMBNAIL     @"thumbnail"

static DBTable* book_declare = Nil;

- (id) init
{
    self = [super init];
    if (self != nil) {
        DBTable* table = [Book declareDBTable];
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
        [book_declare addField:FIELD_BOOK_ID :@"integer" :@"none"];
        [book_declare setPrimaryFieldIndex:0]; //Field book id is primary key

        [book_declare addField:FIELD_TITLE :@"text"];
        [book_declare addField:FIELD_TYPE :@"text"];
        [book_declare addField:FIELD_ISBN :@"text"];
        [book_declare addField:FIELD_DESCRIPTION :@"text"];
        [book_declare addField:FIELD_AUTHOR :@"text"];
        [book_declare addField:FIELD_PUBLISHER :@"text"];
        [book_declare addField:FIELD_PUBLISH_TIME :@"text"];
        [book_declare addField:FIELD_TRANSLATOR :@"text"];
        [book_declare addField:FIELD_LANGUAGE :@"text"];
        [book_declare addField:FIELD_MARKET_PRICE :@"text" :@"float"];
        [book_declare addField:FIELD_OUR_PRICE :@"text" :@"float"];
        [book_declare addField:FIELD_THUMBNAIL :@"text"];
    }
    return book_declare;
}

+ (Book*) createBookFromDatabase:(sqlite3_stmt*) compiled
{
    Book* res = [[Book alloc] init];
    DBTable* table = [Book declareDBTable];
    [table parse:compiled wObject:res];
    
    return res;
}

+ (NSString*) getSqlSelectAllBook
{
    return [[Book declareDBTable] getSqlSelectAllRow];
}

+ (NSString*) getSqlSelectBookById:(int) bookId
{
    return [[Book declareDBTable] getSqlSelectRowWithCondition:[NSString stringWithFormat:@"%@ = '%@'", FIELD_BOOK_ID, bookId]];
}

+ (NSString*) getSqlUpdateBook:(Book*) book
{
    DBTable* table = [Book declareDBTable];
    return [table getSqlUpdateRow:book wCondition:[NSString stringWithFormat:@"%@ = '%@'", FIELD_BOOK_ID, [book getBookId]]];
}

+ (NSString*) getSqlInsertBook:(Book*) book
{
    DBTable* table = [Book declareDBTable];
    return [table getSqlInsertRow:book];
}

+ (NSString*) getSqlDeteteBook:(int) bookId
{
    return [NSString stringWithFormat:@"DELETE FROM %@ WHERE (%@ = %d)",
            TABLE_NAME_BOOK,
            FIELD_BOOK_ID,
            bookId];
}

//----------------------------------------------------------------------------
//
//                         BOOK MEMBERS ACCESS
//
//----------------------------------------------------------------------------

static int book_id_idx = -1;

- (int) getBookId
{
    if (book_id_idx < 0) {
        book_id_idx = [[Book declareDBTable] searchForFieldIndex:FIELD_BOOK_ID];
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
        book_id_idx = [[Book declareDBTable] searchForFieldIndex:FIELD_BOOK_ID];
    }
    
    [self setField:book_id_idx wInt:bookId];
}

// truong vuong add
- (NSString*) getBookTITLE{
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_TITLE];
    return [self getFieldAsString:index];
}
- (void) setBookTITLE:(NSString*) bookTITLE{
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_TITLE];
    [self setField:index wString:bookTITLE];
}



- (NSString*) getBookTYPE{
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_TYPE];
    return [self getFieldAsString:index];
}
- (void) setBookTYPE:(NSString*) bookTYPE{
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_TYPE];
    [self setField:index wString:bookTYPE];
}



- (NSString*) getBookISBN{
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_ISBN];
    return [self getFieldAsString:index];
}
- (void) setBookISBN:(NSString*) bookISBN{
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_ISBN];
    [self setField:index wString:bookISBN];
}

- (NSString*) getBookDESCRIPTION{
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_DESCRIPTION];
    return [self getFieldAsString:index];
}

- (void) setBookDESCRIPTION:(NSString*) bookDESCRIPTION{
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_DESCRIPTION];
    [self setField:index wString:bookDESCRIPTION];
}

- (NSString*) getBookAUTHOR {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_AUTHOR];
    return [self getFieldAsString:index];
}
- (void) setBookAUTHOR:(NSString*) bookAUTHOR {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_AUTHOR];
    [self setField:index wString:bookAUTHOR];
}

- (NSString*) getBookPUBLISHER {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_PUBLISHER];
    return [self getFieldAsString:index];
}
- (void) setBookPUBLISHER:(NSString*) bookPUBLISHER {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_PUBLISHER];
    [self setField:index wString:bookPUBLISHER];
}

- (NSString*) getBookPUBLISH_TIME {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_PUBLISH_TIME];
    return [self getFieldAsString:index];
}
- (void) setBookPUBLISH_TIME:(NSString*) bookPUBLISH_TIME {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_PUBLISH_TIME];
    [self setField:index wString:bookPUBLISH_TIME];
}


- (NSString*) getBookTRANSLATOR  {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_TRANSLATOR];
    return [self getFieldAsString:index];
}

- (void) setBookTRANSLATOR:(NSString*) bookTRANSLATOR {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_TRANSLATOR];
    [self setField:index wString:bookTRANSLATOR];
}

- (NSString*) getBookLANGUAGE {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_LANGUAGE];
    return [self getFieldAsString:index];
}

- (void) setBookLANGUAGE:(NSString*) bookLANGUAGE {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_LANGUAGE];
    [self setField:index wString:bookLANGUAGE];
}

- (NSString*) getBookMARKET_PRICE {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_MARKET_PRICE];
    return [self getFieldAsString:index];
}

- (void) setBookMARKET_PRICE:(NSString*) bookMARKET_PRICE {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_MARKET_PRICE];
    [self setField:index wString:bookMARKET_PRICE];
}

- (NSString*) getBookOUR_PRICE  {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_OUR_PRICE];
    return [self getFieldAsString:index];
}
- (void) setBookOUR_PRICE:(NSString*) bookOUR_PRICE {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_OUR_PRICE];
    [self setField:index wString:bookOUR_PRICE];
}


- (NSString*) getBookTHUMBNAIL {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_THUMBNAIL];
    return [self getFieldAsString:index];
}
- (void) setBookTHUMBNAIL:(NSString*) bookTHUMBNAIL {
    int index = [[Book declareDBTable] searchForFieldIndex:FIELD_THUMBNAIL];
    [self setField:index wString:bookTHUMBNAIL];
}


@end
