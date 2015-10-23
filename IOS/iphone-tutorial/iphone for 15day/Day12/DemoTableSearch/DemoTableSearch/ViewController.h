//
//  ViewController.h
//  DemoTableSearch
//
//  Created by cuong minh on 4/19/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Person.h"
#import "PeopleManager.h"
@interface ViewController : UITableViewController <UISearchDisplayDelegate, UISearchBarDelegate>

@property (nonatomic, strong) PeopleManager *peopleManager;

@end
