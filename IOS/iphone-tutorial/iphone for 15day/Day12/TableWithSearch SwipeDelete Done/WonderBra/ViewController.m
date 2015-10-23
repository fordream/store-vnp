//
//  ViewController.m
//  WonderBra
//
//  Created by cuong minh on 3/12/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import "DataSource.h"
#import "CustomViewCell.h"
#import "DetailViewController.h"
#import "MainNavigationController.h"
#import "Photo.h"
#import "CustomViewCell.h"

@implementation ViewController
@synthesize mainTableView;
@synthesize tmpCell;
@synthesize cellNib;
@synthesize datasource;
@synthesize detailViewController;
@synthesize addButton;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"My Photos";	
    //self.datasource = [[DataSource alloc] init];
    MainNavigationController *mainNavigationController = (MainNavigationController *) self.navigationController;
    self.datasource = mainNavigationController.datasource;
    self.cellNib = [UINib nibWithNibName:@"TableCell" bundle:nil];
    self.mainTableView.rowHeight = 80;
    
}

- (void)viewDidUnload
{
    [self setMainTableView:nil];
    [self setCellNib:nil];
    [self setTmpCell:nil];
    [self setDetailViewController:nil];
    
    [self setAddButton:nil];
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


- (void) setEditing:(BOOL)editing 
           animated:(BOOL)animated{
    
    [super setEditing:editing
             animated:animated];
    
    [self.mainTableView setEditing:editing
                          animated:animated];
    
    
}
#pragma mark - UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    NSInteger result = 1;
    return result;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
	if (tableView == self.searchDisplayController.searchResultsTableView)
	{
       return [self.datasource searchCount];
    }
	else
	{
       return [self.datasource count];
    }
  
}

- (UITableViewCell *)  tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    static NSString *CellIdentifier =  @"CustomCell"; 
    Photo *photo = nil;
	if (tableView == self.searchDisplayController.searchResultsTableView)
	{        
        photo = [self.datasource searchPhotoAtIndex:indexPath.row];        
    } else {        
        photo = [self.datasource photoAtIndex:indexPath.row];      
    }    
    
    CustomViewCell *cell = (CustomViewCell *)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
	
    if (cell == nil)
    {
        [self.cellNib instantiateWithOwner:self options:nil];
		cell = tmpCell;
		self.tmpCell = nil;
    }

    
    cell.thumbImage.image =  photo.image;
    cell.title.text = photo.description;
    cell.tag = indexPath.row;
    NSLog(@"cell.tag = %d", cell.tag);
   /* UILongPressGestureRecognizer *longPressRecognizer = [[UILongPressGestureRecognizer alloc] 
                                                         initWithTarget:self
                                                         action:@selector(pressToEdit:)];
    [cell addGestureRecognizer:longPressRecognizer];*/
    UISwipeGestureRecognizer * horizontalSwipeRecognizer = [[UISwipeGestureRecognizer alloc]
                                                   initWithTarget:self
                                                   action:@selector(swipeToEdit:)];
    horizontalSwipeRecognizer.direction = UISwipeGestureRecognizerDirectionRight;
    [cell addGestureRecognizer:horizontalSwipeRecognizer];

    return cell;
}

- (void) swipeToEdit: (UISwipeGestureRecognizer *) recognizer 
{
   // NSLog(@"Press %@", recognizer.v);
    CustomViewCell * customViewCell = (CustomViewCell *) recognizer.view;
    NSLog(@"current cell.tag = %d",customViewCell.tag);
    currentIndex = customViewCell.tag;
    
    self.addButton.center = CGPointMake(285, customViewCell.bounds.size.height/2);
    [self.addButton addTarget:self action:@selector(deleteObject) forControlEvents:UIControlEventTouchUpInside];
   
    [customViewCell addSubview:self.addButton];
}

- (void) deleteObject
{
    [self.datasource removePhotoAtIndex:currentIndex];
    [mainTableView reloadData];
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (! self.detailViewController) {
        self.detailViewController = [[DetailViewController alloc] initWithNibName:@"DetailViewController" bundle:nil]; 
        self.detailViewController.datasource = self.datasource;
    }
    
    detailViewController.selectedIndex = indexPath.row;
    
    detailViewController.isSearchResult = (tableView == self.searchDisplayController.searchResultsTableView) ? YES : NO;
    
    [self.navigationController pushViewController: detailViewController animated:YES];
    
}

/* this does not work with custom cell
- (UITableViewCellEditingStyle)tableView:(UITableView *)tableView
           editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    UITableViewCellEditingStyle result = UITableViewCellEditingStyleNone;
    
    if (tableView != self.searchDisplayController.searchResultsTableView){
        result = UITableViewCellEditingStyleDelete;
    }    
    return result;    
}
 */

#pragma mark UISearchDisplayController Delegate Methods

- (BOOL)searchDisplayController:(UISearchDisplayController *)controller 
shouldReloadTableForSearchString:(NSString *)searchString
{
    [self.datasource filterContentForSearchText:searchString];
        
    // Return YES to cause the search result table view to be reloaded.
    return YES;
}
- (void)searchDisplayController:(UISearchDisplayController *)controller 
  didLoadSearchResultsTableView:(UITableView *)tableView
{
    tableView.rowHeight = 80;
    tableView.backgroundColor = [UIColor blackColor];
}

@end
