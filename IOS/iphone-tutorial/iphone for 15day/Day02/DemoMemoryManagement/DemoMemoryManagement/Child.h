//
//  Child.h
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/2/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import <Foundation/Foundation.h>
@class Parent;
@interface Child : NSObject
@property (nonatomic, strong) Parent *parent;
@end
