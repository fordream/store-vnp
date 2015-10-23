//
//  RootViewController.h
//  todo
//
//  Created by Brandon Trebitowski on 8/17/08.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TodoViewController.h"

@interface RootViewController : UITableViewController {
	TodoViewController *todoView;
}

@property(nonatomic,retain) TodoViewController *todoView;

@end
