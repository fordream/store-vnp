//
//  NavigationControlleriPhone.m
//  DemoNavigationOneXib
//
//  Created by cuong minh on 2/25/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "NavControlleriPhone.h"
#import "AnotherViewController.h"
#import "MoreViewController.h"

@implementation NavControlleriPhone
@synthesize anotherViewController;
@synthesize moreViewController;
@synthesize txtValue, numValue;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
      
    }
    return self;
}


- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView
{
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
    txtValue = @"Love my life";
    numValue = 0.5;
    
}


- (void)viewDidUnload
{
    [self setAnotherViewController:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Handle Navigation requests
- (IBAction)gotoAnotherView:(id)sender {    
   	    
    if (! self.anotherViewController) {
        self.anotherViewController = [[AnotherViewController alloc] initWithNibName:@"AnotherViewController" bundle:nil];
    }
    
    [self pushViewController: self.anotherViewController animated:TRUE];

}

- (void) gotoMoreView: (id)sender {
    if (! self.moreViewController) {
        self.moreViewController = [[MoreViewController alloc] initWithNibName:@"MoreViewController" bundle:nil];        
    }
    [self pushViewController: self.moreViewController animated:TRUE];
    
}

@end
