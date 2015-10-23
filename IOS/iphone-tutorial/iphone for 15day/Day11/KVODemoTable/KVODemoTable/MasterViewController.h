//
//  MasterViewController.h
//  KVODemoTable
//
//  Created by cuong minh on 4/4/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Dogs.h"
#import "Dog.h"

@interface MasterViewController : UITableViewController
@property (nonatomic, strong) Dogs* dogs;
@property (strong, nonatomic) IBOutlet UIBarButtonItem *barButtonItem;
@property (strong, nonatomic) IBOutlet UISegmentedControl *toogleObserver;
@property (strong, nonatomic) IBOutlet UIBarButtonItem *changeButton;

@end
