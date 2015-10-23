//
//  BestCoresController.h
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SoundCommon.h"
#import "Setting1.h"
#import "DBManager.h"
#import "Score.h"
#import "Point1.h"

@interface BestCoresController : UIViewController <UITableViewDelegate, UITableViewDataSource>  {
	UITableView *tableView;
	NSArray *colorNames;
}
@property (nonatomic, retain) IBOutlet UITableView *tableView;
@property (nonatomic, retain) NSArray *colorNames;
//@property (nonatomic, retain) IBOutlet UIButton *btnRight;
-(IBAction)onClick :(id)sender;

@end
