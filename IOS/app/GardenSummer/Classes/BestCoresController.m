    //
//  BestCoresController.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "BestCoresController.h"


@implementation BestCoresController

@synthesize  colorNames;  ;
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    if (interfaceOrientation == UIInterfaceOrientationLandscapeLeft || interfaceOrientation == UIInterfaceOrientationLandscapeRight) {
        return YES;
    }
    else {
        return NO;
    }
}
// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
/*
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization.
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	//[super viewDidLoad];
	
	Score *score = [[Common sharedInstance].dbManager getSocre ];
	
//	if(score != nil){
//		for(int i = 0; i < 50; i ++){
//			Point1 * temp  = [score get:i];
//			if(temp != nil){
//				//NSLog(@" level = %d score = %d" ,temp.x,temp.y);
//				[temp release];
//			}
//		}
//	}
	
	
	self.colorNames = [[NSArray alloc] initWithObjects:@"Red", @"Green",
					   @"Blue", @"Indigo", @"Violet", nil];
	tableView.editing = YES;
	
	//UiTable
	int height = 0;
	UIView *view__ = [[[UIView alloc] initWithFrame:CGRectMake(0, 0, 300, 300)] autorelease];
	[view__ setBackgroundColor:[UIColor whiteColor]];
	[tableView addSubview:view__];
	for(int i = 0; i < 25 ; i = i + 2){
		
		
		
		NSString *value1, *value2;
		value1 = [[NSString alloc]initWithFormat:@""];
		value2 = [[NSString alloc]initWithFormat:@""];

		if(score != nil){
			Point1 *temp = [score get:i];
			if(temp!= nil)
				value1 = [[NSString alloc]initWithFormat:@"%d",temp.y];
			temp = [score get:(i + 1)];
			if(temp!= nil)
				value2 = [[NSString alloc]initWithFormat:@"%d",temp.y];
			
		}
		
		UILabel *scoreLabel = [[UILabel alloc ] initWithFrame:CGRectMake(20, height, 50, 20)];
		scoreLabel.textAlignment =  UITextAlignmentCenter;
		scoreLabel.textColor = [UIColor blackColor];
		//scoreLabel.backgroundColor = [UIColor blackColor];
		scoreLabel.font = [UIFont fontWithName:@"10" size:(10.0)];
		scoreLabel.text = [NSString stringWithFormat: @"%@", value1];
		//[tableView addSubview:scoreLabel];
		
		UILabel *scoreLabel1 = [[UILabel alloc ] initWithFrame:CGRectMake(140, height, 50, 20) ];
		scoreLabel1.textAlignment =  UITextAlignmentCenter;
		scoreLabel1.textColor = [UIColor blackColor];
		//scoreLabel.backgroundColor = [UIColor blackColor];
		scoreLabel1.font = [UIFont fontWithName:@"10" size:(10.0)];
		scoreLabel1.text = [NSString stringWithFormat: @"%@", value2];
		//[tableView addSubview:scoreLabel];
		
		

		UIImageView * imgView_ = [self createUIImageView:(i + 1) X:0 Y:height];
		imgView_.frame = CGRectMake(0, height, 20, 20);
		//[tableView addSubview:imgView_];
		
		UIImageView *imgView_1= [self createUIImageView:(i + 2) X:0 Y:height];
		imgView_1.frame = CGRectMake(120, height, 20, 20);
		//[tableView addSubview:imgView_];
		
		//[tableView addSubview:[self createUIImageView:(i + 2) X:120 Y: height]];
		[view__ addSubview:imgView_];
		[view__ addSubview:imgView_1];
		[view__ addSubview:scoreLabel];
		[view__ addSubview:scoreLabel1];
		height = height + 20;
		//name = [[NSString alloc]initWithFormat:@"level%d.png",(i + 2)];
//		img = [UIImage imageNamed:name];
//		imgView = [[UIImageView alloc]initWithImage:img];
//		imgView.frame = CGRectMake(120, height, 20, 20);
		
		//[tableView addSubview:[self createUIImageView: (i + 2) X : 120 Y: height]];
		
		
	}
}
-(UIImageView*)createUIImageView:(int)level X:(float)x Y:(float)y{
	
	NSString * name = [[NSString alloc]initWithFormat:@"level%d.png",level];
	UIImage * img = [UIImage imageNamed:name];
	UIImageView *imgView_ = [[UIImageView alloc]initWithImage:img];
		return imgView_;
}
// Customize the number of rows in the table view.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
	return [self.colorNames count];
}

// Customize the appearance of table view cells.
//- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
//	
//    static NSString *CellIdentifier = @"Cell";
//	
//    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
//    if (cell == nil) {
//        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
//    }
//	
//	// Configure the cell.
//	cell.textLabel.text = [self.colorNames objectAtIndex: [indexPath row]];
//	
//    return cell;
//}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	
    static NSString *CellIdentifier = @"Cell";
	
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier] autorelease];
    }
	
	// Configure the cell.
	UIImage *cellImage = [UIImage imageNamed:@"apple.png"];
	cell.imageView.image = cellImage;
	
	NSString *colorString = [self.colorNames objectAtIndex: [indexPath row]];
	
	cell.textLabel.text = colorString;
	
	NSString *subtitle = [NSString stringWithString: @"All about the color "];
	subtitle = [subtitle stringByAppendingFormat:colorString];
	
	cell.detailTextLabel.text = subtitle;
	
	return cell;
} 

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/



- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

-(IBAction )onClick :(id)sender{
	if([Setting1 canSoundEffect]){
		[[SoundCommon soundMenu] play];
	}
	 [self.navigationController popViewControllerAnimated:YES];
}
- (void)dealloc {
    [super dealloc];
}


@end
