//
//  ViewController.m
//  HelloWorld
//
//  Created by cuong minh on 3/22/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@implementation ViewController

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
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
- (IBAction)demoMyClass:(id)sender {
    MyClass *myClass = [[MyClass alloc] init];
    myClass->publicNumber = 12.0;
    //myClass->secretNumber = 34.0;
    //myClass->protectNumber = 30.0;
    [myClass logText];
    
    MySubClass *mySubClass = [[MySubClass alloc] init];
    [mySubClass logNumbers];
    
    [myClass release];
    [mySubClass release];
}
- (IBAction)demoMyClassSwim:(id)sender {
    MyClass *mySwimObject = [[MyClass alloc] init];
    [mySwimObject iCanSwim];
    
    
   
}

-(IBAction)onStart:(id)sender{
    // start new view controller
    MenuViewController *addController = [[[MenuViewController alloc]initWithNibName:@"MenuViewController" bundle:nil]autorelease ];
    // add to view
    // addController.delegate = self;
    UINavigationController *navigationController = [[UINavigationController alloc]initWithRootViewController:addController];
    
//    [self.view ];
   // [self presentViewController:navigationController animated:YES completion: nil];
    
}

@end
