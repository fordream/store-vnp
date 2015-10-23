//
//  Person.h
//  KVODemo
//
//  Created by cuong minh on 4/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Person : NSObject
@property (nonatomic, strong) NSString *firstName;
@property (nonatomic, strong) NSString *lastName;
@property (nonatomic, readonly, strong) NSString *fullName;

- (id) init;
+ (NSString*) randomName;
@end
