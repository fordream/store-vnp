//
//  DetailViewController.h
//  MasterDetailCoreData
//
//  Created by cuong minh on 5/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Stuff.h"
@interface DetailViewController : UIViewController <UISplitViewControllerDelegate>

//@property (strong, nonatomic) id detailItem;
@property (strong, nonatomic) Stuff * detailItem;

@property (strong, nonatomic) IBOutlet UILabel *detailDescriptionLabel;

@end
