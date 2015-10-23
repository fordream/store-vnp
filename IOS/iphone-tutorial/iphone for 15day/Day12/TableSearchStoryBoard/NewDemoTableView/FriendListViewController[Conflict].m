//
//  FirendListViewController.m
//  NewDemoTableView
//
//  Created by Ageha Ng on 7/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "FriendListViewController.h"
#import "Friend.h"
#import "CustomCell.h"
#import "WifiViewController.h"
#import "DetailsViewController.h"

#define CustomCellIdentifier1 @"CustomCell1"
#define CustomCellIdentifier2 @"CustomCell2"


@interface FriendListViewController ()

@end

@implementation FriendListViewController
@synthesize data = _data;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    NSString *dataPath = [[NSBundle mainBundle] pathForResource:@"data" ofType:@"plist"];
    NSArray *rawData = [NSArray arrayWithContentsOfFile:dataPath];
    _data = [[NSMutableArray alloc] initWithCapacity: [rawData count]];
    for (NSDictionary *object in rawData) {
        Friend *friend = [[Friend alloc] init:[object valueForKey:@"fullName"] withNotes:[object valueForKey:@"notes"] andPhoto:[object valueForKey:@"photo"]];
        [_data addObject: friend];
    }
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    _data = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    NSInteger result = 1;
    return result;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    NSInteger result = [_data count];
    return result;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    Friend *friend = [_data objectAtIndex:indexPath.row];
    
    NSInteger div = indexPath.row % 2;
    CustomCell *cell;
    if (div == 1) {
        cell = [tableView dequeueReusableCellWithIdentifier:CustomCellIdentifier1];
    } else {
        cell = [tableView dequeueReusableCellWithIdentifier:CustomCellIdentifier2];
    }
    cell.myLabel.text = friend.fullName;
    cell.myImageView.image = friend.photo;
    cell.myTextView.text = friend.notes;
    
    return cell;
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    NSIndexPath *indexPath = self.tableView.indexPathForSelectedRow;
    
    if ([segue.identifier isEqualToString:@"Odd"])
    {
        DetailsViewController *detailVC = (DetailsViewController *) segue.destinationViewController;
        Friend *friend = [_data objectAtIndex:indexPath.row];
        detailVC.friend = friend;
    } else if ([segue.identifier isEqualToString:@"Even"])
    {
        
    }
}


@end
