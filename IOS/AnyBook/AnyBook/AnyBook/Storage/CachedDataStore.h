//
//  DataStore.h
//  Imuzik3G
//
//  Created by Ngo Tri Hoai on 4/9/12.
//  Copyright 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LocalDatabase.h"
#import "UserAccount.h"
#import "URLCacheConnection.h"
#import "BookMark.h"
// add for setting
#define KEY_STATUS_ON @"key_status_on"
#define KEY_DISPLAY_VALUE @"key_display_value"

#define KEY_ASYN_FULL @"key_asyn_full"

//paging
#define KEY_PAGING @"key_paging"
#define PAGING_10px @"10 px"
#define PAGING_20px @"20 px"
#define PAGING_30px @"30 px"
#define PAGING_40px @"40 px"
#define PAGING_50px @"50 px"

//DayOrNight
#define KEY_DAY_OR_NIGHT @"KEY_DAY_OR_NIGHT"
#define DAY_AUTO @"Auto"
#define DAY_DAY @"DAY"
#define DAY_NIGHT @"NIGHT"

#define ON @"ON"
#define OFF @"OFF"

//paging
#define KEY_FONT_SIZE @"KEY_FONT_SIZE"
#define KEY_FONT_SIZE_VERY_SMALL @"very small"
#define KEY_FONT_SIZE_SMALL @"small"
#define KEY_FONT_SIZE_NORMAL @"normal"
#define KEY_FONT_SIZE_BIG @"big"
#define KEY_FONT_SIZE_VERY_BIG @"very big"

//paging
#define KEY_ORIENT @"KEY_ORIENT"
#define KEY_ORIENT_AUTO @"KEY_ORIENT_AUTO"
#define KEY_ORIENT_H @"KEY_ORIENT_H"
#define KEY_ORIENT_V @"KEY_ORIENT_V"




//-----------------------     CLASS     ------------------------------------

@interface CachedDataStore : LocalDatabase {
    
    NSMutableDictionary* dicConfigs;
    NSMutableDictionary* dicBooks;
    
    NSMutableDictionary* dicBooksMark;
}

+ (CachedDataStore*) getInstance;

#pragma mark - User

- (BOOL) setCurrentUserAccount:(UserAccount*) account;
- (UserAccount*) getCurrentUserAccount;

#pragma mark - Book

- (BOOL) addBook:(Book*) book;
- (BOOL) addBookList:(NSArray*) list;

#pragma mark - User book shelf

- (NSArray*) getCurrentUserBookShelf;
- (BOOL) markRemoveBookFromCurrentUserBookShelf:(Book*) book;
- (BOOL) addBookToCurrentUserBookShelf:(int) bookId;
- (BOOL) doSynchonizeUserBookShelf;
- (BOOL) doSynchonizeUserBookShelf:(id<URLCacheConnectionDelegate>) deligate;
- (BOOL) updateUserBook:(DBUserBook*) userbook;
- (DBUserBook*) getUserBookById:(int) bookId;

#pragma mark - Configs
- (NSString*) getStringConfig:(NSString*) skey;
- (BOOL) setStringConfig:(NSString*) svalue forKey:(NSString*) skey;

#pragma mark - BookMarks
- (BOOL) addBookMark:(BookMark*) bookMark;
- (BOOL) addBookMarkList:(NSArray*) list;
- (NSArray*) getBookMarkShelf;

@end

