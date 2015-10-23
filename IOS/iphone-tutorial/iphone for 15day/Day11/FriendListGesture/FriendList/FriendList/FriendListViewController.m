//
//  FriendListViewController.m
//  FriendList
//
//  Created by Techmaster on 7/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "FriendListViewController.h"
#import "Friend.h"
#import "CustomCell.h"
@interface FriendListViewController ()

@end

@implementation FriendListViewController
@synthesize data = _data;

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
    return [_data count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *OddCellIdentifier = @"Odd";
    static NSString *EvenCellIdentifier = @"Even";
    CustomCell *cell;
    
    if (indexPath.row%2==0) {
        cell = (CustomCell *) [tableView dequeueReusableCellWithIdentifier:EvenCellIdentifier];
    } else {
        cell = (CustomCell *) [tableView dequeueReusableCellWithIdentifier:OddCellIdentifier];
    }
    
    
    UILongPressGestureRecognizer *longPressGesture = [[UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(onCellLongPress:)];
    [cell addGestureRecognizer:longPressGesture];
    
    Friend *friend = (Friend *) [_data objectAtIndex:indexPath.row];
    cell.photo.image = friend.photo;
    cell.title.text = friend.fullName;
    cell.description.text = friend.notes;
   
    return cell;
}

-(void) onCellLongPress: (UILongPressGestureRecognizer *) recognizer
{
    if (recognizer.state == UIGestureRecognizerStateBegan) {
        CustomCell *cell = (CustomCell *) recognizer.view;
        NSLog(@"%@", cell.title.text);

    }
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


- (void) onLongPress
{
    
}
@end
