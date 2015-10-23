//
//  DisplayController.h
//  AnyBook
//
//  Created by vega on 4/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CachedDataStore.h"

@interface DisplayController : UIViewController{
    IBOutlet UISwitch *uiSwith;
    IBOutlet UIButton *btnGotoHome;
    IBOutlet UISlider *uiSlider;
    CachedDataStore* dataStore;
}

-(IBAction)onCLick:(id)sender;

@end
