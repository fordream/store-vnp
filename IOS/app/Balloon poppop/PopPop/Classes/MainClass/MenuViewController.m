    //
//  MenuViewController.m
//  DiamondGame
//
//  Created by hnsunflower1807 on 6/20/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "MenuViewController.h"
#import "PlayViewController.h"
#import "HelpViewController.h"
#import "BestScoreViewController.h"


@implementation MenuViewController

@synthesize modeGameChoosed;
@synthesize playBtnClassic, playBtnTimeBeaten, helpBtn, bestScoreBtn;



 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization.
    }
	
    return self;
}


/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
	
    [super viewDidLoad];
	
	self.navigationController.navigationBarHidden = YES;
	
	
	modeGameChoosed = 0;
	
}



- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Overriden to allow any orientation.
   // return YES;
	return (interfaceOrientation == UIDeviceOrientationPortrait);
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
	[playBtnClassic release];
	[playBtnTimeBeaten release];
	[helpBtn release];
	[bestScoreBtn release];
	
    [super dealloc];
}



//======================================================================================
-(IBAction) ClickPlayBtn:(id)sender{
	
	UIButton *btn = (UIButton*)sender;
	
	[playBtnClassic setImage:[UIImage imageNamed:@"btn_classic_off.png"] forState:UIControlStateNormal];
	[playBtnTimeBeaten setImage:[UIImage imageNamed:@"btn_timebeaten_off.png"] forState:UIControlStateNormal];
	
	modeGameChoosed = btn.tag;
	
	switch (modeGameChoosed) {
		case 0:
			[btn setImage:[UIImage imageNamed:@"btn_classic_on.PNG"] 
				 forState:UIControlStateNormal];
			
			break;
		case 1:
			[btn setImage:[UIImage imageNamed:@"btn_time_beaten_on.png"] 
				 forState:UIControlStateNormal];
			
			break;
			
		default:
			break;
	}
	
	PlayViewController *playView = [[PlayViewController alloc] initWithNibName:@"PlayView" bundle:nil withGameMode:modeGameChoosed];
	[self.navigationController pushViewController:playView animated:YES];
	
}



-(IBAction) clickHelpBtn:(id)sender {
	HelpViewController *helpView = [[HelpViewController alloc] initWithNibName:@"HelpView" bundle:nil];
	[self.navigationController pushViewController:helpView animated:YES];

}

-(IBAction) clickBestScoreBtn:(id)sender {
	BestScoreViewController *bestScoreView = [[BestScoreViewController alloc] initWithNibName:@"BestScoreView" bundle:nil];
	[self.navigationController pushViewController:bestScoreView animated:YES];
}







@end
