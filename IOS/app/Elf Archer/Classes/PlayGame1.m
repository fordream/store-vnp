    //
//  PlayGame1.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/6/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "PlayGame1.h"


@implementation PlayGame1


- (id)initWithFrame:(CGRect)frame {
	if (self = [super initWithFrame:frame]) {
		// Initialization code
	}
	return self;
}



- (void)drawRect:(CGRect)rect {
	CGContextRef	context = UIGraphicsGetCurrentContext();
	
	[self DrawLine:context];
	//[self DrawEclipse:context];
	//[self DrawEClipse1:context]
	//[self drawRect1:context];
	//[self MocNgoac:context];
	//[self drawTraing: context];
	
}

//drawable
-(void)drawRect1:(CGRect)rect { 
	CGContextRef ctx = UIGraphicsGetCurrentContext(); 
	CGContextBeginPath(ctx);
	CGContextAddArc(ctx,110,50,30,0,2*M_PI,1); 
	CGContextAddArc(ctx,210,50,30,0,2*M_PI,1); 
	CGContextAddArc(ctx,160,110,15,0,2*M_PI,1); 
	CGContextAddArc(ctx,160,210,25,0,2*M_PI,1); 
	CGContextFillPath(ctx);
}

//
//-(void)drawRect1:(CGContextRef*)context{
//	CGContextSetRGBStrokeColor(context, 0.0, 1.0, 0.0, 1.0);
//	CGContextSetRGBFillColor(context, 0.0, 0.0, 1.0, 1.0);
//	CGContextAddRect(context, CGRectMake(30.0, 30.0, 60.0, 60.0));
//	CGContextFillPath(context);
//}
-(void)DrawEClipse1:(CGContextRef*)context{
	CGContextAddEllipseInRect(context, CGRectMake(150.0, 170.0, 10.0, 50.0));
	CGContextFillPath(context);
}
-(void)DrawEclipse:(CGContextRef*)context{
	CGContextAddEllipseInRect(context, CGRectMake(70.0, 170.0, 20.0, 50.0));
	CGContextStrokePath(context);
}
-(void)DrawLine:(CGContextRef*)context{
	CGContextSetLineWidth(context, 5.0);
	CGContextMoveToPoint(context, 50, 100);
	CGContextAddLineToPoint(context, 200, 100);
	CGContextStrokePath(context);
}
-(void)MocNgoac:(CGContextRef*)context{
	CGContextAddArc(context, 260, 90, 40, 0.0*M_PI/180, 270*M_PI/180, 1);
	CGContextAddLineToPoint(context, 280, 350);
	CGContextStrokePath(context);
}
-(void)drawTraing :(CGContextRef*)context{
	CGContextMoveToPoint(context, 130, 300);
	CGContextAddLineToPoint(context, 80, 400);
	CGContextAddLineToPoint(context, 190, 400);
	CGContextAddLineToPoint(context, 130, 300);
	CGContextStrokePath(context);
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

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

//- (void)didReceiveMemoryWarning {
//    // Releases the view if it doesn't have a superview.
//    [super didReceiveMemoryWarning];
//    
//    // Release any cached data, images, etc. that aren't in use.
//}
//
//- (void)viewDidUnload {
//    [super viewDidUnload];
//    // Release any retained subviews of the main view.
//    // e.g. self.myOutlet = nil;
//}
//

- (void)dealloc {
    [super dealloc];
}


@end
