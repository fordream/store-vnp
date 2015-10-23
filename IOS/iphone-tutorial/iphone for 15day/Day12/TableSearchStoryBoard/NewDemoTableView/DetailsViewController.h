//
//  DetailsViewController.h
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Friend.h"
#import "CustomCellDelegate.h"

@interface DetailsViewController : UIViewController <CustomCellDelegate>

@property (weak, nonatomic) IBOutlet UIImageView *myImageView;
@property (weak, nonatomic) IBOutlet UILabel *myLabelBold;
@property (weak, nonatomic) IBOutlet UILabel *myLabelShow;
@property (nonatomic, strong) Friend * friend;

@end
