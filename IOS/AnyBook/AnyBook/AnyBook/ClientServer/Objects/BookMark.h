//
//  Book.h
//  AnyBook
//
//  Created by Vuong Van Truong on 4/10/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DBTable.h"
#import "DBFriendlyObject.h"
#import <sqlite3.h>


#define TABLE_NAME_BOOK                         @"bookmark"

#define FIELD_BOOK_MARK_ID                      @"book_mark_id"         //book mark id primary key 
#define FIELD_BOOK_ID                           @"book_id"              //bookID
#define FIELD_BOOK_PAGE                         @"book_mark_page"       //book mark page 
#define FIELD_BOOK_CHAPTER                      @"book_mark_chapter"    //book mark chapter
#define FIELD_BOOK_NUMBER_PHONE_USER            @"book_phone_user" 

@interface BookMark : DBFriendlyObject {
    
}

+ (NSString*) getDBTableName;
+ (DBTable*) declareDBTable;
+ (BookMark*) createBookFromDatabase:(sqlite3_stmt*) compiled;
+ (NSString*) getSqlSelectAllBook;
+ (NSString*) getSqlSelectBookById:(int) bookMarkId;
+ (NSString*) getSqlUpdateBook:(BookMark*) book;
+ (NSString*) getSqlInsertBook:(BookMark*) book;
+ (NSString*) getSqlDeteteBook:(int) bookMarkId;

#pragma mark - Member access


- (int) getBookMarkId;
- (void) setBookMarkId:(int) bookMarkId;

- (int) getBookMarkPage;
- (void) setBookMarkPage:(int) bookMarkPage;

- (int) getBookMarkChapter;
- (void) setBookMarkChapter:(int) bookMarkChapter;

- (int) getBookMarkPhone;
- (void) setBookMarkPhone:(NSString*) bookMarkPhone;

// book id use call table book
- (int) getBookId;
- (NSString*) getBookIdAsString;
- (void) setBookId:(int) bookId;





@end
