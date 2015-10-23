//
//  GoodChild.h
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
//#import "GoodParent.h"   in GoodParent.h we already import GoodChild.h, so in GoodChild.h we cannot import GoodParent.h. It cause cyclic importing
//We must use @class GoodParent instead
@class GoodParent;

@interface GoodChild : NSObject
@property (nonatomic, assign) __weak GoodParent *goodParent;

//What different between assign and strong
//strong increases retain count +1, assign does not
@property (nonatomic, strong) UIImage *photo;

- (void) sayThanksToParent;
- (void) receiveToyFromParent;
@end
