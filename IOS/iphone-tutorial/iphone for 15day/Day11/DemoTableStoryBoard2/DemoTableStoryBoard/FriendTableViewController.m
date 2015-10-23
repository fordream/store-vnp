//
//  FriendTableViewController.m
//  DemoTableStoryBoard
//
//  Created by cuong minh on 6/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "FriendTableViewController.h"
#import "Friend.h"
#import "CustomViewCell.h"
#import "FriendViewController.h"
@interface FriendTableViewController ()

@end

@implementation FriendTableViewController
@synthesize data;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    NSString *dataPath = [[NSBundle mainBundle] pathForResource:@"data" ofType:@"plist"];
    NSArray *rawData = [NSArray arrayWithContentsOfFile:dataPath];
    self.data = [[NSMutableArray alloc] initWithCapacity: [rawData count]];
    for (NSDictionary *object in rawData) 
    {
        Friend *friend =[[Friend alloc] init: [object valueForKey:@"fullName"] 
        withNotes:[object valueForKey:@"notes"] andPhoto:[object valueForKey:@"photo"]];
        [self.data addObject: friend];
        
    }
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    self.data = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.data count];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *OddCellIdentifier = @"OddCell";
    static NSString *EvenCellIdentifier = @"EvenCell";
    CustomViewCell *cell;
    
    if (indexPath.row %2==0) {
        cell = (CustomViewCell *)[tableView dequeueReusableCellWithIdentifier:EvenCellIdentifier];
    } else {
        cell = (CustomViewCell *)[tableView dequeueReusableCellWithIdentifier:OddCellIdentifier];
    }
    
    Friend *aFriend = (Friend *) [self.data objectAtIndex:indexPath.row];
    cell.photo.image = aFriend.photo;
    cell.title.text = aFriend.fullName;
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}

#pragma mark - Context Menu for Row
- (BOOL) tableView:(UITableView *)tableView shouldShowMenuForRowAtIndexPath:(NSIndexPath *)indexPath{    
    /* Allow the context menu to be displayed on every cell */
    return YES;    
}
- (BOOL) tableView:(UITableView *)tableView
  canPerformAction:(SEL)action
 forRowAtIndexPath:(NSIndexPath *)indexPath 
        withSender:(id)sender{
    
    if (action == @selector(copy:)){
        return YES;
    } else {    
        return NO;
    }
}

- (void) tableView:(UITableView *)tableView 
     performAction:(SEL)action
 forRowAtIndexPath:(NSIndexPath *)indexPath 
        withSender:(id)sender{
    
    if (action == @selector(copy:)){        
        CustomViewCell *cell = (CustomViewCell *)[self.tableView cellForRowAtIndexPath:indexPath];
        UIPasteboard *pasteBoard = [UIPasteboard generalPasteboard];
        [pasteBoard setString:cell.title.text];
        NSLog(@"%@", cell.title.text);
        
    }
    
}


- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    NSIndexPath *selectedIndex = self.tableView.indexPathForSelectedRow;
    if ([segue.identifier isEqualToString:@"Odd"]) {
        FriendViewController *friendVC = (FriendViewController *) segue.destinationViewController;
        friendVC.friend = [data objectAtIndex: selectedIndex.row];        
        
    } else if ([segue.identifier isEqualToString:@"Even"]) {
        
    }
}
@end
