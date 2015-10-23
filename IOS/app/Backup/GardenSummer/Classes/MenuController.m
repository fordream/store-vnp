    //
//  MenuController.m
//  GardenSummer
//
//  Created by Truong Vuong on 8/26/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "MenuController.h"
#import "PlayerController.h"
#import "LevelController.h"
#import "Setting1.h"
#import "SettingController.h"
#import "BestCoresController.h"
#import "HelpController.h"
#import "MoreAppController.h"

@implementation MenuController


@synthesize btnNewGame,btnExit,btnSetting,btnBesores,btnHelp,btnMoreApp;

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
	self.navigationController.navigationBarHidden = YES;
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

//add new 
-(IBAction) buttonPressed:(id)sender{
	
	//Setting1 setting = [[Setting1 alloc]init];
	//[setting setSound:NO];
	if([Setting1 canSoundEffect]){
		[[SoundCommon soundMenu] play];
	}
	//[[SoundCommon soundMenu]play];
	
	if(sender == btnNewGame){
		LevelController *_view0 = [[LevelController alloc] initWithNibName:@"level" bundle:nil];
		[self.navigationController pushViewController:_view0 animated:YES];
	}else if(sender == btnExit){
		//[[UIApplication sharedApplication] terminateWithSuccess];
	}else if(sender == btnSetting){
		SettingController *_view1 = [[SettingController alloc] initWithNibName:@"setting" bundle:nil];
		[self.navigationController pushViewController:_view1 animated:YES];
	}else if(sender == btnBesores){
		BestCoresController *_view2 = [[BestCoresController alloc] initWithNibName:@"bestcores" bundle:nil];
		[self.navigationController pushViewController:_view2 animated:YES];
	}else if(sender == btnHelp){
		HelpController *_view3 = [[HelpController alloc] initWithNibName:@"help" bundle:nil];
		[self.navigationController pushViewController:_view3 animated:YES];
	}else if(sender == btnMoreApp){
		MoreAppController *_view4 = [[MoreAppController alloc] initWithNibName:@"moreapp" bundle:nil];
		[self.navigationController pushViewController:_view4 animated:YES];
	}
}


@end
