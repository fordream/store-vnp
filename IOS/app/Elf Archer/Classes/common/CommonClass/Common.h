//
//  Common.h
//  BlankProject
//
//   Created by namnd on 7/27/11..
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class DBManager;

// function
#define DEGREES_TO_RADIANS(__ANGLE__) ((__ANGLE__) / 180.0 * M_PI)

// DEFINE CONSTANT
#define CURRENT_DB_VER			@"1.0"
#define SQL_FILE_NAME			@"database.sqlite"

#define CURRENT_DB_VERSION		@"CURRENT_DB_VERSION"
#define PREVIOUS_LEVEL			@"PREVIOUS_LEVEL"
#define SELECTED_TEMPLATE		@"SELECTED_TEMPLATE"
#define SELECTED_LANGUAGE		@"SELECTED_LANGUAGE"
#define SOUND_ENABLE			@"SOUND_ENABLE"
#define BACKGROUND_SOUND_ENABLE	@"BACKGROUND_SOUND_ENABLE"

#define CHECKSUM_KEY			@"%#%@^()asd1231412sA^%$m,.SDFasdf"
#define AES_KEY					@"/,./trydgfd#%$(*(@#&*$(@&*"

#define MAX_SCORE					45000
#define LEADER_BOARD_CATEGORY		@"pikachu.score"

#define LEADER_BOARD_EASY			@"pikachu.easy"
#define LEADER_BOARD_NORMAL			@"pikachu.normal"
#define LEADER_BOARD_HARD			@"pikachu.hard"
#define LEADER_BOARD_EXPERT			@"pikachu.expert"

#define OFFLINE_PLAYER_ID			@"OFFLINE"
#define SETTING_PLAYER_ALIAS		@"PLAYER_NAME"
#define CURRENT_LANGUAGE_TYPE		@"CURRENT_LANGUAGE_TYPE"

#define TOTAL_TIME_PLAY				400	// 400 seconds
#define START_ALERT_TIME			350	// 350 seconds
#define REWARD_DISPLAY_TIME			4	// 4 seconds


enum SOUND_TYPE {
	SOUND_ON			= 1,
	SOUND_OFF			= 0
};

enum DIRECT_PAGE_TYPE {
	DIRECT_NONE				= 0,
	DIRECT_TO_REGISTER		= 1,
	DIRECT_TO_PAYMENT		= 2
};

enum GAME_MODE_TYPE {
	GAME_MODE_EASY		= 0,
	GAME_MODE_NORMAL	= 1,
	GAME_MODE_HARD		= 2,
	GAME_MODE_EXPERT	= 3
};

enum TEMPLATE_IMAGE_KIND_TYPE{
	TEMPLATE_IMAGE_KIND_ONE		= 0,
	TEMPLATE_IMAGE_KIND_TRUE	= 1,
	TEMPLATE_IMAGE_KIND_THREE	= 2
};

enum LANGUAGE_TYPE {
	LANGUAGE_VIETNAM	= 0,
	LANGUAGE_ENGLISH	= 1
};

enum GAME_STATE {
	STATE_PLAYING		= 0,
	STATE_PAUSE			= 1,
	STATE_STOP			= 2,
	STATE_LEVEL_UP		= 3,
	STATE_WIN			= 4
};

@interface Common : NSObject {

	DBManager		*dbManager;
	BOOL			isSoundOn;
	BOOL			isBackgroundSoundOn;
	
	int64_t highestScore;
	NSString *currentPlayerId;
	NSString *restorePlayerId;
	
	int languageType;
	
}

@property (nonatomic, retain) DBManager	*dbManager;
@property BOOL			isSoundOn;
@property BOOL			isBackgroundSoundOn;
@property (nonatomic) int64_t highestScore;
@property (nonatomic, retain) NSString *currentPlayerId;
@property (nonatomic, retain) NSString *restorePlayerId;
@property int languageType;


// message from which our instance is obtained
+ (Common *)sharedInstance;

+ (NSString *) appFilePath;
+ (NSString *) dataBaseFilePath;
+ (NSString *) getFullPath : (NSString *) filename;
+ (void) initialDataBase;

// check that can user connect to network ?
+ (BOOL) isCanConnectToNetWork;

+ (void) showInvalidateDataMessage;
+ (NSString *) createMD5ForString : (NSString*) inputString;
+ (NSString *) createCheckSumForString : (NSString*) inputString;
+ (NSString *) getGameModeCategoryName : (int) gameMode;

+ (void) setViewToLandscape : (UIView*) viewObject;

@end
