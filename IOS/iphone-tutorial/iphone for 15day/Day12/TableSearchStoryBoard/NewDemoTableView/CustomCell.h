//
//  CustomCell.h
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CustomCellDelegate.h"

@interface CustomCell : UITableViewCell

@property (strong) id <CustomCellDelegate> delegate;

@property (nonatomic, retain) IBOutlet UILabel *myLabel;

@property (nonatomic, retain) IBOutlet UIImageView *myImageView;

@property (nonatomic, retain) IBOutlet UITextView *myTextView;

@end
