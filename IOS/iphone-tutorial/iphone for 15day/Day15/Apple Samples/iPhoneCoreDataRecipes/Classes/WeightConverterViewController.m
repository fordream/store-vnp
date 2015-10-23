
#import "WeightConverterViewController.h"

#import "MetricPickerController.h"
#import "ImperialPickerController.h"

@implementation WeightConverterViewController

@synthesize pickerViewContainer;
@synthesize imperialPickerController;
@synthesize imperialPickerViewContainer;
@synthesize metricPickerController;
@synthesize metricPickerViewContainer;
@synthesize segmentedControl;


#define METRIC_INDEX 0
#define IMPERIAL_INDEX 1

- (void)viewDidLoad {
	[super viewDidLoad];
	
	self.navigationItem.title = @"Weight";
	
	// Set the currently-selected unit for self and the segmented control
	selectedUnit = METRIC_INDEX;
	segmentedControl.selectedSegmentIndex = selectedUnit;	
	
	[self toggleUnit];
}


- (void)viewDidUnload {    
	self.pickerViewContainer = nil;
	
	self.metricPickerController = nil;
	self.metricPickerViewContainer = nil;
	
	self.imperialPickerController = nil;
	self.imperialPickerViewContainer = nil;
	
	self.segmentedControl = nil;

	[super viewDidUnload];
}


- (IBAction)toggleUnit {
	
	/*
	 When the user changes the selection in the segmented control, set the appropriate picker as the current subview of the picker container view (and remove the previous one).
	 */
	selectedUnit = [segmentedControl selectedSegmentIndex];
	if (selectedUnit == IMPERIAL_INDEX) {
		[metricPickerViewContainer removeFromSuperview];
		[pickerViewContainer addSubview:imperialPickerViewContainer];
		[imperialPickerController updateLabel];
	} else {
		[imperialPickerViewContainer removeFromSuperview];
		[pickerViewContainer addSubview:metricPickerViewContainer];	
		[metricPickerController updateLabel];
	}
}


- (void)dealloc {
	[pickerViewContainer release];
    
    [imperialPickerController release];
	[imperialPickerViewContainer release];
    
    [metricPickerController release];
	[metricPickerViewContainer release];
    
	[segmentedControl release];
	[super dealloc];
}

@end

