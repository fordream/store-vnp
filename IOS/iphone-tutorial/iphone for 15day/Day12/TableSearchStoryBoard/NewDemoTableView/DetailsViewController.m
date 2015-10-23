//
//  DetailsViewController.m
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "DetailsViewController.h"

@interface DetailsViewController ()

@end

@implementation DetailsViewController
@synthesize myImageView = _myImageView;
@synthesize myLabelBold = _myLabelBold;
@synthesize myLabelShow = _myLabelShow;
@synthesize friend = _friend;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    _myImageView.image = _friend.photo;
}

- (void)viewDidUnload
{
    [self setMyImageView:nil];
    [self setMyLabelBold:nil];
    [self setMyLabelShow:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

-(void) onLongPress
{
    
}

@end
