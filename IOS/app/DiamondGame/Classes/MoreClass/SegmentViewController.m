/*
     File: SegmentViewController.m 
 Abstract: The view controller for hosting the UISegmentedControl features of this sample. 
  Version: 2.9 
  
  */

#import "SegmentViewController.h"
//#import "Constants.h"
#define kLeftMargin				20.0
#define kTopMargin				20.0
#define kRightMargin			20.0
#define kTweenMargin			10.0

#define kTextFieldHeight		30.0

#define kSegmentedControlHeight 40.0
#define kLabelHeight			20.0

@implementation SegmentViewController

+ (UILabel *)labelWithFrame:(CGRect)frame title:(NSString *)title
{
    UILabel *label = [[[UILabel alloc] initWithFrame:frame] autorelease];
    
	label.textAlignment = UITextAlignmentLeft;
    label.text = title;
    label.font = [UIFont boldSystemFontOfSize:17.0];
    label.textColor = [UIColor colorWithRed:76.0/255.0 green:86.0/255.0 blue:108.0/255.0 alpha:1.0];
    label.backgroundColor = [UIColor clearColor];
	
    return label;
}

- (void)segmentAction:(id)sender
{
	//NSLog(@"segmentAction: selected segment = %d", [sender selectedSegmentIndex]);
}

- (void)createControls
{
	NSArray *segmentTextContent = [NSArray arrayWithObjects: @"Check", @"Search", @"Tools", nil];
	
	// label
	CGFloat yPlacement = kTopMargin;
	CGRect frame = CGRectMake(kLeftMargin, yPlacement, self.view.bounds.size.width - (kRightMargin * 2.0), kLabelHeight);
	[self.view addSubview:[SegmentViewController labelWithFrame:frame title:@"UISegmentedControl:"]];
	
#pragma mark -
#pragma mark UISegmentedControl
	UISegmentedControl *segmentedControl = [[UISegmentedControl alloc] initWithItems:
												[NSArray arrayWithObjects:
													[UIImage imageNamed:@"segment_check.png"],
													[UIImage imageNamed:@"segment_search.png"],
													[UIImage imageNamed:@"segment_tools.png"],
													nil]];
	yPlacement += kTweenMargin + kLabelHeight;
	frame = CGRectMake(	kLeftMargin,
						yPlacement,
						self.view.bounds.size.width - (kRightMargin * 2.0),
						kSegmentedControlHeight);
	segmentedControl.frame = frame;
	[segmentedControl addTarget:self action:@selector(segmentAction:) forControlEvents:UIControlEventValueChanged];
	segmentedControl.segmentedControlStyle = UISegmentedControlStylePlain;
	segmentedControl.selectedSegmentIndex = 1;	
	[self.view addSubview:segmentedControl];
	[segmentedControl release];
	
	// Add the appropriate accessibility label to each image.
	[[segmentedControl imageForSegmentAtIndex:0] setAccessibilityLabel:NSLocalizedString(@"CheckMarkIcon", @"")];
	[[segmentedControl imageForSegmentAtIndex:1] setAccessibilityLabel:NSLocalizedString(@"SearchIcon", @"")];
	[[segmentedControl imageForSegmentAtIndex:2] setAccessibilityLabel:NSLocalizedString(@"ToolsIcon", @"")];

	// label
	yPlacement += (kTweenMargin * 2.0) + kSegmentedControlHeight;
	frame = CGRectMake(	kLeftMargin,
						yPlacement,
						self.view.bounds.size.width - (kRightMargin * 2.0),
						kLabelHeight);
	[self.view addSubview:[SegmentViewController labelWithFrame:frame title:@"UISegmentControlStyleBordered:"]];
	
#pragma mark -
#pragma mark UISegmentControlStyleBordered
	segmentedControl = [[UISegmentedControl alloc] initWithItems:segmentTextContent];
	yPlacement += kTweenMargin + kLabelHeight;
	frame = CGRectMake(	kLeftMargin,
						yPlacement,
						self.view.bounds.size.width - (kRightMargin * 2.0),
						kSegmentedControlHeight);
	segmentedControl.frame = frame;
	[segmentedControl addTarget:self action:@selector(segmentAction:) forControlEvents:UIControlEventValueChanged];
	segmentedControl.segmentedControlStyle = UISegmentedControlStyleBordered;
	segmentedControl.selectedSegmentIndex = 1;
	
	[self.view addSubview:segmentedControl];
	[segmentedControl release];

	// label
	yPlacement += (kTweenMargin * 2.0) + kSegmentedControlHeight;
	frame = CGRectMake(	kLeftMargin,
					   yPlacement,
					   self.view.bounds.size.width - (kRightMargin * 2.0),
					   kLabelHeight);
	[self.view addSubview:[SegmentViewController labelWithFrame:frame title:@"UISegmentControlStyleBar:"]];
	
#pragma mark -
#pragma mark UISegmentControlStyleBar
	yPlacement += kTweenMargin + kLabelHeight;
	segmentedControl = [[UISegmentedControl alloc] initWithItems:segmentTextContent];
	frame = CGRectMake(	kLeftMargin,
					   yPlacement,
					   self.view.bounds.size.width - (kRightMargin * 2.0),
					   kSegmentedControlHeight);
	segmentedControl.frame = frame;
	[segmentedControl addTarget:self action:@selector(segmentAction:) forControlEvents:UIControlEventValueChanged];
	segmentedControl.segmentedControlStyle = UISegmentedControlStyleBar;
	segmentedControl.selectedSegmentIndex = 1;
	
	[self.view addSubview:segmentedControl];
	[segmentedControl release];
	
	// label
	yPlacement += (kTweenMargin * 2.0) + kSegmentedControlHeight;
	frame = CGRectMake(	kLeftMargin,
						yPlacement,
						self.view.bounds.size.width,
						kLabelHeight);
	[self.view addSubview:[SegmentViewController labelWithFrame:frame title:@"UISegmentControlStyleBar: (tinted)"]];
		
#pragma mark -
#pragma mark UISegmentedControl (red-tinted)
	segmentedControl = [[UISegmentedControl alloc] initWithItems:segmentTextContent];
	yPlacement += kTweenMargin + kLabelHeight;
	frame = CGRectMake(	kLeftMargin,
						yPlacement,
						self.view.bounds.size.width - (kRightMargin * 2.0),
						kSegmentedControlHeight);
	segmentedControl.frame = frame;
	[segmentedControl addTarget:self action:@selector(segmentAction:) forControlEvents:UIControlEventValueChanged];
	segmentedControl.segmentedControlStyle = UISegmentedControlStyleBar;	
	segmentedControl.tintColor = [UIColor colorWithRed:0.70 green:0.171 blue:0.1 alpha:1.0];
	segmentedControl.selectedSegmentIndex = 1;
	
	[self.view addSubview:segmentedControl];
	[segmentedControl release];
#pragma mark -
}

- (void)viewDidLoad
{	
	[super viewDidLoad];
	
	self.title = NSLocalizedString(@"SegmentTitle", @"");
	
	self.view.backgroundColor = [UIColor groupTableViewBackgroundColor];	// use the table view background color
	
	[self createControls];	// create the showcase of controls
}

@end

