//
//  FirstViewController.m
//  BasicCoreData
//
//  Created by cuong minh on 3/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.

#import "FirstViewController.h"
#import "AppDelegate.h"
@implementation FirstViewController
@synthesize managedObjectContext;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
       
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView
{
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
    AppDelegate *appDelegate = (AppDelegate*) [[UIApplication sharedApplication] delegate];
    self.managedObjectContext = appDelegate.managedObjectContext;
    NSLog(@"%@", self.managedObjectContext);
}


- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
#pragma mark - CRUD Person model
- (void) createPersonwithFirstName: (NSString *) firstName lastName: (NSString *) lastName DOB: (NSString *) DOBString{
    Person *person = (Person *)[NSEntityDescription insertNewObjectForEntityForName:@"Person" 
                                                             inManagedObjectContext:self.managedObjectContext];
	if (person) {
        [person setFirstName:firstName];
        [person setLastName:lastName];
        NSString *dateStr = DOBString;
        
        // Convert string to date object
        NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
        [dateFormat setDateFormat:@"yyyyMMdd"];
        NSDate *dob = [dateFormat dateFromString:dateStr];
        
        [person setDateOfBirth: dob];
        NSError *savingError = nil;
        if ([self.managedObjectContext save:&savingError]){
            NSLog(@"Successfully saved the context.");
        } else {
            NSLog(@"Failed to save the context. Error = %@", savingError);
        }
    } else {    
        NSLog(@"Failed to create the new person.");
    }
    
}
- (void)fetchRecords {
	
	// Define our table/entity to use
	NSEntityDescription *entity = [NSEntityDescription entityForName:@"Person" inManagedObjectContext:managedObjectContext];
	
	// Setup the fetch request
	NSFetchRequest *request = [[NSFetchRequest alloc] init];
	[request setEntity:entity];
	
	// Define how we will sort the records
	NSSortDescriptor *sortDescriptor = [[NSSortDescriptor alloc] initWithKey:@"firstName" ascending:NO];
	NSArray *sortDescriptors = [NSArray arrayWithObject:sortDescriptor];
	
	[request setSortDescriptors:sortDescriptors];
    
	// Fetch the records and handle an error
	NSError *error;
	NSMutableArray *mutableFetchResults = [[managedObjectContext executeFetchRequest:request error:&error] mutableCopy];
	
	if (!mutableFetchResults) {
		// Handle the error.
		// This is a serious error and should advise the user to restart the application
	} else {
        for (Person *person in mutableFetchResults) {
            NSLog(@"%@ %@", person.fullName, person.dateOfBirth);
            
        }
    }
    
}
- (void) deleteAllRecords {
    // Define our table/entity to use
	NSEntityDescription *entity = [NSEntityDescription entityForName:@"Person" inManagedObjectContext:managedObjectContext];
	
	// Setup the fetch request
	NSFetchRequest *request = [[NSFetchRequest alloc] init];
	[request setEntity:entity];
	
	// Fetch the records and handle an error
	NSError *error;
	NSMutableArray *mutableFetchResults = [[managedObjectContext executeFetchRequest:request error:&error] mutableCopy];
	
	if (!mutableFetchResults) {
		// Handle the error.
		// This is a serious error and should advise the user to restart the application
	} else {
        for (Person *person in mutableFetchResults) {
           [self.managedObjectContext deleteObject:person];            
        }
    }

}
#pragma mark - Handle Button Events


- (IBAction)createData:(id)sender {
    [self createPersonwithFirstName:@"Cuong" lastName:@"Trinh" DOB:@"19751127"];
    [self createPersonwithFirstName:@"Tuan" lastName:@"Le" DOB:@"19811022"];
    [self createPersonwithFirstName:@"Duong" lastName:@"Tran" DOB:@"19820530"];
    [self createPersonwithFirstName:@"Lan" lastName:@"Dao" DOB:@"19730530"];
    [self createPersonwithFirstName:@"Anh" lastName:@"Do" DOB:@"19900520"];
    [self createPersonwithFirstName:@"Long" lastName:@"Pham" DOB:@"1980720"];
    [self createPersonwithFirstName:@"Vo" lastName:@"Lai" DOB:@"19800720"];
}

- (IBAction)deleteAllPeople:(id)sender {
    [self deleteAllRecords];
}

- (IBAction)queryData:(id)sender {
    [self fetchRecords];
}


@end
