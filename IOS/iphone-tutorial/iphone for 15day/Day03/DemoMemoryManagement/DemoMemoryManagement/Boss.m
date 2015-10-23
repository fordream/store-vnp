//
//  Boss.m
//  DemoMemoryManagement
//
//  Created by cuong minh on 3/2/12.
//  Copyright (c) 2012 TechMaster.vn_. All rights reserved.
//

#import "Boss.h"

@implementation Boss
@synthesize delegate;

- (void) onBusinessTrip {
    if (!delegate) return;
    
    for (int i= 0; i < 5; i++ ) {        
        int action  =  random() %10;
        if (action < 5) {
            [delegate driveLexus];
        } else {
            if ([delegate respondsToSelector:@selector(takeBossWifeToCinema)]) {
                [delegate takeBossWifeToCinema];
            } else {
                [delegate signContract];
            }
        }      
    }
}
/*
 
 Try to commend below dealoc and run profile to see memory leaking
*/
- (void)dealloc
{
    [delegate release];
    delegate = nil;
    [super dealloc];
}


@end
