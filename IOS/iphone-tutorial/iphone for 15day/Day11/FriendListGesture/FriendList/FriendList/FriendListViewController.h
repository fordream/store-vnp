//
//  FriendListViewController.h
//  FriendList
//
//  Created by Techmaster on 7/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CustomCellDelegate.h"

@interface FriendListViewController : UITableViewController <CustomCellDelegate>
@property (nonatomic, strong) NSMutableArray *data;
@end
