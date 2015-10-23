//
//  Person.h
//  DemoTableSearch
//
//  Created by cuong minh on 4/19/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Person : NSObject
@property (nonatomic, strong) NSString* fullName;
@property (nonatomic, assign) int age;
@property (nonatomic, strong) UIImage* photo;

- (id) init: (NSString*)_fullName age: (int) _age withPhoto: (NSString*) _photo;
@end
