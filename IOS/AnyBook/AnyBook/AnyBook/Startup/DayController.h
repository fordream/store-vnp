//
//  PagingController.h
//  AnyBook
//
//  Created by vega on 4/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RadioButton.h"
#import "CachedDataStore.h"
@interface DayController : UIViewController{
    IBOutlet RadioButton *btn10Px;
    IBOutlet RadioButton *btn20Px;
    IBOutlet RadioButton *btn30Px;
    CachedDataStore* dataStore;
    
}

-(void)config;
-(IBAction)onClick:(id)sender;

@end
