//
//  ViewController.m
//  DemoUISwitch
//
//  Created by cuong minh on 3/29/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@implementation ViewController
@synthesize aSwitch;
@synthesize switchLabel;
@synthesize bulbImage;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	[self switchON_OFF: self.aSwitch];
}

- (void)viewDidUnload
{
    [self setSwitchLabel:nil];
    [self setBulbImage:nil];
    [self setASwitch:nil];
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
#pragma mark - Handle Control Events
- (void) switchON_OFF: (UISwitch *)sender
{
    if (sender.isOn){
        bulbImage.highlighted = YES;
        self.view.backgroundColor = [UIColor grayColor];
        
    } else {  
        bulbImage.highlighted = NO;
        self.view.backgroundColor = [UIColor blackColor];
        switchLabel.textColor = [UIColor yellowColor];
    }
}
- (IBAction)handleSwitchValueChange:(id)sender {
    if ([sender isKindOfClass:[UISwitch class]]){
        UISwitch *mySwitch = (UISwitch *)sender;
        [self switchON_OFF: mySwitch];
    }
    
}

@end
