//
//  FriendManager.m
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/25/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "FriendManager.h"
#import "Friend.h"

@implementation FriendManager

@synthesize friendArray;
@synthesize resultFriendArray;

- (id) init: (NSString *) dataFile
{
    if (self = [super init]) {
        NSString *dataPath = [[NSBundle mainBundle] pathForResource:dataFile ofType:@"plist"];
        NSArray *rawData = [NSArray arrayWithContentsOfFile:dataPath];
        friendArray = [[NSMutableArray alloc] initWithCapacity: [rawData count]];
        resultFriendArray = [[NSMutableArray alloc] initWithCapacity: [rawData count]];
        for (NSDictionary *object in rawData) {
            Friend *friend = [[Friend alloc] init:[object valueForKey:@"fullName"] withNotes:[object valueForKey:@"notes"] andPhoto:[object valueForKey:@"photo"]];
            [friendArray addObject: friend];
        }

    }
    
    return self;
}

- (void)dealloc
{
    friendArray = nil;
    resultFriendArray = nil;
}

- (void)filterContentForSearchText: (NSString *)searchString
{
    [self.resultFriendArray removeAllObjects]; //Clear all previous search result
    for (Friend *friend in self.friendArray)
	{
        NSComparisonResult result = [friend.fullName compare:searchString options:(NSCaseInsensitiveSearch|NSDiacriticInsensitiveSearch) range:NSMakeRange(0, [searchString length])];
        if (result == NSOrderedSame)
		{
            [self.resultFriendArray addObject:friend];
            
        }
		
	}
}

@end
