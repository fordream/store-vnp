//
//  DetailViewController.m
//  AdvancedTableView
//
//  Created by cuong minh on 3/12/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "DetailViewController.h"
#import "DataSource.h"
#import "Photo.h"
@implementation DetailViewController
@synthesize selectedIndex;
@synthesize imageView;
@synthesize datasource;
@synthesize isSearchResult;

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
    // Do any additional setup after loading the view from its nib.
}

- (void)viewWillAppear:(BOOL)animated
{
    /*NSLog(@"%d", self.selectedIndex);
    NSLog(@"%@", [self.datasource descriptionAtIndex: self.selectedIndex]); */
    Photo *photo; 
    if (isSearchResult) {
        photo = [self.datasource searchPhotoAtIndex:self.selectedIndex];
    } else {
        photo = [self.datasource photoAtIndex:self.selectedIndex];
    }
    self.imageView.image = photo.image;
    [super viewWillAppear:animated];
}

- (void)viewDidUnload
{
    [self setImageView:nil];
    [self setDatasource:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
