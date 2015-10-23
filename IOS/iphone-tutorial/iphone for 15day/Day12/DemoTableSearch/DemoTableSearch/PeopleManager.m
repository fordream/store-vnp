//
//  PeopleManager.m
//  DemoTableSearch
//
//  Created by cuong minh on 4/19/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PeopleManager.h"
#import "Person.h"

@implementation PeopleManager
@synthesize peopleArray;
@synthesize resultPeopleArray;

-(id) init: (NSString *) dataFile
{
    if (self = [super init])
    {
        
        // Load the data.
        NSString *dataPath = [[NSBundle mainBundle] pathForResource:dataFile ofType:@"plist"];
        NSArray* rawArray = [NSArray arrayWithContentsOfFile:dataPath];
        
        
        self.peopleArray = [[NSMutableArray alloc] initWithCapacity:[rawArray count]];
        self.resultPeopleArray = [[NSMutableArray alloc] initWithCapacity:[rawArray count]];
        for (NSDictionary *dic in rawArray) {
            Person *person = [[Person alloc] init: [dic valueForKey:@"fullName"]
                                              age:[[dic valueForKey:@"age"] intValue]
                                        withPhoto:[dic valueForKey:@"photo"]];
            //NSLog(@"%@", person);
            [self.peopleArray addObject:person];
        }
        
    }
   
    return self;
}

-(void) dealloc {
    peopleArray = nil;
    resultPeopleArray = nil;
}

-(void) filterContentForSearchText: (NSString *) searchString
{
    [self.resultPeopleArray removeAllObjects]; //Clear all previous search result
    for (Person *person in self.peopleArray)
	{
        NSComparisonResult result = [person.fullName compare:searchString options:(NSCaseInsensitiveSearch|NSDiacriticInsensitiveSearch) range:NSMakeRange(0, [searchString length])];
        if (result == NSOrderedSame)
		{
            [self.resultPeopleArray addObject:person];

        }
		
	}
}
@end
