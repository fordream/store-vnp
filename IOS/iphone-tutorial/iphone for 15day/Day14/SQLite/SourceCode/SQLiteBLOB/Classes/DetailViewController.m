//
//  DetailViewController.m
//  SQL
//
//  Created by iPhone SDK Articles on 10/26/08.
//  Copyright 2008 www.iPhoneSDKArticles.com.
//

#import "DetailViewController.h"
#import "Coffee.h"
#import "EditViewController.h"

@implementation DetailViewController

@synthesize coffeeObj;

- (void)setEditing:(BOOL)editing animated:(BOOL)animated {
    [super setEditing:editing animated:animated];
    [self.navigationItem setHidesBackButton:editing animated:animated];
	
    if (!editing)
        [coffeeObj saveAllData];
    
    [tableView reloadData];
}

// Implement viewDidLoad to do additional setup after loading the view.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	self.navigationItem.rightBarButtonItem = self.editButtonItem;
	
	//Configure the UIImagePickerController
	imagePickerView = [[UIImagePickerController alloc] init];
	imagePickerView.delegate = self;
}


- (void) viewWillAppear:(BOOL)animated {
	
	[super viewWillAppear:animated];
	
	self.title = coffeeObj.coffeeName;
	
	[tableView reloadData];
}

- (void)viewWillDisappear:(BOOL)animated {
	
	[tableView deselectRowAtIndexPath:selectedIndexPath animated:YES];
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
	
	[imagePickerView release];
	[evController release];
	[selectedIndexPath release];
	[tableView release];
	[coffeeObj release];
    [super dealloc];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tblView {
    return 3;
}


- (NSInteger)tableView:(UITableView *)tblView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tblView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithFrame:CGRectZero reuseIdentifier:CellIdentifier] autorelease];
    }
	
	switch(indexPath.section) {
		case 0:
			cell.text = coffeeObj.coffeeName;
			break;
		case 1:
			cell.text = [NSString stringWithFormat:@"%@", coffeeObj.price];
			break;
		case 2:
			cell.text = @"Change Image";
			if(coffeeObj.coffeeImage != nil)
				cell.image = coffeeObj.coffeeImage;
			break;
	}
	
	return cell;
}

- (NSString *)tableView:(UITableView *)tblView titleForHeaderInSection:(NSInteger)section {
	
	NSString *sectionName = nil;
	
	switch (section) {
		case 0:
			sectionName = [NSString stringWithFormat:@"Coffee Name"];
			break;
		case 1:
			sectionName = [NSString stringWithFormat:@"Price"];
			break;
		case 2:
			sectionName = [NSString stringWithFormat:@"Coffee Image"];
			break;
	}
	
	return sectionName;
}

- (UITableViewCellAccessoryType)tableView:(UITableView *)tv accessoryTypeForRowWithIndexPath:(NSIndexPath *)indexPath {
    // Show the disclosure indicator if editing.
    return (self.editing) ? UITableViewCellAccessoryDisclosureIndicator : UITableViewCellAccessoryNone;
}

- (NSIndexPath *)tableView:(UITableView *)tv willSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    // Only allow selection if editing.
    return (self.editing) ? indexPath : nil;
}

- (void)tableView:(UITableView *)tblView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	
	//Keep track of the row selected.
	selectedIndexPath = indexPath;
	
	if(evController == nil)
		evController = [[EditViewController alloc] initWithNibName:@"EditView" bundle:nil];
	
	//Find out which field is being edited.
	switch(indexPath.section)
	{
		case 0:
			evController.keyOfTheFieldToEdit = @"coffeeName";
			evController.editValue = coffeeObj.coffeeName;
			
			//Object being edited.
			evController.objectToEdit = coffeeObj;
			
			//Push the edit view controller on top of the stack.
			[self.navigationController pushViewController:evController animated:YES];
			break;
		case 1:
			evController.keyOfTheFieldToEdit = @"price";
			evController.editValue = [coffeeObj.price stringValue];
			
			//Object being edited.
			evController.objectToEdit = coffeeObj;
			
			//Push the edit view controller on top of the stack.
			[self.navigationController pushViewController:evController animated:YES];
			break;
		case 2:
			if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypePhotoLibrary])
				[self presentModalViewController:imagePickerView animated:YES];
			break;
	}
}

-(void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    UIImage *image=[info objectForKey:UIImagePickerControllerOriginalImage];
    coffeeObj.coffeeImage = image;
    //[coffeeObj saveAllData];
	[tableView reloadData];
	[picker dismissModalViewControllerAnimated:YES];
}

@end
