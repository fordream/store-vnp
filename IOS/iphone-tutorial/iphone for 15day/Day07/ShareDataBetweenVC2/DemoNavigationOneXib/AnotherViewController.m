//
//  AnotherViewController.m
//  DemoNavigationOneXib
//
//  Created by cuong minh on 2/25/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "AnotherViewController.h"
#import "NavControlleriPhone.h"
#import "MoreViewController.h"

@implementation AnotherViewController
@synthesize moreViewController = _moreViewController;
@synthesize myText;
@synthesize mySlider;
@synthesize text, value;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
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

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Add our custom add button as the nav bar's custom right view
	UIBarButtonItem *addButton = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"More View", @"")
                                                                  style:UIBarButtonItemStyleBordered
                                                                 target:self
                                                                 action:@selector(showMoreView:)];
	self.navigationItem.rightBarButtonItem = addButton;
}

- (void)viewWillAppear:(BOOL)animated {
   [super viewWillAppear:animated];
   self.myText.text = self.text;
   self.mySlider.value = self.value;
}

- (void)viewDidUnload
{
    [self setMyText:nil];
    [self setMySlider:nil];
    [super viewDidUnload];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
#pragma mark - Handle control events
- (IBAction)TextField_DidEndOnExit:(id)sender {
}

- (void) showMoreView : (id) sender {
    if (!_moreViewController){
        _moreViewController = [[MoreViewController alloc] initWithNibName:@"MoreViewController" bundle:nil];
    }

    _moreViewController.text = self.myText.text;
    _moreViewController.value = self.mySlider.value;
    _moreViewController.anotherViewController = self;
    [self.navigationController pushViewController: _moreViewController animated:YES];
}


@end
