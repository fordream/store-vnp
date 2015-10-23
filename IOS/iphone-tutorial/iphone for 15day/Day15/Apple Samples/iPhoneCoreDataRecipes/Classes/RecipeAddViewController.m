#import "RecipeAddViewController.h"
#import "Recipe.h"

@implementation RecipeAddViewController

@synthesize recipe;
@synthesize nameTextField;
@synthesize delegate;


- (void)viewDidLoad {
    
    // Configure the navigation bar
    self.navigationItem.title = @"Add Recipe";
    
    UIBarButtonItem *cancelButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Cancel" style:UIBarButtonItemStyleBordered target:self action:@selector(cancel)];
    self.navigationItem.leftBarButtonItem = cancelButtonItem;
    [cancelButtonItem release];
    
    UIBarButtonItem *saveButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Save" style:UIBarButtonItemStyleDone target:self action:@selector(save)];
    self.navigationItem.rightBarButtonItem = saveButtonItem;
    [saveButtonItem release];
	
	[nameTextField becomeFirstResponder];
}


- (void)viewDidUnload {
	self.nameTextField = nil;
	[super viewDidUnload];
}
	
	
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Support all orientations except upside-down
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}


- (BOOL)textFieldShouldReturn:(UITextField *)textField {
	if (textField == nameTextField) {
		[nameTextField resignFirstResponder];
		[self save];
	}
	return YES;
}


- (void)save {
    
    recipe.name = nameTextField.text;

	NSError *error = nil;
	if (![recipe.managedObjectContext save:&error]) {
		/*
		 Replace this implementation with code to handle the error appropriately.
		 
		 abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development. If it is not possible to recover from the error, display an alert panel that instructs the user to quit the application by pressing the Home button.
		 */
		NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
		abort();
	}		
    
	[self.delegate recipeAddViewController:self didAddRecipe:recipe];
}


- (void)cancel {
	
	[recipe.managedObjectContext deleteObject:recipe];

	NSError *error = nil;
	if (![recipe.managedObjectContext save:&error]) {
		/*
		 Replace this implementation with code to handle the error appropriately.
		 
		 abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development. If it is not possible to recover from the error, display an alert panel that instructs the user to quit the application by pressing the Home button.
		 */
		NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
		abort();
	}		

    [self.delegate recipeAddViewController:self didAddRecipe:nil];
}


- (void)dealloc {
    [recipe release];    
    [nameTextField release];    
    [super dealloc];
}

@end
