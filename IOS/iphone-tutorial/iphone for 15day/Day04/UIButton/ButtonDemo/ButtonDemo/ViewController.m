//
//  ViewController.m
//  ButtonDemo
//
//  Created by cuong minh on 3/5/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@implementation ViewController
@synthesize buttonTargetAction;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	[buttonTargetAction addTarget: self action:@selector(handleTargetActionClick:) forControlEvents: UIControlEventTouchUpInside];
    NSLog(@"%@", self.view.superview);
}
     
- (void) handleTargetActionClick : (id) sender
{
    if ([sender isKindOfClass:[UIButton class]]) {
        UIButton *button = (UIButton *) sender;
        NSString *result = [NSString stringWithFormat: @"User clicks on button '%@'", [button titleForState: UIControlStateNormal]];
        UIAlertView *alertView = [[UIAlertView alloc]
                                  initWithTitle:@"Button Demo"
                                  message:result
                                  delegate: self
                                  cancelButtonTitle:@"Cancel"
                                  otherButtonTitles:@"Ok", nil];
        [alertView show];   
    }   
}

- (void)viewDidUnload
{
    [self setButtonTargetAction:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}
- (IBAction)aboutButtonClick:(id)sender {
    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"Develop by Cuong"
                                                             delegate:self cancelButtonTitle:nil destructiveButtonTitle:@"OK" otherButtonTitles:nil];
	actionSheet.actionSheetStyle = UIActionSheetStyleDefault;
   
	[actionSheet showInView:self.view];	// show from our table view (pops up in the middle of the table)
}
- (IBAction)buttonCalendarClick {
    UIAlertView *alertView = [[UIAlertView alloc]
                              initWithTitle:@"Display calendar"
                              message:@"button Calendar is clicked"
                              delegate: self
                              cancelButtonTitle:@"Cancel"
                              otherButtonTitles:@"Ok", nil];
    [alertView show];   

}
- (IBAction)buttonCalendarTouchDown:(id)sender {
}

@end
