//
//  FriendManager.h
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/25/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface FriendManager : NSObject

@property (nonatomic, strong) NSMutableArray *friendArray;
@property (nonatomic, strong) NSMutableArray *resultFriendArray;

- (id) init: (NSString *) dataFile;
- (void)filterContentForSearchText: (NSString *)searchString;
@end
