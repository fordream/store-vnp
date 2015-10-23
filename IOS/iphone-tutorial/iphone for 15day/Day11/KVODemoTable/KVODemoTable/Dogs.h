//
//  Animals.h
//  KVODemoTable
//
//  Created by cuong minh on 4/4/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Dog.h"
@interface Dogs : NSObject
@property (nonatomic, strong) NSMutableArray *dogArray;

- (id) init: (NSString *) fromFile;

- (NSUInteger)countOfDogArray;
- (id)objectInDogArrayAtIndex:(NSUInteger)index;
- (void)insertObject:(id)obj inDogArrayAtIndex:(NSUInteger)index;
- (void)removeObjectFromDogArrayAtIndex:(NSUInteger)index;
- (void)replaceObjectInDogArrayAtIndex:(NSUInteger)index withObject:(id)obj;
- (NSString *)description;
/*
 - (NSUInteger)countOf<key>;
 - (id)objectIn<key>AtIndex:(NSUInteger)index;
 - (void)insertObject:(id)obj in<key>AtIndex:(NSUInteger)index;
 - (void)removeObjectFrom<key>AtIndex:(NSUInteger)index;
 - (void)replaceObjectIn<key>AtIndex:(NSUInteger)index withObject:(id)obj;
*/
@end
