//
//  DBFriendlyObject.h
//  AnyBook
//
//  Created by Ngo Tri Hoai on 4/11/12.
//  Copyright (c) 2012 Vega Corp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DBFriendlyObject : NSObject {
    
    NSMutableArray* data;
}

- (int) count;

- (NSString*) getFieldAsString:(int) index;
- (int) getFieldAsNumber:(int) index;
- (BOOL) getFieldAsBoolean:(int) index;
- (NSObject*) getFieldAsObject:(int) index;

- (void) setField:(int) index wString:(NSString*) val;
- (void) setField:(int) index wInt:(int) val;
- (void) setField:(int) index wBoolean:(BOOL) val;
- (void) setField:(int) index wObject:(NSObject*) val;

- (void) copyInfoTo:(DBFriendlyObject*) peer;

@end
