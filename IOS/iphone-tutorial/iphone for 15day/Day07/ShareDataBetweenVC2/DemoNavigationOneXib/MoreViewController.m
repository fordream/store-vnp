//
//  MoreViewController.m
//  DemoNavigationOneXib
//
//  Created by cuong minh on 2/25/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MoreViewController.h"
#import "NavControlleriPhone.h"
@implementation MoreViewController
@synthesize myText;
@synthesize mySlider;
@synthesize text, value;
@synthesize anotherViewController=_anotherViewController;


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
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.myText.text = self.text;
    self.mySlider.value = self.value;
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
  // Cai nay chay khong duoc  
    /*
    _anotherViewController.myText.text = myText.text;
    _anotherViewController.mySlider.value = mySlider.value;
    */
    
    _anotherViewController.text = myText.text;
    _anotherViewController.value = mySlider.value;
    }

- (void)viewDidUnload
{
    [self setMyText:nil];
    [self setMySlider:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
#pragma mark - Handle Events
- (BOOL) textFieldShouldReturn: (UITextField *) theTextField {
    [theTextField resignFirstResponder];    
    return  YES;
}

@end
