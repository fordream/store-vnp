//
//  ViewController.m
//  UIPickerView
//
//  Created by Deepak Kumar on 31/07/09.
//

#import "ViewController.h"

@implementation ViewController
@synthesize mlabel;

- (void)viewDidLoad
{
	[super viewDidLoad];
	arrayNo = [[NSMutableArray alloc] init];
	[arrayNo addObject:@" 100 "];
	[arrayNo addObject:@" 200 "];
	[arrayNo addObject:@" 400 "];
	[arrayNo addObject:@" 600 "];
	[arrayNo addObject:@" 1000 "];
	
	[pickerView selectRow:1 inComponent:0 animated:NO];
    mlabel.text= [arrayNo objectAtIndex:[pickerView selectedRowInComponent:0]];	
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView;
{
	return 1;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
	mlabel.text=	[arrayNo objectAtIndex:row];
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component;
{
	return [arrayNo count];
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component;
{
	return [arrayNo objectAtIndex:row];
}


/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload
{
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}


@end
