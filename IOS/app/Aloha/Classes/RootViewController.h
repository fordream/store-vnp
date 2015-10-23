//
//  RootViewController.h
//  Aloha
//
//  Created by Truong Vuong on 9/11/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RootViewController : UITableViewController {
	NSMutableArray *listOfItems;
	UITableView * tableView;
}
@property (nonatomic, retain) IBOutlet UITableView * tableView;
@end
