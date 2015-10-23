#import "DetailViewController.h"

@implementation DetailViewController

@synthesize desview;
@synthesize slider;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
	desview.font = [UIFont fontWithName:@"Helvetica" size:18.0];
	slider.continuous=TRUE;
	slider.minimumValue=10.0;
	slider.maximumValue=26.0;
	slider.value=18.0;
	[self.navigationController.navigationBar setBarStyle:UIBarStyleBlackOpaque];	
	
	
}


- (IBAction)slidermoved{
	[desview setFont:[UIFont fontWithName:@"Helvetica" size:slider.value]];
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)dealloc
{
	[desview release];
	[slider release];
	[super dealloc];
}

@end