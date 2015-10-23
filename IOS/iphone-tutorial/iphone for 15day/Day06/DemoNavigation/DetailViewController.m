//
//  DetailViewController.m
//  DemoNavigation
//
//  Created by cuong minh on 6/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "DetailViewController.h"


@interface DetailViewController ()

@end

@implementation DetailViewController
@synthesize moreDetailViewController;

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
    self.title = @"Đồ Uống";
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    self.moreDetailViewController= nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
- (IBAction)handleButtonTap:(id)sender {
    if(!self.moreDetailViewController) {
        self.moreDetailViewController = [[MoreDetailViewController alloc] initWithNibName:@"MoreDetailViewController" bundle:nil];
    }
    [self.navigationController pushViewController:self.moreDetailViewController animated:YES];
}

@end
