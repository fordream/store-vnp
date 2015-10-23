//
//  MasterViewController.m
//  DemoNavigation
//
//  Created by cuong minh on 6/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MasterViewController.h"

@interface MasterViewController ()

@end

#define cakeTag 1
#define cocktailTag 2
#define soupTag 3
#define sushiTag 4
#define beachTag 5
@implementation MasterViewController
@synthesize detailViewController;

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
    self.title = @"Food Lovers";
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    self.detailViewController = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
- (IBAction)handleButtonTap:(id)sender {
    UIButton *button = (UIButton *) sender;
    if (!self.detailViewController) {
        self.detailViewController = [[DetailViewController alloc] initWithNibName:@"DetailViewController" bundle:nil];
    }
    switch (button.tag) {
        case cakeTag:            
            break;
        case cocktailTag:
            break;
        case soupTag:
            break;
        case sushiTag:
            break;
        default:
            break;
    }
    
    [self.navigationController pushViewController:self.detailViewController animated:YES];
}

@end
