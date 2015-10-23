//
//  PlayController.m
//  ElfArcher
//
//  Created by truong vuong on 9/15/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "PlayController.h"


@implementation PlayController

// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization.
    }

	
    return self;
}



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
   
	[super viewDidLoad];
	CGRect rectFrame = [UIScreen mainScreen].applicationFrame;
	//CGRectMake(0, 0, 100, 100)
	RoundView * roundView =[[RoundView alloc]initWithFrame:rectFrame ];
	[self.view addSubview:roundView];
	
//	top = 50;
//	left = 120;
//	height = 100;
//	width = 100;
//	height2 = 10;
//	width2 = 10;
//	
// 	UIImage *img = [UIImage imageNamed:@"kc1.jpg"];
//	_view1 = [[UIImageView alloc] initWithImage:img];
//	
//	_view1.frame = CGRectMake(left, top, width, height);
//	[self.view addSubview:_view1];
//	
//	img = [UIImage imageNamed:@"images.jpg"];
//	_view2 = [[UIImageView alloc] initWithImage:img];
//	_view2.frame = CGRectMake(_view1.center.x - width2/2, _view1.center.y - height2/2, width2, height2);
//	[self.view addSubview:_view2];
	
	//[(boardButton[x][y]) addTarget:self action:@selector(wasDragged:withEvent:) forControlEvents:UIControlEventTouchDragInside];
}
-(void) wasDragged :(UIButton *)button withEvent:(UIEvent *)event {
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc. that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}


@end
