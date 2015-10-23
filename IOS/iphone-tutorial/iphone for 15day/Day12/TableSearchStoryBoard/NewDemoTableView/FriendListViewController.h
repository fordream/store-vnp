//
//  FirendListViewController.h
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

@class CustomImageCell;
@class CustomCell;

#import <UIKit/UIKit.h>
#import "FriendManager.h"

@interface FriendListViewController : UITableViewController<UISearchBarDelegate, UISearchDisplayDelegate>

@property (strong, nonatomic) FriendManager *friendManager;

@property (strong, nonatomic) IBOutlet UILongPressGestureRecognizer *longPressGesture;

@end
