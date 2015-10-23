    //
//  MoreAppController.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "MoreAppController.h"


@implementation MoreAppController

-(IBAction) loadWebView:(id)sender{
	UIButton *btn = (UIButton *) sender;
	
	if([Setting1 canSoundEffect]){
		[[SoundCommon soundMenu] play];
	}
	
	switch (btn.tag) {
		case 0:
			[[UIApplication sharedApplication]
			 openURL:[NSURL
					  URLWithString:@"http://itunes.apple.com/us/app/turtles-smiles/id455536308?mt=8"]];
			break;
		case 1:
			[[UIApplication sharedApplication]
			 openURL:[NSURL
					  URLWithString:@"http://itunes.apple.com/us/app/pikachu-beaches/id456373708?mt=8"]];
			break;
		case 2:
			[[UIApplication sharedApplication]
			 openURL:[NSURL
					  URLWithString:@"http://itunes.apple.com/us/app/green-line/id463783372?ls=1&mt=8"]];
			break;
		case 3:
			[[UIApplication sharedApplication]
			 openURL:[NSURL
					  URLWithString:@"http://itunes.apple.com/us/app/balloons-poppop/id460656216?mt=8"]];
			break;
		case 4:
			[[UIApplication sharedApplication]
			 openURL:[NSURL
					  URLWithString:@"http://itunes.apple.com/us/app/pikachu-beach-hd/id456552100?mt=8"]];
			break;
		case 5:
			[[UIApplication sharedApplication]
			 openURL:[NSURL
					  URLWithString:@"http://itunes.apple.com/us/app/green-line-hd/id463789421?ls=1&mt=8"]];
			break;
		case 6:
			[[UIApplication sharedApplication]
			 openURL:[NSURL
					  URLWithString:@"http://itunes.apple.com/us/app/balloons-poppop-hd/id460665128?mt=8"]];
			break;
			
		default:
			break;
	}
}


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
-(IBAction)onClick:(id)sender{
	if([Setting1 canSoundEffect]){
		[[SoundCommon soundMenu] play];
	}
	[self.navigationController popViewControllerAnimated:YES];
}

- (void)dealloc {
    [super dealloc];
}


@end
