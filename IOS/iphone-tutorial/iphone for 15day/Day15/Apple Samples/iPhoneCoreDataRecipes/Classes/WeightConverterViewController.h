
@class MetricPickerController;
@class ImperialPickerController;

@interface WeightConverterViewController : UIViewController {
	
    UIView *pickerViewContainer;
    MetricPickerController *metricPickerController;
    UIView *metricPickerViewContainer;
	
    UIView *imperialPickerViewContainer;
    ImperialPickerController *imperialPickerController;
    
	UISegmentedControl *segmentedControl;
    
	NSUInteger selectedUnit;
}

@property (nonatomic, retain) IBOutlet UIView *pickerViewContainer;

@property (nonatomic, retain) IBOutlet MetricPickerController *metricPickerController;
@property (nonatomic, retain) IBOutlet UIView *metricPickerViewContainer;

@property (nonatomic, retain) IBOutlet ImperialPickerController *imperialPickerController;
@property (nonatomic, retain) IBOutlet UIView *imperialPickerViewContainer;

@property (nonatomic, retain) IBOutlet UISegmentedControl *segmentedControl;

- (IBAction)toggleUnit;

@end

