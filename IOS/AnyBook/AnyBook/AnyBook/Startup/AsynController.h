//
//  AsynController.h
//  AnyBook
//
//  Created by vega on 4/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RadioButton.h"
#import "CachedDataStore.h"
@interface AsynController : UIViewController{
    IBOutlet RadioButton *btnNormal;
    IBOutlet RadioButton *btnFull;
    CachedDataStore* dataStore;

}
-(IBAction)onClick:(id)sender;
@end
