//
//  RootViewController.m
//  todo
//
//  Created by Brandon Trebitowski on 8/17/08.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//

#import "RootViewController.h"
#import "todoAppDelegate.h"
#import "Todo.h"
#import "TodoCell.h"

@implementation RootViewController
@synthesize todoView;

- (void)viewDidLoad {
	self.title = @"Todo Items";
	self.navigationItem.leftBarButtonItem = self.editButtonItem;

	UIBarButtonItem * btn = [[UIBarButtonItem alloc] initWithTitle:@"Add" 
															 style:UIBarButtonItemStyleBordered 
															 target:self action:@selector(addTodo:)];
	self.navigationItem.rightBarButtonItem = btn;
}

- (void) addTodo:(id)sender {
	todoAppDelegate *appDelegate = (todoAppDelegate *)[[UIApplication sharedApplication] delegate];
	
	if(self.todoView == nil) {
		TodoViewController *viewController = [[TodoViewController alloc] 
											  initWithNibName:@"TodoViewController" bundle:[NSBundle mainBundle]];
		self.todoView = viewController;
		[viewController release];
	}
	
	Todo *todo = [appDelegate addTodo];
	[self.navigationController pushViewController:self.todoView animated:YES];
	self.todoView.todo = todo;
	self.todoView.title = todo.text;
	[self.todoView.todoText setText:todo.text];	
	
}

- (void)setEditing:(BOOL)editing animated:(BOOL)animated {
    // Updates the appearance of the Edit|Done button as necessary.
    [super setEditing:editing animated:animated];
    [self.tableView setEditing:editing animated:YES];
    // Disable the add button while editing.
    if (editing) {
        self.navigationItem.rightBarButtonItem.enabled = NO;
    } else {
        self.navigationItem.rightBarButtonItem.enabled = YES;
    }
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
	return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	todoAppDelegate *appDelegate = (todoAppDelegate *)[[UIApplication sharedApplication] delegate];
    return appDelegate.todos.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	
	static NSString *MyIdentifier = @"MyIdentifier";
	
	TodoCell *cell = (TodoCell *)[tableView dequeueReusableCellWithIdentifier:MyIdentifier];
	if (cell == nil) {
		cell = [[[TodoCell alloc] initWithFrame:CGRectZero reuseIdentifier:MyIdentifier] autorelease];
	}
	
	todoAppDelegate *appDelegate = (todoAppDelegate *)[[UIApplication sharedApplication] delegate];
	Todo *td = [appDelegate.todos objectAtIndex:indexPath.row];
	
	[cell setTodo:td];
	
	// Set up the cell
	return cell;
}


 - (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	 todoAppDelegate *appDelegate = (todoAppDelegate *)[[UIApplication sharedApplication] delegate];
	 Todo *todo = (Todo *)[appDelegate.todos objectAtIndex:indexPath.row];
	 
	 if(self.todoView == nil) {
		 TodoViewController *viewController = [[TodoViewController alloc] 
											   initWithNibName:@"TodoViewController" bundle:[NSBundle mainBundle]];
		 self.todoView = viewController;
		 [viewController release];
	 }
	 
	 [self.navigationController pushViewController:self.todoView animated:YES];
	 self.todoView.todo = todo;
	 self.todoView.title = todo.text;
	 [self.todoView.todoText setText:todo.text];
	 
	 NSInteger priority = todo.priority - 1;
	 if(priority > 2 || priority < 0) {
		 priority = 1;
	 }
	 priority = 2 - priority;
	 
	 [self.todoView.todoPriority setSelectedSegmentIndex:priority];
	 
	 if(todo.status == 1) {
		 [self.todoView.todoButton setTitle:@"Mark As In Progress" forState:UIControlStateNormal];
		 [self.todoView.todoButton setTitle:@"Mark As In Progress" forState:UIControlStateHighlighted];
		 [self.todoView.todoStatus setText:@"Complete"];
	 } else {
		 [self.todoView.todoButton setTitle:@"Mark As Complete" forState:UIControlStateNormal];
		 [self.todoView.todoButton setTitle:@"Mark As Complete" forState:UIControlStateHighlighted];
		  [self.todoView.todoStatus setText:@"In Progress"];
	 }
}



// Override if you support editing the list
- (void)tableView:(UITableView *)tableView commitEditingStyle:
	(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
	
	todoAppDelegate *appDelegate = (todoAppDelegate *)[[UIApplication sharedApplication] delegate];
	Todo *todo = (Todo *)[appDelegate.todos objectAtIndex:indexPath.row];
	
	if (editingStyle == UITableViewCellEditingStyleDelete) {
		[appDelegate removeTodo:todo];
		// Delete the row from the data source
		[tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:YES];
	}	
}



/*
 Override if you support conditional editing of the list
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
	// Return NO if you do not want the specified item to be editable.
	return YES;
}
*/


/*
 Override if you support rearranging the list
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/


/*
 Override if you support conditional rearranging of the list
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
	// Return NO if you do not want the item to be re-orderable.
	return YES;
}
 */ 

- (void)viewWillAppear:(BOOL)animated {
	[self.tableView reloadData];
	[super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated {
	[super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated {
}

- (void)viewDidDisappear:(BOOL)animated {
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	// Return YES for supported orientations
	return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


- (void)didReceiveMemoryWarning {
	[super didReceiveMemoryWarning]; // Releases the view if it doesn't have a superview
	// Release anything that's not essential, such as cached data
}


- (void)dealloc {
	[super dealloc];
}


@end

