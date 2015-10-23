//
//  AFLGameManager.h
//  DauTruongViet
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import  <GameKit/GameKit.h>

@interface NEOSOFTGameKit : NSObject {

}

+ (void) authenticateLocalPlayer;
+ (void) registerForAuthenticationNotification;
+ (void) authenticationChanged;

+ (void) retrieveFriends;
+ (void) loadPlayerData: (NSArray *) identifiers;
// + (void) reportScore: (int64_t) score forCategory: (NSString*) category;
+ (void) reportScore: (int64_t) score forGameMode : (int) gameMode;
+ (void) retrieveTopTenScores;

+ (NSString*) getLocalPlayerUserId;
+ (NSString*) getLocalPlayerUserAlias;

+ (BOOL) isGameCenterAvailable;

@end
