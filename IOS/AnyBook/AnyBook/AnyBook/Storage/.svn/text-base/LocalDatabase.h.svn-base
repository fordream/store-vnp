//
//  LocalDatabase.h
//  Imuzik3G
//
//  Created by Ngo Tri Hoai on 4/9/12.
//  Copyright 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>
#import "Book.h"
#import "DBUserBook.h"
#import "BookMark.h"

@interface LocalDatabase : NSObject {

@private
    sqlite3*    sharedDatabase;
    
@protected
}

//-----------------------    BASIC MANAGEMENT   ------------------------------------

-(NSString*) getLastDBError;

//-----------------------    BOOK   ------------------------------------

- (NSMutableDictionary*)    db_getListOfBook;
- (BOOL)                    db_updateBook:(Book*) book;
- (BOOL)                    db_insertBook:(Book*) book;
- (BOOL)                    db_deleteBook:(int) bookid;
- (Book*)                   db_getBookById:(int) bookId;

//-----------------------    USER BOOK   ------------------------------------

- (NSMutableArray*)         db_getUserBookForUser:(NSString*) msisdn;
- (NSMutableArray*)         db_getUserBookMarkedRemoveForUser:(NSString*) msisdn;
- (NSMutableArray*)         db_getAllUserBookForUser:(NSString*) msisdn;
- (NSMutableArray*)         db_doGetUserBookList:(NSString*) sql;
- (DBUserBook*)             db_getUserBookForUserByBookId:(NSString*) msisdn wBookId:(int) bookId;
- (BOOL)                    db_insertUserBook:(DBUserBook*) userbook;
- (BOOL)                    db_updateUserBook:(DBUserBook*) userbook;
- (BOOL)                    db_deleteUserBook:(DBUserBook*) userbook;
- (int)                     db_countUserBookForBookId:(int)bookid;

//-----------------------    FLAT   ------------------------------------

- (NSString*)               db_getValueForKey:(NSString*) key;
- (NSMutableDictionary*)    db_getAllKeyValue;
- (BOOL)                    db_insertKeyValue:(NSString*) skey wValue:(NSString*) svalue;
- (BOOL)                    db_updateKeyValue:(NSString*) skey wValue:(NSString*) svalue;

//--------------- Book Mark
- (NSMutableDictionary*) db_getListOfBookMarks;
- (BOOL)                    db_updateBookMarks:(BookMark*) bookMark;
- (BOOL)                    db_insertBookMarks:(BookMark*) bookMark;
- (BOOL)                    db_deleteBookMarks:(int) bookMarkid;
- (BookMark*)                   db_getBookMarkById:(int) bookMarkId;


//-----------------------    PLAYLIST   ------------------------------------

//-(int)              db_doLoadLastUpdate;
//-(NSMutableArray *) db_ForUILoadListOfPlaylists:(int)iType;
//-(SyncPackage*)     db_ForSyncLoadListOfPlaylists;
//
//-(int) db_OnSyncUpdateLastUpdate:(NSString*) lastupdate;
//
////Remove Playlist
//-(int) db_OnUIRemovePlaylist:(Playlist*) playlist;
//-(int) db_OnSyncRemovePlaylist:(Playlist*) playlist;
//
////Add Playlist
//-(int) db_OnUIInsertPlaylist:(Playlist*)aPlaylist;
//-(int) db_OnSyncInsertPlaylist:(Playlist*)aPlaylist;
//-(int) db_doInsertPlaylist:(Playlist*)aPlaylist withIsSync:(BOOL) isSync;
//
////Update Playlist
//-(int) db_OnUIUpdatePlaylist:(Playlist*)aPlaylist withDelSongs:(NSMutableArray*)delSongs withAddSongs:(NSMutableArray*)addSongs;
//-(int) db_OnSyncUpdatePlaylist:(Playlist*)aPlaylist withDelSongs:(NSMutableArray*)delSongs withAddSongs:(NSMutableArray*)addSongs;
//-(int) db_doUpdatePlaylist:(Playlist*)aPlaylist withDelSongs:(NSMutableArray*)delSongs withAddSongs:(NSMutableArray*)addSongs withIsSync:(int)isSync;
//
////Playlist utils
//-(int) db_doRemoveSongsFromPlaylist:(int)aPlaylistId withSongList:(NSMutableArray*)aSongList withIsSync:(BOOL) isSync;
//-(int) db_doUpdateRegistedIds:(NSMutableArray*) registeredIds;
//-(int) db_doClearState:(SyncPackage*) submitted;
//-(int) generateUniqueRandomIndex:(sqlite3*)aDatabase inTable:(NSString*)aTable;
//- (int) db_doUPdateSongOrder:(int)iPl song:(int)iSong order:(int)iOrder;
//
//// Favourite Playlist
//-(int) db_OnSyncInsertFavouritePlaylist:(Playlist*)aPlaylist;
//-(int) db_doInsertFavouritePlaylist:(Playlist*)aPlaylist withIsSync:(BOOL) isSync;
//-(NSMutableArray *) db_ForUILoadListOfFavouritedPlaylists;
//-(int) db_OnClearFavouritePlaylist;
//-(int) db_OnUIRemoveFavouritedPlaylist:(Playlist*) playlist;
//-(int) db_doRemoveSongsFromFavouritedPlaylist:(int)aPlaylistId withSongList:(NSMutableArray*)aSongList;
//-(int) db_doInsertSongsOfFavouritePlaylist:(Playlist*)aPlaylist;
//
////-----------------------    SONG   ------------------------------------
//
////Song
//-(Song*) db_doLoadSong:(int) songid;
//-(int) db_doInsertSong:(Song*)aSong;
//-(int) getNumberOfSongReference:(int)aSongId;
//
//-(int) generateUniqueRandomIndex:(sqlite3*)aDatabase inTable:(NSString*)aTable;
////-(int) updatePlaylistSyncedSongCount:(int)aPlaylistId withSyncedSongCount:(int)aCount;
//-(int) db_updateSongCachedFilePath:(int)songId withCachedFilePath:(NSString*)aPath;
////-(int) updatePlaylistShuffleMode:(int)aPlaylisId shuffleModeOn:(BOOL)aShuffleOn;
////-(int) updatePlaylistOfflineMode:(int)aPlaylisId offlineModeOn:(BOOL)aOfflineOn;
//-(NSString*) db_getSongCachedFilePath:(int)songId;
////-(int) getSyncSongCount:(Playlist*) playlist;
//-(int) db_clearSyncedSongs;
//-(int) db_clearSyncedSongsOfOnePlaylist:(Playlist*)pl;
////-(void) deleteLocalFiles:(NSMutableArray*)files;
//
////-----------------------    SYNC   ------------------------------------
//
//- (NSString*) db_getSongCachedFilePath:(int)songId;
//- (int) db_updateSongCachedFilePath:(int)songId withCachedFilePath:(NSString*)aPath;
//
////- (int) db_updatePlaylistSyncedSongCount:(int)aPlaylistId withSyncedSongCount:(int)aCount;
//
////Temporary commented out this function as it will never be used
////- (int) updatePlaylistShuffleMode:(int)aPlaylisId shuffleModeOn:(BOOL)aShuffleOn;
////- (int) updatePlaylistOfflineMode:(int)aPlaylisId offlineModeOn:(BOOL)aOfflineOn;
//
//- (int) db_clearSyncedSongs;
//- (int) db_clearSyncedSongsOfOnePlaylist:(Playlist*)pl;
//- (int) db_clearSyncedSongsFromList:(NSMutableArray*)list;
////- (void) deleteLocalFiles:(NSMutableArray*)files;
//
////-----------------------    USERS   ------------------------------------
//
//- (void) db_updateLoginInfo:(NSString*)username password:(NSString*)pass packageCode:(NSString*)code accountInfo:(NSString*)info rateCount:(int)rate account:(Account*)_account;
//- (NSDictionary*) db_getLoginInfo;
//
//-(NSMutableArray*) db_loadCachedSongs;
//-(NSMutableArray*) db_loadFavouriteSongs:(int)playlist_id;
//
//- (void) db_saveSongsToCache;
//- (NSMutableArray*) db_getSongsToCache;
//- (int) db_insertSongToCache:(Song*)song;
//- (int) db_deleteSongToCache:(int)songId;
//
//- (int) db_setMemoryLimit:(float)value;
//- (float) db_getMemoryLimit;
//
//- (int) db_resetDatabase;
//- (void)db_setRateCount:(int)count;
//
//- (int) db_updateUserInfo:(Account*)account password:(NSString*)pass;
//-(NSDictionary*) db_LoadPlaylistModified:(Playlist*)pl;
//- (int) db_updateMusicQuality:(int)value;
//- (int) db_getMusicQuality;

@end
