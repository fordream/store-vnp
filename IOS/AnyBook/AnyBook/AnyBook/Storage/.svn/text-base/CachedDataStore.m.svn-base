//
//  DataStore.m
//  Imuzik3G
//
//  Created by Ngo Tri Hoai on 4/9/12.
//  Copyright 2012 Vega Corp. All rights reserved.
//

#import "CachedDataStore.h"
#import "Utility.h"
#import "DBUserBook.h"
#import "JsonSync.h"
#import "JsonBook.h"
#import <stdlib.h>

@interface CachedDataStore()

#pragma mark - Private functions, please don't invoke them externally

- (NSString*) getCurrentUserMobile;
- (void) private_initCache;
- (void) private_clearCache;

- (BOOL) private_deleteUserBook:(DBUserBook*) dbub;

@end

@implementation CachedDataStore

//----------------------------------------------------------------------------
//
//                SINGLETON IMPLEMENTATION, CONSTRUCTOR/DESTRUCTOR
//
//----------------------------------------------------------------------------
#pragma mark Singleton, CachedDataStore init/delloc

static CachedDataStore* _sharedDataStore = nil;

+ (CachedDataStore*) getInstance
{
	@synchronized([CachedDataStore class])
	{
		if (!_sharedDataStore) {
            DLog_High(@"alloc & init CachedDataStore");
            _sharedDataStore = [[CachedDataStore alloc] init];
        }			
        
		return _sharedDataStore;
	}
}

+ (void)cleanSharedInstance
{
    _sharedDataStore = nil;
}

+(id)alloc
{
	@synchronized([CachedDataStore class])
	{	
		NSAssert(_sharedDataStore == nil, @"Attempted to allocate a second instance of a singleton.");
        if (_sharedDataStore == nil) {
            _sharedDataStore = [super alloc];
        }
		return _sharedDataStore;
	}
    
	return nil;
}

-(id)init {
	self = [super init];
	if (self != nil) {
        [self private_initCache];
	}
    
	return self;
}

-(void)dealloc 
{
    [self private_clearCache];
}

//----------------------------------------------------------------------------
//
//                              Cached
//
//----------------------------------------------------------------------------

- (void) private_initCache
{
    //Init configs
    dicConfigs = [self db_getAllKeyValue];
    dicBooks = [self db_getListOfBook];
    dicBooksMark  = [self db_getListOfBookMarks];
}

- (void) private_clearCache
{
    dicConfigs = Nil;
}

//----------------------------------------------------------------------------
//
//                              User
//
//----------------------------------------------------------------------------
#pragma mark User

#define KEY_USER_FULL_NAME  @"user_full_name"
#define KEY_USER_MOBILE     @"user_mobile"
#define KEY_USER_EMAIL      @"user_email"
#define KEY_USER_ADDRESS    @"user_address"
#define KEY_USER_PASSWORD   @"user_password"

- (BOOL) setCurrentUserAccount:(UserAccount*) account;
{    
    if (![self setStringConfig:[account user_full_name] forKey:KEY_USER_FULL_NAME]) {
        return NO;
    }
    if (![self setStringConfig:[account user_mobile] forKey:KEY_USER_MOBILE]) {
        return NO;
    }
    if (![self setStringConfig:[account user_email] forKey:KEY_USER_EMAIL]) {
        return NO;
    }
    if (![self setStringConfig:[account user_address] forKey:KEY_USER_ADDRESS]) {
        return NO;
    }
    if (![self setStringConfig:[account user_password] forKey:KEY_USER_PASSWORD]) {
        return NO;
    }
    return YES;
}

- (UserAccount*) getCurrentUserAccount
{
    UserAccount* account = [[UserAccount alloc] init];
    [account setUser_full_name:[dicConfigs objectForKey:KEY_USER_FULL_NAME]];
    [account setUser_mobile:[dicConfigs objectForKey:KEY_USER_MOBILE]];
    [account setUser_email:[dicConfigs objectForKey:KEY_USER_EMAIL]];
    [account setUser_address:[dicConfigs objectForKey:KEY_USER_ADDRESS]];
    [account setUser_password:[dicConfigs objectForKey:KEY_USER_PASSWORD]];
    
    return account;
}

- (NSString*) getCurrentUserMobile
{
    return [dicConfigs objectForKey:KEY_USER_MOBILE];    
}

//----------------------------------------------------------------------------
//
//                              Books
//
//----------------------------------------------------------------------------

- (BOOL) addBook:(Book*) book
{
    @synchronized (dicBooks) {
        NSString* bookId = [book getBookIdAsString];
        if ([dicBooks objectForKey:bookId] != Nil) {
            if (![self db_updateBook:book]) {
                return NO;
            }
        } else {
            if (![self db_insertBook:book]) {
                return NO;
            }
        }
        [dicBooks setValue:book forKey:bookId];
    }
    return YES;
}

- (BOOL) addBookList:(NSArray*) list
{
    BOOL passed = YES;
    for (NSObject* obj in list) {
        if ([obj isKindOfClass:[Book class]]) {
            if (![self addBook:(Book*) obj]) {
                passed = NO;
            }
        } else {
            passed = NO;
        }
    }
    return passed;
}

- (NSArray*) getCurrentUserBookShelf
{
    NSMutableArray* userbook = [self db_getUserBookForUser:[self getCurrentUserMobile]];
    NSMutableArray* list = [[NSMutableArray alloc] init];
    @synchronized (dicBooks) {
        for (DBUserBook* ub in userbook) {
            NSString* bookid = [ub getBookIdAsString];
            [list addObject:[dicBooks objectForKey:bookid]];
        }
    }
    return list;
}

//----------------------------------------------------------------------------
//
//                              Current User Book Self
//
//----------------------------------------------------------------------------

- (BOOL) markRemoveBookFromCurrentUserBookShelf:(Book*) book
{
    DBUserBook* dbub = [self db_getUserBookForUserByBookId:[self getCurrentUserMobile] wBookId:[book getBookId]];
    if ((dbub != Nil) && (![dbub isMarkRemoved])) {
        [dbub markRemoved:YES];
        if (![self db_updateUserBook:dbub]) {
            return NO;
        }
    }
    return YES;
}

- (BOOL) addBookToCurrentUserBookShelf:(int) bookId
{
    Book* cached = Nil;
    @synchronized (dicBooks) {
        cached = [dicBooks objectForKey:[NSString stringWithFormat:@"%d", bookId]];
        if (cached == Nil) {
            return NO;
        }
        
        DBUserBook* dbub = [[DBUserBook alloc] init];
        [dbub setBookId:bookId];
        [dbub setUserMsisdn:[self getCurrentUserMobile]];
        
        return [self db_insertUserBook:dbub];
    }
}

- (BOOL) updateUserBook:(DBUserBook*) userbook
{
    return [self db_updateUserBook:userbook];
}

- (BOOL) private_deleteUserBook:(DBUserBook*) dbub
{
    if (![self db_deleteUserBook:dbub]) {
        return NO;
    }
    
    //Check and remove from book list if there is no reference to this book
    int count = [self db_countUserBookForBookId:[dbub getBookId]];
    if (count == 0) {
        @synchronized (dicBooks) {
            if ([self db_deleteBook:[dbub getBookId]]) {
                [dicBooks removeObjectForKey:[dbub getBookIdAsString]];
            }
        }
    }
    return YES;
}

- (DBUserBook*) getUserBookById:(int) bookId
{
    return [self db_getUserBookForUserByBookId:[self getCurrentUserMobile] wBookId:bookId];
}

- (BOOL) doSynchonizeUserBookShelf
{
    return [self doSynchonizeUserBookShelf:Nil];
}

- (BOOL) doSynchonizeUserBookShelf:(id<URLCacheConnectionDelegate>) deligate
{
    NSString* msisdn = [self getCurrentUserMobile];
    if (![Utility isDeviceActivated]) {
        JsonBase* jb = [JsonBase registerDevice:msisdn :[Utility getDeviceProfile] :[Utility getDeviceImei]];
        if (![jb isDeviceAlreadyExisted] && ![jb isSuccess]) {
            return NO;
        }
        [Utility setDeviceActivated];
    }
    

    JsonSync* jsonSync = [JsonSync getUserBookShelfInfo:msisdn];
    if (![jsonSync isSuccess]) {
        return NO;
    }
    
    NSMutableArray* list_user_book = [self db_getAllUserBookForUser:msisdn];
    NSMutableDictionary* dic_user_book = [[NSMutableDictionary alloc] init];
    for (DBUserBook* b in list_user_book) {
        [dic_user_book setValue:b forKey:[b getBookIdAsString]];
    }

    NSMutableArray* list_book_id_to_add = [[NSMutableArray alloc] init];
    NSMutableArray* list_book_id_to_download = [[NSMutableArray alloc] init];
    NSArray* list_from_server = [jsonSync list_book_info];
    @synchronized (dicBooks) {
        for (JsonSyncUserBookInfo* compactInfo in list_from_server) {
            int book_id = [compactInfo book_id];
            NSString* sBookId = [NSString stringWithFormat:@"%d", book_id];
            DBUserBook* local_book = [dic_user_book objectForKey:sBookId];
            if (local_book == Nil) {
                [list_book_id_to_add addObject:[NSNumber numberWithInt:book_id]];
                if ([dicBooks objectForKey:sBookId] == Nil) {
                    [list_book_id_to_download addObject:[NSNumber numberWithInt:book_id]];
                }
            }
        }
    }
    
    //Now download books and save to database first
    NSString* profile = [Utility getDeviceProfile];
    NSString* imei = [Utility getDeviceImei];
    for (NSNumber* book_id in list_book_id_to_download) {
        JsonBook* jbook = [JsonBook getBook:msisdn book_id:[book_id intValue] device_profile:profile device_imei:imei];
        if ([jbook isSuccess]) {
            [self addBook:[jbook book]];
        }
    }
    
    //Add book to user book shelf
    for (NSNumber* book_id in list_book_id_to_add) {
        [self addBookToCurrentUserBookShelf:[book_id intValue]];
    }
    
    //Remove deleted book
    for (DBUserBook* dbub in list_user_book) {
        if ([dbub isMarkRemoved]) {
            JsonBase* jb = [JsonBase removeBookFromUserBookShelf:msisdn wBookId:[dbub getBookId]];
            if ([jb isSuccess]) {
                [self private_deleteUserBook:dbub];
            }
        }
    }
    
    //Do download book if needed
    NSArray* list_all_user_book = [self db_getUserBookForUser:msisdn];
    NSMutableArray* not_cached_yet = [[NSMutableArray alloc] init];
    for (DBUserBook* ub in list_all_user_book) {
        if ([Utility isEmptyOrNull:[ub getCachedPath]]) {
            [not_cached_yet addObject:ub];
        }
    }
    
    if ([not_cached_yet count] > 0) {
        URLCacheConnection* ucc = [URLCacheConnection getInstance];
        if (deligate != Nil) {
            [ucc addListener:deligate];
        }
        [ucc downloadBookList:not_cached_yet];
    }
    
    return YES;
}

//----------------------------------------------------------------------------
//
//                              Configs
//
//----------------------------------------------------------------------------
#pragma mark - Configs
- (NSString*) getStringConfig:(NSString*) skey
{
    return [dicConfigs objectForKey:skey];
}

- (BOOL) setStringConfig:(NSString*) svalue forKey:(NSString*) skey
{
    @synchronized (dicConfigs) {
        if ([dicConfigs objectForKey:skey] != Nil) {
            if (![self db_updateKeyValue:skey wValue:svalue]) {
                return NO;
            }
        } else {
            if (![self db_insertKeyValue:skey wValue:svalue]) {
                return NO;
            }
        }
        [dicConfigs setValue:svalue forKey:skey];
    }
    return YES;
}

//---------------------
//      Book Mark
//---------------------
- (BOOL) addBookMark:(BookMark*) bookMark{
    @synchronized (dicBooksMark) {
        NSString* bookId = [bookMark getBookIdAsString];
        if ([dicBooksMark objectForKey:bookId] != Nil) {
            if (![self db_updateBookMarks:bookMark]) {
                return NO;
            }
        } else {
            if (![self db_insertBookMarks:bookMark]) {
                return NO;
            }
        }
        [dicBooksMark setValue:bookMark forKey:bookId];
    }
    return YES;
}

- (BOOL) addBookMarkList:(NSArray*) list{
    BOOL passed = YES;
    for (NSObject* obj in list) {
        if ([obj isKindOfClass:[BookMark class]]) {
            if (![self addBookMark:(BookMark*) obj]) {
                passed = NO;
            }
        } else {
            passed = NO;
        }
    }
    return passed;
}
- (NSArray*) getBookMarkShelf{
    NSMutableArray* userbook = [self db_getUserBookForUser:[self getCurrentUserMobile]];
    NSMutableArray* list = [[NSMutableArray alloc] init];
    @synchronized (dicBooksMark) {
        for (DBUserBook* ub in userbook) {
            NSString* bookid = [ub getBookIdAsString];
            [list addObject:[dicBooksMark objectForKey:bookid]];
        }
    }
    return list;
}

@end
