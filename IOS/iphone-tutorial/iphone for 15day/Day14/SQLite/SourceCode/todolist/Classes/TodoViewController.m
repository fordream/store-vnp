//
//  TodoViewController.m
//  todo
//
//  Created by Brandon Trebitowski on 9/6/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

#import "TodoViewController.h"


@implementation TodoViewController

@synthesize todoText,todoPriority,todoStatus,todoButton,todo;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
	if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
		// Initialization code
	}
	return self;
}

- (IBAction) updateStatus:(id) sender {
	if(self.todo.status == 0) {
		[self.todoButton setTitle:@"Mark As In Progress" forState:UIControlStateNormal];
		[self.todoButton setTitle:@"Mark As In Progress" forState:UIControlStateHighlighted];
		[self.todoStatus setText:@"Complete"];
		[self.todo updateStatus:1];
	} else {
		[self.todoButton setTitle:@"Mark As Complete" forState:UIControlStateNormal];
		[self.todoButton setTitle:@"Mark As Complete" forState:UIControlStateHighlighted];
		[self.todoStatus setText:@"In Progress"];
		[self.todo updateStatus:0];
	}
}

- (IBAction) updatePriority:(id)sender {
	int priority = [self.todoPriority selectedSegmentIndex];
	[self.todo updatePriority:(2-priority+1)];
}

- (IBAction) updateText:(id) sender {
	self.todo.text = self.todoText.text;
}

/*
 Implement loadView if you want to create a view hierarchy programmatically
- (void)loadView {
}
 */

/*
 If you need to do additional setup after loading the view, override viewDidLoad.
- (void)viewDidLoad {
}
 */


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
