//
//  LocalDatabase.m
//  Imuzik3G
//
//  Created by Ngo Tri Hoai on 4/9/12.
//  Copyright 2012 Vega Corp. All rights reserved.
//

#import "LocalDatabase.h"
#import "DBUserBook.h"

#define STRING_DATABASE_NAME @"AnyBook.db"

//----------------------------------------------------------------------------
//
//                         LOCAL DATABASE
//
//----------------------------------------------------------------------------


#pragma mark Private functions
@interface LocalDatabase()

-(BOOL)     private_checkAndOpenDatabase;
-(BOOL)     private_openDatabase;
-(void)     private_closeDatabase;
-(void)     private_ensureDatabaseOpen;
-(int)      private_executeSql:(NSString*) sql;

@end


@implementation LocalDatabase

-(id) init {
	self = [super init];
	if (self != nil) {
    }
    return self;
}

-(void)dealloc 
{
    [self private_closeDatabase];
}

//----------------------------------------------------------------------------
//
//                         BASE MANAGEMENT
//
//----------------------------------------------------------------------------
#pragma mark Base Management
-(BOOL) private_openDatabase
{
    if (sharedDatabase != nil) {
        return TRUE;
    }
    
    [self private_checkAndOpenDatabase];
    return TRUE;
}

-(void) private_closeDatabase
{
    if (sharedDatabase != nil) {
        sqlite3_close(sharedDatabase);
        sharedDatabase = nil;
    }
}

-(void) private_ensureDatabaseOpen
{
    if (sharedDatabase == nil) {
        [self private_openDatabase];
    }
}

-(int)      private_executeSql:(NSString*) sql
{
    @synchronized([LocalDatabase class]){
        int res = 0;
        sqlite3_stmt *compiled = nil;
        DLog_Low(@"SQL = %@", sql);
        
        if (sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) != SQLITE_OK) {
            DLog_Error(@"Compile Sql statement failed: %@", [self getLastDBError]);
            res = -1;
        } else {
            if (sqlite3_step(compiled) != SQLITE_DONE) {
                DLog_Error(@"Execute Sql statement failed: %@", [self getLastDBError]);
                res = -1;
            }
            sqlite3_finalize(compiled);
            compiled = nil;
        }
        return res;
    }
}

-(NSString*) getLastDBError
{
    return [NSString stringWithUTF8String:sqlite3_errmsg(sharedDatabase)];
}

-(BOOL) private_checkAndOpenDatabase
{
    DLog_Low(@"GetIn");
    
	// Get the path to the documents directory and append the databaseName
	NSArray *documentPaths = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES);
	NSString *documentsDir = [documentPaths objectAtIndex:0];
	NSString* sDatabasePath = [documentsDir stringByAppendingPathComponent:STRING_DATABASE_NAME];
    
    BOOL needCreateTable = TRUE;

	// Check if the database has already been created in the users filesystem
	NSFileManager *fileManager = [NSFileManager defaultManager];
	if ([fileManager fileExistsAtPath:sDatabasePath]) {
        DLog_Low(@"Database exist!!!");
        needCreateTable = FALSE;
    }
    
    //Open database
	if (sqlite3_open([sDatabasePath UTF8String], &sharedDatabase) != SQLITE_OK) {
        DLog_Error(@"Failed openning database: %@", sDatabasePath);
        return FALSE;
    }
    
    if (!needCreateTable) {
        return TRUE;
    }

    //Create tables on first run
    DLog_Low(@"Creating new database file!!!");
    //Book
    [self private_executeSql:[[Book declareDBTable] getSqlCreateTable]];
    
     [self private_executeSql:[[BookMark declareDBTable] getSqlCreateTable]];
    
    //DBUserBook
    [self private_executeSql:[[DBUserBook declareDBTable] getSqlCreateTable]];
    
    //Config
    [self private_executeSql:@"CREATE TABLE configs(skey text, svalue text)"];
    
    //Test only
//    [self private_executeSql:@"INSERT INTO book(book_id, title, type, isbn, description, author, publisher, publish_time, translator, language, market_price, our_price, thumbnail) VALUES('book_id', 'title', 'type', 'isbn', 'description', 'author', 'publisher', 'publish_time', 'translator', 'language', 'market_price', 'our_price', 'thumbnail')"];
//    
//    [self private_executeSql:@"INSERT INTO book(book_id, title, type, isbn, description, author, publisher, publish_time, translator, language, market_price, our_price, thumbnail) VALUES('book_id 2', 'title 2', 'type 2', 'isbn 2', 'description 2', 'author 2', 'publisher 2', 'publish_time 2', 'translator 2', 'language 2', 'market_price 2', 'our_price 2', 'thumbnail 2')"];
//
//    [self private_executeSql:@"INSERT INTO user_book(book_id, user_msisdn, mark_removed) VALUES('book_id', '123456789', 0)"];
//    [self private_executeSql:@"INSERT INTO user_book(book_id, user_msisdn, mark_removed) VALUES('book_id 2', '123456789', 0)"];
//    
//    [self private_executeSql:@"INSERT INTO configs(skey, svalue) VALUES ('user_full_name', 'Hello')"];
//    [self private_executeSql:@"INSERT INTO configs(skey, svalue) VALUES ('user_mobile', '123456789')"];
//    [self private_executeSql:@"INSERT INTO configs(skey, svalue) VALUES ('user_email', 'aa@bb.com')"];
//    [self private_executeSql:@"INSERT INTO configs(skey, svalue) VALUES ('user_address', 'ha ha')"];
    
//    [self executeSql:sql];
    
//    sql = @"CREATE TABLE Artist(id integer primary key, name text, image_url text, description text, album_count integer, song_count integer)";
//    [self executeSql:sql];
//
//    sql = @"CREATE TABLE Playlist(id integer primary key, serverid integer, title text, type integer, last_update integer, synced_song_count integer, shuffle_mode integer, offline_mode integer, modified integer, deleted integer)";
//    [self executeSql:sql];
//    
//    sql = @"CREATE TABLE Song(id integer primary key, title text, duration integer, artist_id integer, artist_name text, artist_image_url text, artist_image_url_large text, album_id integer, album_title text, cached_file_path text, category text)";
//    [self executeSql:sql];
//    
//    sql = @"CREATE TABLE Songs_Of_Album(album_id integer, song_id integer, primary key(album_id, song_id))";
//    [self executeSql:sql];
//    
//    sql = @"CREATE TABLE Songs_Of_Playlist(playlist_id integer, song_id integer, modified integer, deleted integer, song_order integer)";
//    [self executeSql:sql];
//    
//    sql = @"CREATE TABLE Config(skey text, svalue text)";
//    [self executeSql:sql];
//    
//    sql = @"CREATE TABLE Songs_To_Cache(id integer primary key, title text, artist_name text);";
//    [self executeSql:sql];
//    
//    //Insert default values
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('dbver', '1')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('%@', '%@')", STRING_PL_LASTUPDATE_KEY, sLastUpdate];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('username', '')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('password', '')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('memory_limit', '0.5')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('package_code', '')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('account_info', '')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('rate_count', '-1')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('begin_date', '')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('end_date', '')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('avatar', '')"];
//    [self executeSql:sql];
//    
//    sql = [NSString stringWithFormat:@"INSERT INTO Config(skey, svalue) VALUES ('quality', '-1')"];
//    [self executeSql:sql];
//    
//    sql = @"CREATE TABLE Favourited_Playlist(id integer primary key, serverid integer, title text, creator_name text,total integer, avatar text)";
//    [self executeSql:sql];
//    
//    sql = @"CREATE TABLE Songs_Of_Favourited_Playlist(playlist_id integer, song_id integer, primary key(playlist_id, song_id))";
//    [self executeSql:sql];
//    
//    sql = @"CREATE TABLE User_Info(login_name text, phone_number text, package_code text, begin_date text, end_date text, account_info text, avatar text, full_name text, gender integer, address text, password text, birthday text, email text);";
//    [self executeSql:sql];
//    
//    sql = @"INSERT INTO User_Info(login_name, phone_number, package_code, begin_date, end_date, account_info, avatar, full_name, gender, address, password, birthday, email) VALUES('', '', '', '', '', '', '', '', 0, '', '', '', '');";
//    [self executeSql:sql];
    
    DLog_Low(@"GetOut");
    
    return TRUE;
}


//----------------------------------------------------------------------------
//
//                         BOOK
//
//----------------------------------------------------------------------------

- (NSMutableDictionary*) db_getListOfBook
{
    NSMutableDictionary* dic = [[NSMutableDictionary alloc] init];
    NSString* sql = [Book getSqlSelectAllBook];
    DLog_Low(@"SQL = %@", sql);
    
    [self private_ensureDatabaseOpen];
    @synchronized([LocalDatabase class]) {
        
        sqlite3_stmt *compiled;
        if(sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) == SQLITE_OK) {
            
            // Loop through the results and add them to the feeds array
            while(sqlite3_step(compiled) == SQLITE_ROW) {
                Book* book = [Book createBookFromDatabase:compiled];
                [dic setValue:book forKey:[book getBookIdAsString]];
            }
        } else {
            DLog_Error(@"Exec SQL failed: %@", [self getLastDBError]);
        }
        // Release the compiled statement from memory
        sqlite3_finalize(compiled);        
    }
    
    return dic;
}

- (BOOL)            db_updateBook:(Book*) book
{
    NSString* sql = [Book getSqlUpdateBook:book];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;
}

- (BOOL)            db_insertBook:(Book*) book
{
    NSString* sql = [Book getSqlInsertBook:book];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;
}

- (BOOL)                    db_deleteBook:(int) bookid
{
    NSString* sql = [Book getSqlDeteteBook:bookid];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;
}

- (Book*) db_getBookById:(int) bookId
{
    Book* book = Nil;
    NSString* sql = [Book getSqlSelectBookById:bookId];
    DLog_Low(@"SQL = %@", sql);
    
    [self private_ensureDatabaseOpen];
    @synchronized([LocalDatabase class]) {
        
        sqlite3_stmt *compiled;
        if(sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) == SQLITE_OK) {
            
            // Loop through the results and add them to the feeds array
            if (sqlite3_step(compiled) == SQLITE_ROW) {
                book = [Book createBookFromDatabase:compiled];
            }
        } else {
            DLog_Error(@"Exec SQL failed: %@", [self getLastDBError]);
        }
        // Release the compiled statement from memory
        sqlite3_finalize(compiled);        
    }
    return book;
}

//----------------------------------------------------------------------------
//
//                         DB USER BOOK
//
//----------------------------------------------------------------------------

- (NSMutableArray*)         db_getAllUserBookForUser:(NSString*) msisdn
{
    return [self db_doGetUserBookList:[DBUserBook getSqlSelectAllUserBook:msisdn]];
}

- (NSMutableArray*) db_getUserBookForUser:(NSString*) msisdn
{
    return [self db_doGetUserBookList:[DBUserBook getSqlSelectUserBookShelf:msisdn]];
}



- (NSMutableArray*) db_getUserBookMarkedRemoveForUser:(NSString*) msisdn
{
    return [self db_doGetUserBookList:[DBUserBook getSqlSelectUserMarkRemovedBook:msisdn]];
}

- (NSMutableArray*)         db_doGetUserBookList:(NSString*) sql
{
    NSMutableArray* list = [[NSMutableArray alloc] init];    
    DLog_Low(@"SQL = %@", sql);

    [self private_ensureDatabaseOpen];
    @synchronized([LocalDatabase class]) {
        
        sqlite3_stmt *compiled;
        if(sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) == SQLITE_OK) {
            
            // Loop through the results and add them to the feeds array
            while(sqlite3_step(compiled) == SQLITE_ROW) {
                DBUserBook* userbook = [DBUserBook createDBUserBookFromDatabase:compiled];
                [list addObject:userbook];
            }
        } else {
            DLog_Error(@"Exec SQL failed: %@", [self getLastDBError]);
        }
        // Release the compiled statement from memory
        sqlite3_finalize(compiled);        
    }
    
    return list;
}

- (DBUserBook*)             db_getUserBookForUserByBookId:(NSString*) msisdn wBookId:(int) bookId
{
    NSArray* list = [self db_doGetUserBookList:[DBUserBook getSqlSelectUserBookByBookId:msisdn wBookId:bookId]];
    if ([list count] > 0) {
        return (DBUserBook*) [list objectAtIndex:0];
    }
    return Nil;
}

- (BOOL)                    db_insertUserBook:(DBUserBook*) userbook
{
    NSString* sql = [DBUserBook getSqlInsertUserBook:userbook];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;
}

- (BOOL)                    db_updateUserBook:(DBUserBook*) userbook
{
    NSString* sql = [DBUserBook getSqlUpdateUserBook:userbook];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;
}

- (BOOL)                    db_deleteUserBook:(DBUserBook*) userbook
{
    NSString* sql = [DBUserBook getSqlDeteteUserBook:userbook];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;    
}

- (int)                     db_countUserBookForBookId:(int)bookid
{
    int res = 0;
    NSString* sql = [DBUserBook getSqlCountUserBookForBookId:bookid];
    [self private_ensureDatabaseOpen];
    
    @synchronized([LocalDatabase class]) {
        sqlite3_stmt *compiled;
        if(sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) == SQLITE_OK) {
            
            // Loop through the results and add them to the feeds array
            if (sqlite3_step(compiled) == SQLITE_ROW) {
                res = sqlite3_column_int(compiled, 0);
            }
        } else {
            DLog_Error(@"Exec SQL failed: %@", [self getLastDBError]);
        }
        // Release the compiled statement from memory
        sqlite3_finalize(compiled);
    }
    return res;
}

//----------------------------------------------------------------------------
//
//                         Configs
//
//----------------------------------------------------------------------------

- (NSString*)       db_getValueForKey:(NSString*) key
{
    NSString* res = Nil;
    NSString* sql = [NSString stringWithFormat:@"SELECT svalue FROM configs WHERE skey = '%@';", key];
    [self private_ensureDatabaseOpen];
    
    @synchronized([LocalDatabase class]) {
        sqlite3_stmt *compiled;
        if(sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) == SQLITE_OK) {
            
            // Loop through the results and add them to the feeds array
            if (sqlite3_step(compiled) == SQLITE_ROW) {
                res = [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiled, 0)];
            }
        } else {
            DLog_Error(@"Exec SQL failed: %@", [self getLastDBError]);
        }
        // Release the compiled statement from memory
        sqlite3_finalize(compiled);        
    }
    return res;
}

- (NSMutableDictionary*)   db_getAllKeyValue
{
    NSMutableDictionary* res = [[NSMutableDictionary alloc] init];
    NSString* sql = @"SELECT skey, svalue FROM configs;";
    [self private_ensureDatabaseOpen];
    
    @synchronized([LocalDatabase class]) {
        sqlite3_stmt *compiled;
        if(sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) == SQLITE_OK) {
            
            // Loop through the results and add them to the feeds array
            while (sqlite3_step(compiled) == SQLITE_ROW) {
                NSString* skey = [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiled, 0)];
                NSString* sval = [NSString stringWithUTF8String:(char *)sqlite3_column_text(compiled, 1)];
                [res setValue:sval forKey:skey];
            }
        } else {
            DLog_Error(@"Exec SQL failed: %@", [self getLastDBError]);
        }
        // Release the compiled statement from memory
        sqlite3_finalize(compiled);        
    }
    return res;
}

- (BOOL)            db_insertKeyValue:(NSString*) skey wValue:(NSString*) svalue
{
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:[NSString stringWithFormat:
                                     @"INSERT INTO configs(skey, svalue) VALUES('%@', '%@');",
                                     skey,
                                     svalue
                                     ]] == 0;
}

- (BOOL)            db_updateKeyValue:(NSString*) skey wValue:(NSString*) svalue
{
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:[NSString stringWithFormat:
                                     @"UPDATE configs SET svalue = '%@' WHERE skey = '%@';",
                                     svalue,
                                     skey
                                     ]] == 0;
}


//----------------------------------------
//          Book Mark
//----------------------------------------
- (NSMutableDictionary*) db_getListOfBookMarks
{
    NSMutableDictionary* dic = [[NSMutableDictionary alloc] init];
    NSString* sql = [BookMark getSqlSelectAllBook];
    DLog_Low(@"SQL = %@", sql);
    
    [self private_ensureDatabaseOpen];
    @synchronized([LocalDatabase class]) {
        
        sqlite3_stmt *compiled;
        if(sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) == SQLITE_OK) {
            
            // Loop through the results and add them to the feeds array
            while(sqlite3_step(compiled) == SQLITE_ROW) {
                BookMark* book = [BookMark createBookFromDatabase:compiled];
                [dic setValue:book forKey:[book getBookIdAsString]];
            }
        } else {
            DLog_Error(@"Exec SQL failed: %@", [self getLastDBError]);
        }
        // Release the compiled statement from memory
        sqlite3_finalize(compiled);        
    }
    
    return dic;
}



- (BOOL) db_updateBookMarks:(BookMark*) bookMark{
    NSString* sql = [BookMark getSqlUpdateBook:bookMark];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;

}
- (BOOL) db_insertBookMarks:(BookMark*) bookMark{
    NSString* sql = [BookMark getSqlInsertBook:bookMark];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;
}
- (BOOL)db_deleteBookMarks:(int) bookMarkid{
        NSString* sql = [BookMark getSqlDeteteBook:bookMarkid];
    [self private_ensureDatabaseOpen];
    return [self private_executeSql:sql] == 0;
}
- (BookMark*) db_getBookMarkById:(int) bookMarkId{
    BookMark* book = Nil;
    NSString* sql = [BookMark getSqlSelectBookById:bookMarkId];
    DLog_Low(@"SQL = %@", sql);
    
    [self private_ensureDatabaseOpen];
    @synchronized([LocalDatabase class]) {
        
        sqlite3_stmt *compiled;
        if(sqlite3_prepare_v2(sharedDatabase, [sql UTF8String], -1, &compiled, NULL) == SQLITE_OK) {
            
            // Loop through the results and add them to the feeds array
            if (sqlite3_step(compiled) == SQLITE_ROW) {
                book = [BookMark createBookFromDatabase:compiled];
            }
        } else {
            DLog_Error(@"Exec SQL failed: %@", [self getLastDBError]);
        }
        // Release the compiled statement from memory
        sqlite3_finalize(compiled);        
    }
    return book;

}

@end
