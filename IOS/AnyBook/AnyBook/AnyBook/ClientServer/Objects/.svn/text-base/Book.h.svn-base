//
//  Book.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/10/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DBTable.h"
#import "DBFriendlyObject.h"
#import <sqlite3.h>

@interface Book : DBFriendlyObject {
    
}

+ (NSString*) getDBTableName;
+ (DBTable*) declareDBTable;
+ (Book*) createBookFromDatabase:(sqlite3_stmt*) compiled;

+ (NSString*) getSqlSelectAllBook;
+ (NSString*) getSqlSelectBookById:(int) bookId;
+ (NSString*) getSqlUpdateBook:(Book*) book;
+ (NSString*) getSqlInsertBook:(Book*) book;
+ (NSString*) getSqlDeteteBook:(int) bookId;

#pragma mark - Member access
- (int) getBookId;
- (NSString*) getBookIdAsString;
- (void) setBookId:(int) bookId;

// truong vuong add
- (NSString*) getBookTITLE;
- (void) setBookTITLE:(NSString*) bookTITLE;

- (NSString*) getBookTYPE;
- (void) setBookTYPE:(NSString*) bookTYPE;

- (NSString*) getBookISBN;
- (void) setBookISBN:(NSString*) bookISBN;

- (NSString*) getBookDESCRIPTION;
- (void) setBookDESCRIPTION:(NSString*) bookDESCRIPTION;

- (NSString*) getBookAUTHOR ;
- (void) setBookAUTHOR:(NSString*) bookAUTHOR ;

- (NSString*) getBookPUBLISHER ;
- (void) setBookPUBLISHER:(NSString*) bookPUBLISHER ;

- (NSString*) getBookPUBLISH_TIME ;
- (void) setBookPUBLISH_TIME:(NSString*) bookPUBLISH_TIME ;


- (NSString*) getBookTRANSLATOR ;
- (void) setBookTRANSLATOR:(NSString*) bookTRANSLATOR ;

- (NSString*) getBookLANGUAGE ;
- (void) setBookLANGUAGE:(NSString*) bookLANGUAGE ;

- (NSString*) getBookMARKET_PRICE ;
- (void) setBookMARKET_PRICE:(NSString*) bookMARKET_PRICE ;

- (NSString*) getBookOUR_PRICE ;
- (void) setBookOUR_PRICE:(NSString*) bookOUR_PRICE ;

- (NSString*) getBookTHUMBNAIL ;
- (void) setBookTHUMBNAIL:(NSString*) bookTHUMBNAIL ;
@end
