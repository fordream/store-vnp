//
//  DBFriendlyObject.m
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/11/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import "DBFriendlyObject.h"
#import "DBTable.h"

@interface DBFriendlyObject()

- (void) ensureIndex:(int) index;

@end


@implementation DBFriendlyObject

- (id) init
{
    self = [super init];
    if (self != nil) {
        data = [[NSMutableArray alloc] init];
    }
    return self;
}

-(void)dealloc 
{
    data = Nil;
}

- (int) count
{
    return [data count];
}

- (NSString*) getFieldAsString:(int) index
{
    if ((index >= 0) && ([data count] > index)) {
        NSObject* obj = [data objectAtIndex:index];
        if ([obj isKindOfClass:[NSString class]]) {
            return (NSString*) obj;
        }
    }
    return Nil;
}

- (int) getFieldAsNumber:(int) index
{
    if ((index >= 0) && ([data count] > index)) {
        NSObject* obj = [data objectAtIndex:index];
        if ([obj isKindOfClass:[NSNumber class]]) {
            return [((NSNumber*) obj) intValue];
        }
    }
    return 0;
}

- (BOOL) getFieldAsBoolean:(int) index
{
    if ((index >= 0) && ([data count] > index)) {
        NSObject* obj = [data objectAtIndex:index];
        if ([obj isKindOfClass:[NSNumber class]]) {
            return ([((NSNumber*) obj) intValue] == SQL_BOOL_TRUE);
        }
    }
    return NO;
}

- (NSObject*) getFieldAsObject:(int) index
{
    if ((index >= 0) && ([data count] > index)) {
        return [data objectAtIndex:index];
    }
    return Nil;
}

- (void) setField:(int) index wString:(NSString*) val
{
    [self ensureIndex:index];
    [data replaceObjectAtIndex:index withObject:val];
}

- (void) setField:(int) index wInt:(int) val
{
    [self ensureIndex:index];
    [data replaceObjectAtIndex:index withObject:[NSNumber numberWithInt:val]];    
}

- (void) setField:(int) index wBoolean:(BOOL) val
{
    [self ensureIndex:index];
    [data replaceObjectAtIndex:index withObject:[NSNumber numberWithInt:(val?SQL_BOOL_TRUE:SQL_BOOL_FALSE)]];
}

- (void) setField:(int) index wObject:(NSObject*) val
{
    [self ensureIndex:index];
    [data replaceObjectAtIndex:index withObject:val];
}

- (void) ensureIndex:(int) index
{
    while ([data count] < index + 1) {
        [data addObject:[NSNumber numberWithInt:0]];
    }
}

- (void) copyInfoTo:(DBFriendlyObject*) peer
{
    int total = [self count];
    for (int i = 0; i < total; i++) {
        [peer setField:i wObject:[self getFieldAsObject:i]];
    }
}

@end
