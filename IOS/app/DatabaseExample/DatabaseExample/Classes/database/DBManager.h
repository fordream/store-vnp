//
//  DBManager.h
//  BlankProject
//
//   Created by truongvv on 10/14/11.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "sqlite3.h"
#define SQL_FILE_NAME			@"database.sqlite"

@interface DBManager : NSObject {
	NSMutableArray *arrBoard;
	
	//InfoOther *infoOther;
	
@private
	sqlite3 *db;
}

+ (void) initialDataBase;

- (BOOL) openDB;
- (int) getMaxlevel;


@end
