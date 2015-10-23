//
//  Custom3GroupCell.m
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/16/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Custom3GroupCell.h"

@implementation Custom3GroupCell
@synthesize myImageView;
@synthesize myLabelBold;
@synthesize myLabelShow;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
