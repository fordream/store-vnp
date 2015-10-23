//
//  MasterViewController.m
//  KVODemoTable
//
//  Created by cuong minh on 4/4/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MasterViewController.h"

@interface MasterViewController ()

@end

@implementation MasterViewController
@synthesize dogs;
@synthesize barButtonItem;
@synthesize toogleObserver;
@synthesize changeButton;

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
    self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    self.navigationItem.leftBarButtonItem = self.changeButton;
    
   // NSArray* toolBarArray = [[NSArray alloc] initWithObjects:self.monitorButton, self.unMonitorButton, nil];
    [self setToolbarItems: [[NSArray alloc] initWithObjects:self.barButtonItem, nil]];
    self.navigationController.toolbarHidden = NO;
    self.dogs = [[Dogs alloc] init: @"dogs"];
    
}

- (void)viewDidUnload {
    [self setDogs:nil];
    [self setBarButtonItem:nil];
    [self setToogleObserver:nil];
    [self setChangeButton:nil];
    [super viewDidUnload];
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
    return [self.dogs.dogArray count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *result = nil;
    static NSString *TableViewCellIdentifier = @"MyCells";
    result = [tableView dequeueReusableCellWithIdentifier:TableViewCellIdentifier];
    
    if (result == nil){
        result = [[UITableViewCell alloc] 
                  initWithStyle:UITableViewCellStyleDefault
                  reuseIdentifier:TableViewCellIdentifier];
    }
    
    //result.textLabel.text = [self.dogs dogAt:indexPath.row].name;
    Dog *dog = (Dog *)[self.dogs objectInDogArrayAtIndex:indexPath.row];
    result.textLabel.text = dog.name;
    return result;
}


// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}



// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [self.dogs removeObjectFromDogArrayAtIndex:indexPath.row];
        
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}

/*
 
 */


// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
    
}



// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}


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
- (IBAction)addRemoveObjserver:(id)sender {
    if (toogleObserver.selectedSegmentIndex == 0) {
        [self.dogs addObserver:self forKeyPath:@"dogArray" options:0 context:nil];
    } else {
        [self.dogs removeObserver:self forKeyPath:@"dogArray"];
    }
}
- (IBAction)changeData:(id)sender {
    Dog *newDog = [[Dog alloc] init:@"Phu Quoc" withType:Sporting];
    [self.dogs insertObject:newDog inDogArrayAtIndex:2];
}

- (void)observeValueForKeyPath:(NSString*)keyPath
                      ofObject:(id)object
                        change:(NSDictionary*)change
                       context:(void*)context
{
    if ([keyPath isEqualToString:@"dogArray"]) {
        /*
         NSKeyValueChangeSetting = 1,
         NSKeyValueChangeInsertion = 2,
         NSKeyValueChangeRemoval = 3,
         NSKeyValueChangeReplacement = 4
        */
        NSInteger changeType = [[change objectForKey:NSKeyValueChangeKindKey] integerValue]; 
        
        switch (changeType) {
            case NSKeyValueChangeSetting:                
                break;
            case NSKeyValueChangeInsertion:
                if ([change objectForKey:NSKeyValueChangeIndexesKey])
                {
                    NSIndexSet *indexSet = (NSIndexSet *)[change objectForKey:NSKeyValueChangeIndexesKey];
                    NSLog(@"%@", ((Dog *)[self.dogs objectInDogArrayAtIndex: [indexSet firstIndex]]).name);
                }
                [self.tableView reloadData];
                break;
            case NSKeyValueChangeRemoval:                
                //NSLog(@"Dog is removed \n%@", (Dogs *)object);
                if ([change objectForKey:NSKeyValueChangeIndexesKey])
                {
                    NSIndexSet *indexSet = (NSIndexSet *)[change objectForKey:NSKeyValueChangeIndexesKey];
                    NSLog(@"A dos is removed at index %d",[indexSet firstIndex]);
                }
                break;
            case NSKeyValueChangeReplacement:
                break;
            default:
                break;
        } 
        
        
    } else {
        [super observeValueForKeyPath:keyPath
                             ofObject:object
                               change:change
                              context:context];
    }
}

@end
