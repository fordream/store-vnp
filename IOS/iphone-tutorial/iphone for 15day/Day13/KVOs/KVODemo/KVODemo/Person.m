//
//  Person.m
//  KVODemo
//
//  Created by cuong minh on 4/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Person.h"

@implementation Person
@synthesize firstName, lastName;
@synthesize fullName;

static NSArray *randomArray;
+ (NSString*) randomName {
    int index = rand() % [randomArray count];
    return [randomArray objectAtIndex: index];
}

- (id) init {
    if (self = [super init]) {
        randomArray = [[NSArray alloc] initWithObjects:@"Alex", @"Tom", @"Jack", @"Helena", @"Wonder", @"Hilton",
                       @"Steve", @"Jimmy", @"Trung", @"Hoa", @"Lan", @"Dzung", @"Tien", @"Toan", @"Loan", @"Long", nil];
        
        self.firstName = [Person randomName];
        self.lastName =  [Person randomName];
    }
    return self;
}




- (NSString *)fullName 
{
    return [NSString stringWithFormat : @"%@ %@", firstName, lastName];
}

+ (NSSet *)keyPathsForValuesAffectingValueForKey:(NSString *)key
{
    NSSet *keyPaths = [super keyPathsForValuesAffectingValueForKey:key];
    if ([key isEqualToString:@"fullName"])
    {
        NSSet *affectingKeys = [NSSet setWithObjects:@"lastName", @"firstName", nil];
        keyPaths = [keyPaths setByAddingObjectsFromSet:affectingKeys];
    }
    return  keyPaths;
    
}
@end
