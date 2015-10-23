//
//  ViewController.m
//  LoadMultipleViews
//
//  Created by cuong minh on 6/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize mainView;
@synthesize anotherView;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void) viewWillAppear:(BOOL)animated
{
    NSLog(@"viewWillAppear");
}
- (void) viewDidAppear:(BOOL)animated
{
     NSLog(@"viewDidAppear");
}
- (void) viewWillDisappear:(BOOL)animated
{
    NSLog(@"viewWillDisappear");
}
- (void) viewDidDisappear:(BOOL)animated
{
    NSLog(@"viewDidDisappear");
}

- (void) viewWillUnload 
{
    NSLog(@"viewWillUnload");
}
- (void)viewDidUnload
{
    [super viewDidUnload];
    self.mainView = nil;
    self.anotherView = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}
- (IBAction)loadAnotherView:(id)sender {
    if (!self.anotherView) {
        NSArray* nibArray = [[NSBundle mainBundle] loadNibNamed:@"anotherView" owner:self options:nil];
        
        self.anotherView = [nibArray objectAtIndex:0];
    }
    self.mainView = self.view;
    self.view = self.anotherView;
    
}
- (IBAction)backToMainView:(id)sender {
    self.view = self.mainView;
}

@end
