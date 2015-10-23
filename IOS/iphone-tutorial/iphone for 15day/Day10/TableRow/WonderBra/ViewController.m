//
//  ViewController.m
//  WonderBra
//
//  Created by cuong minh on 3/12/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@implementation ViewController
@synthesize mainTableView;
@synthesize demoSwitch;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"Sexy Bra";	
}

- (void)viewDidUnload
{
    [self setMainTableView:nil];
    [self setDemoSwitch:nil];
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

#pragma mark - UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    NSInteger result = 3;
    return result;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    NSInteger result = 0;
    switch (section){
        case 0:{
            result = 3;
            break;
        }
        case 1:{
            result = 5;
            break;
        }
        case 2:{
            result = 8;
            break;
        }
    }
   
    return result;
}

- (UITableViewCell *)  tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    UITableViewCell *result = nil;
    static NSString *TableViewCellIdentifier = @"MyCells";
    result = [tableView dequeueReusableCellWithIdentifier:TableViewCellIdentifier];
        
    if (result == nil){
       result = [[UITableViewCell alloc] 
                      initWithStyle:UITableViewCellStyleDefault
                      reuseIdentifier:TableViewCellIdentifier];
    }
        
    result.textLabel.text = [NSString stringWithFormat:@"Section %ld, Cell %ld",
                                 (long)indexPath.section,
                                 (long)indexPath.row];
 
    NSLog(@"Section %d, Row %d", indexPath.section, indexPath.row);
    if (indexPath.section ==0) {
        switch (indexPath.row) {
            case 0:
                result.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                break;
            case 1:
                result.accessoryType = UITableViewCellAccessoryDetailDisclosureButton;
                break;
            case 2:
                result.accessoryType = UITableViewCellAccessoryCheckmark;
                break;
            default:
                result.accessoryType = UITableViewCellAccessoryNone;
                break;
        }
    } else if (indexPath.section == 1) {
        switch (indexPath.row) {
            case 0:
                result.accessoryView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"wrench"]];
                break;
            case 1:
                result.accessoryView = self.demoSwitch;
                break;            
            default:
                result.accessoryView = nil;
                break;
        } 
    } else {
        result.accessoryType = UITableViewCellAccessoryNone;
        result.accessoryView = nil;
    }
    return result;
    
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    //NSLog(@"Section %d, Row %d", indexPath.section, indexPath.row);
}

@end
