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
@synthesize friendManager = _friendManager;
@synthesize longPressGesture = _longPressGesture;

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
    _friendManager = [[FriendManager alloc] init:@"data"];
}

- (void)viewDidUnload
{
    [self setLongPressGesture:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    _friendManager = nil;
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
    NSInteger result = 0;
    if (tableView != self.searchDisplayController.searchResultsTableView) {
        result = [_friendManager.friendArray count];
    } else {
        result = [_friendManager.resultFriendArray count];
    }
    
    return result;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    Friend *friend = [[Friend alloc]init];
    
    if (tableView != self.searchDisplayController.searchResultsTableView) {
        friend = [_friendManager.friendArray objectAtIndex:indexPath.row];
    } else {
        friend = [_friendManager.resultFriendArray objectAtIndex:indexPath.row];
    }
    
    static NSString *CellIdentifier = @"Cell";
    NSInteger div = indexPath.row % 2;
    CustomCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if (tableView != self.searchDisplayController.searchResultsTableView) {
        if (div == 1) {
            cell = [tableView dequeueReusableCellWithIdentifier:CustomCellIdentifier1];
        } else {
            cell = [tableView dequeueReusableCellWithIdentifier:CustomCellIdentifier2];
        }
    } else {
        cell = [[CustomCell alloc] 
                initWithStyle:UITableViewCellStyleSubtitle 
                reuseIdentifier:CellIdentifier];
    }
    
    //UILongPressGestureRecognizer *longPessGes = [[UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(onLongPress)];
    [_longPressGesture addTarget:self action:@selector(onCellLongPress:)];
    [cell addGestureRecognizer:_longPressGesture];
    if (tableView != self.searchDisplayController.searchResultsTableView) {
        cell.myLabel.text = friend.fullName;
        cell.myImageView.image = friend.photo;
        cell.myTextView.text = friend.notes;
    } else {
        cell.imageView.image = friend.photo;
        cell.textLabel.text = friend.fullName;
    }
    
    return cell;
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    NSIndexPath *indexPath = self.tableView.indexPathForSelectedRow;
    
    if ([segue.identifier isEqualToString:@"Odd"])
    {
        DetailsViewController *detailVC = (DetailsViewController *) segue.destinationViewController;
        Friend *friend = [_friendManager.friendArray objectAtIndex:indexPath.row];
        detailVC.friend = friend;
    } else if ([segue.identifier isEqualToString:@"Even"])
    {
        
    }
}

-(void)onCellLongPress : (UILongPressGestureRecognizer *) recognier
{
    if (recognier.state == UIGestureRecognizerStateBegan) {
        NSLog(@"AAA");
    }
}

-(BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    return YES;
}

// return YES to reload table. called when search string/option changes. convenience methods on top UISearchBar delegate methods
- (BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchString:(NSString *)searchString
{
    [_friendManager filterContentForSearchText:searchString];
    return YES;
}


@end
