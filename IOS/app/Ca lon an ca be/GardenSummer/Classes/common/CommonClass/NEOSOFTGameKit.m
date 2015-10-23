//
//  AFLGameManager.m
//  DauTruongViet
//
//   Created by namnd on 7/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "NEOSOFTGameKit.h"
#import "Common.h"
#import "DBManager.h"
#import "ScoreInfo.h"

@implementation NEOSOFTGameKit

+ (BOOL) isGameCenterAvailable
{
	// check for presence of GKLocalPlayer API
	Class gcClass = (NSClassFromString(@"GKLocalPlayer"));
	
	// check if the device is running iOS 4.1 or later
	NSString *reqSysVer = @"4.1";
	NSString *currSysVer = [[UIDevice currentDevice] systemVersion];
	BOOL osVersionSupported = ([currSysVer compare:reqSysVer options:NSNumericSearch] != NSOrderedAscending);
	
	return (gcClass && osVersionSupported);
	
}


+ (void) authenticateLocalPlayer {
	
    [[GKLocalPlayer localPlayer] authenticateWithCompletionHandler:^(NSError *error) {
		if (error == nil)
		{
			// save current player Id;
			[Common sharedInstance].currentPlayerId = [GKLocalPlayer localPlayer].playerID;
			
			// Insert code here to handle a successful authentication.
			// NSLog(@"authenticateLocalPlayer : sign in successful");
			// check data I try commit data
			GKLocalPlayer *lp = [GKLocalPlayer localPlayer];
			DBManager *dbManager = [Common sharedInstance].dbManager;
			NSArray *listScoreOfUser = [dbManager getlistScoreForPlayerId:lp.playerID];
			
			for (int i = 0; i < [listScoreOfUser count]; i++) {
				ScoreInfo *scoreInfo = [listScoreOfUser objectAtIndex:i];
				// try recommit to the server
				[self reportScore:[scoreInfo getLongLongScore] forGameMode:scoreInfo.gameMode];
			}
			
			// compare with player in the database
			GKLeaderboard* leaderBoard= [[[GKLeaderboard alloc] init] autorelease];
			leaderBoard.category= LEADER_BOARD_CATEGORY;
			leaderBoard.timeScope= GKLeaderboardTimeScopeAllTime;
			leaderBoard.range= NSMakeRange(1, 1);
			
			[leaderBoard loadScoresWithCompletionHandler:  ^(NSArray *scores, NSError *error)
			 {
				 if(error == NULL)
				 {
					 int64_t personalBest= leaderBoard.localPlayerScore.value;
					 [Common sharedInstance].highestScore = personalBest;
					 // NSLog(@"get highest of user from leader board %ld", personalBest);
				 }
				 else
				 {
					 // NSLog(@"not get highest score of user from leader board --> assign 0");
					 [Common sharedInstance].highestScore = 0;
				 }
				 
			 }];
			
			
		}
		else
		{
			
			[Common sharedInstance].currentPlayerId = OFFLINE_PLAYER_ID;
			
			// Your application can process the error parameter to report the error to the player.
			// NSLog(@"Your application can process the error parameter to report the error to the player.");
//			for (id key in [error userInfo]) {
//				NSLog(@"key: %@, value: %@", key, [[error userInfo] objectForKey:key]);				
//			}
			[Common sharedInstance].highestScore = 0;
			
		}
	}];
}

+ (void) registerForAuthenticationNotification {
	
    NSNotificationCenter *nc = [NSNotificationCenter defaultCenter];
    [nc addObserver: self
           selector:@selector(authenticationChanged)
               name:GKPlayerAuthenticationDidChangeNotificationName
             object:nil];
	
}

+ (void) authenticationChanged {
	
    if ([GKLocalPlayer localPlayer].isAuthenticated) {
        // Insert code here to handle a successful authentication.
		//NSLog(@"Changed & successful authentication");
	} else {
		// Insert code here to clean up any outstanding Game Center-related classes.
		//NSLog(@"Clear data before");
	}
	
}

+ (void) retrieveFriends {
	
    GKLocalPlayer *lp = [GKLocalPlayer localPlayer];
	
    if (lp.authenticated) {
		
        [lp loadFriendsWithCompletionHandler:^(NSArray *friends, NSError *error) {
			if (error == nil)
			{
				// use the player identifiers to create player objects.
			}
			else
			{
				// report an error to the user.
			}
		}];
    }
	
}

+ (NSString*) getLocalPlayerUserId {

	GKLocalPlayer *lp = [GKLocalPlayer localPlayer];
	
	if (lp.authenticated) {
		return lp.playerID;
	} else {
		return nil;
	}

}

+ (NSString*) getLocalPlayerUserAlias {
	
	GKLocalPlayer *lp = [GKLocalPlayer localPlayer];
	
	if (lp.authenticated) {
		return lp.alias;
	} else {
		return nil;
	}
	
}

+ (void) loadPlayerData: (NSArray *) identifiers {
	
    [GKPlayer loadPlayersForIdentifiers:identifiers withCompletionHandler:^(NSArray *players, NSError *error) {
        if (error != nil)
        {
            // Handle the error.
        }
        if (players != nil)
        {
            // Process the array of GKPlayer objects.
        }
	}];
	
}

/**
 * Function : reportScore
 * - Commit score to the server : 
 *		+ Error : save (or update highest score ) to database.
 *		+ ( when authenticate successfull --> we are will commit score
 *
 */
// + (void) reportScore: (int64_t) score forCategory: (NSString*) category {

+ (void) reportScore: (int64_t) score forGameMode : (int) gameMode {
	
	// check game kit support
	if (![NEOSOFTGameKit isGameCenterAvailable]) {
		NSLog(@"doesn't support game kit --> not update");
		return;
	}
	
	// check score
	if ((score > MAX_SCORE) || (score == 0)) {
		// NSLog(@"SCORE = %i --> DON'T COMMIT TO SERVER", score);
		return;
	}
	
	// commit score
    GKScore *scoreReporter = [[[GKScore alloc] initWithCategory:[Common getGameModeCategoryName:gameMode]] autorelease];
    scoreReporter.value = score;
	
    [scoreReporter reportScoreWithCompletionHandler:^(NSError *error) {
		if (error != nil)
		{
            // handle the reporting error
			// NSLog(@"reportScore : Error --> save to database");
			GKLocalPlayer *lp = [GKLocalPlayer localPlayer];
			DBManager *dbManager = [Common sharedInstance].dbManager;
			if ([dbManager isExistPlayerId:lp.playerID andGameMode:gameMode]) {
				// NSLog(@"Still don't commit score to server");
				ScoreInfo *scoreInfo = [dbManager getScoreInfoForPlayerId:lp.playerID 
															  andGameMode:gameMode];
				if (score > [scoreInfo getLongLongScore]) {
					// NSLog(@"Update new high score");
					[scoreInfo updateHighestScore:score];
					[dbManager updateScoreInfo:scoreInfo];
				}
			} else {
				// NSLog(@"New record need insert to database");
				ScoreInfo *scoreInfo = [[ScoreInfo alloc] initWithPlayerId:lp.playerID 
																  gameMode:gameMode
														   andHighestScore:score];
				[dbManager insertScoreInfo:scoreInfo];
			}
			
        } else {
			// NSLog(@"reportScore : Success --> delete in db if success");
			GKLocalPlayer *lp = [GKLocalPlayer localPlayer];
			DBManager *dbManager = [Common sharedInstance].dbManager;
			if ([dbManager isExistPlayerId:lp.playerID andGameMode:gameMode]) {
				[dbManager deleteSocreInfoForPlayerId:lp.playerID 
										  andGameMode:gameMode];
			}
		}

    }];
	
}

+ (void) retrieveTopTenScores {
	
    GKLeaderboard *leaderboardRequest = [[GKLeaderboard alloc] init];
    if (leaderboardRequest != nil)
    {
        leaderboardRequest.playerScope = GKLeaderboardPlayerScopeGlobal;
        leaderboardRequest.timeScope = GKLeaderboardTimeScopeAllTime;
        leaderboardRequest.range = NSMakeRange(1,10);
        [leaderboardRequest loadScoresWithCompletionHandler: ^(NSArray *scores, NSError *error) {
            if (error != nil)
            {
                // handle the error.
            }
            if (scores != nil)
            {
                // process the score information.
            }
		}];
    }
}

@end
