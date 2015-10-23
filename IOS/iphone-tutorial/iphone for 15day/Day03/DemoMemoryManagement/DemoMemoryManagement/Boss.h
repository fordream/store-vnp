//
//  Boss.h
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/2/12.
//  Copyright (c) 2012 TechMaster.vn. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BossMethods.h"

@interface Boss : NSObject

@property (strong) id <BossMethods> delegate;

- (void) onBusinessTrip;

@end
