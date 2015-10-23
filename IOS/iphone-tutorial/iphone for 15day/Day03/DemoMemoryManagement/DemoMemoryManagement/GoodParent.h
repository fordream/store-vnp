//
//  GoodParent.h
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GoodChild.h"
@interface GoodParent : NSObject
@property (nonatomic, assign) __weak GoodChild* goodChild;

- (void) giveChildAToy;
- (void) receiveThanksFromChild;
@end
