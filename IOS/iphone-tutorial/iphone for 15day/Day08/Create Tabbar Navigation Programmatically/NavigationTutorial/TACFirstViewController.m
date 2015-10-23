//
//  TACFirstViewController.m
//  NavigationTutorial
//
//  Created by kent franks on 12/29/11.
//  Copyright (c) 2011 TheAppCodeBlog. All rights reserved.
//

#import "TACFirstViewController.h"
#import "TACThirdViewController.h"

@implementation TACFirstViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{    
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = @"First";
        self.tabBarItem.image = [UIImage imageNamed:@"first"];
        
        
        UIBarButtonItem *nextButton = [[UIBarButtonItem alloc] initWithTitle:@"Next" style:UIBarButtonItemStyleBordered target:self action:@selector(goToSubView)];
        self.navigationItem.rightBarButtonItem = nextButton;
    }
    return self;
}

- (void)goToSubView
{
//    TACThirdViewController *thirdView = [[TACThirdViewController alloc] initWithNibName:@"TACThirdViewController" bundle:[NSBundle mainBundle]];
    
    TACThirdViewController *thirdView = [[TACThirdViewController alloc] initWithNibName:@"TACThirdViewController" bundle:nil];

    
    [self.navigationController pushViewController:thirdView animated:YES];
    
}
							
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

@end
