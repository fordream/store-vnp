    //
//  SettingController.m
//  GardenSummer
//
//  Created by Truong Vuong on 9/4/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "SettingController.h"
#import "Setting1.h"
#import "SoundCommon.h"
#import "MenuController.h"
@implementation SettingController
@synthesize btnSoundBackground,btnSoundEffect,btnTrainning,btnTrainning1,btnHome;



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
	self.navigationController.navigationBarHidden = YES;
	
	
	[self config];
	
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

-(IBAction) buttonPressed:(id)sender{

	//[(imgEnd[i]) setImage:img1 forState:UIControlStateNormal];

		
	if(sender == btnSoundEffect){
		[Setting1 setSoundEffect:(![Setting1 canSoundEffect])];
		
		
	}else if(sender == btnTrainning){
		[Setting1 setTrainning:YES];		
		
	}else if(btnSoundBackground == sender){
		[Setting1 setCanSoundBackgrond:![Setting1 canSoundBackgrond]];
		if([Setting1 canSoundBackgrond]){
			[[SoundCommon soundBackground] play];
		}else{
			[[SoundCommon soundBackground] stop];
		}	
	}else if(sender == btnHome){
		MenuController *_view3 = [[MenuController alloc] initWithNibName:@"menu" bundle:nil];
		UINavigationController *nav=[[[UINavigationController alloc]initWithRootViewController:_view3] autorelease];
		[self presentModalViewController:nav animated:YES];
		//MenuController *_view2 = [[MenuController alloc] initWithNibName:@"menu" bundle:nil];
		//[self.navigationController pushViewController:_view2 animated:YES];
	}else if(sender == btnTrainning1){
		[Setting1 setTrainning:NO];
	}
	[self config];
	if([Setting1 canSoundEffect]){
		[[SoundCommon soundMenu] play];
	}
	
	 

}

-(void) config{
	UIImage *img1= [UIImage imageNamed:@"soundoff.png"];
	UIImage *img2= [UIImage imageNamed:@"soundon.png"];
	
	//if([Setting1 isTranning]){
//		[btnTrainning setTitle:@"Trainning" forState:UIControlStateNormal]; 
//	}else{
//		[btnTrainning setTitle:@"Normal" forState:UIControlStateNormal];
//	}
	
	if([Setting1 canSoundEffect]){
		[btnSoundEffect setImage:img2 forState:UIControlStateNormal];
	}else{
		[btnSoundEffect setImage:img1 forState:UIControlStateNormal];

	}
	
	if([Setting1 canSoundBackgrond]){
		[btnSoundBackground setImage:img2 forState:UIControlStateNormal];
	}else{
		[btnSoundBackground setImage:img1 forState:UIControlStateNormal];
	}
	
	if([Setting1 isTranning]){
		[btnTrainning setImage:[UIImage imageNamed:@"trainingon.png"] forState:UIControlStateNormal];
		[btnTrainning1 setImage:[UIImage imageNamed:@"tournamentoff.png"] forState:UIControlStateNormal];
	}else{
		[btnTrainning setImage:[UIImage imageNamed:@"trainingoff.png"] forState:UIControlStateNormal];
		[btnTrainning1 setImage:[UIImage imageNamed:@"tournamenton.png"] forState:UIControlStateNormal];
		
	}
}
- (void)dealloc {
    [super dealloc];
}


@end
