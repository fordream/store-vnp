//
//  Animals.m
//  KVODemoTable
//
//  Created by cuong minh on 4/4/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Dogs.h"

@implementation Dogs
@synthesize dogArray;

- (id) init: (NSString *) fromFile
{
    if (self = [super init]) {
        NSString *pathname = [[NSBundle mainBundle]  pathForResource:fromFile ofType:nil inDirectory:@"/"];
        NSArray *rawData = [[NSString stringWithContentsOfFile:pathname encoding:NSUTF8StringEncoding error:nil] componentsSeparatedByString:@"\n"];
        if ([rawData count] > 0) {
            self.dogArray = [[NSMutableArray alloc] initWithCapacity: [rawData count]];
        
            for (NSString* row in rawData) {
                NSArray* fields = [row componentsSeparatedByString: @","];
                [self.dogArray addObject: 
                 [[Dog alloc] init: [fields objectAtIndex:0] 
                          withType: (DogType)[fields lastObject]]]; 
            }
        }
    }
    return self;
}

- (NSString *)description
{
    NSMutableString * tempString = [[NSMutableString alloc] init];
    for (Dog *dog in dogArray)
    {
        [tempString appendFormat: @"%@\n", dog.name];
    }
    return [NSString stringWithString:tempString];
}
#pragma mark - Collection Assesor Methods
- (NSUInteger)countOfDogArray
{
    return [self.dogArray count];
}
- (id)objectInDogArrayAtIndex:(NSUInteger)index
{
    return [self.dogArray objectAtIndex:index];
}
- (void)insertObject:(id)obj inDogArrayAtIndex:(NSUInteger)index
{
    [self.dogArray insertObject:obj atIndex:index];
}
- (void)removeObjectFromDogArrayAtIndex:(NSUInteger)index
{
    [self.dogArray removeObjectAtIndex:index];
}
- (void)replaceObjectInDogArrayAtIndex:(NSUInteger)index withObject:(id)obj
{
    [self.dogArray replaceObjectAtIndex:index withObject:obj];
}
@end
