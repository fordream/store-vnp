    //
//  LevelController.m
//  GardenSummer
//
//  Created by Truong Vuong on 8/30/11.
//  Copyright 2011 CNC Software. All rights reserved.
//

#import "LevelController.h"
#import "Point1.h"
#import "PlayerController.h"

#import "MenuController.h"
#import "PlayGame1.h"

@implementation LevelController
@synthesize btnHome,btnReset;
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

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	 top  = 0;
	 left = 80;
	 width = 43;
	 height = 43;
	[self config];

}

-(void)config{
	Score *score = [[Common sharedInstance].dbManager getSocre ];
	int maxLevel = [[Common sharedInstance].dbManager getMaxlevelStore ];
	int _width = 0;
	int _height = 0;
	for(int i = 0; i < maxLevel ; i ++){
		if(i % 7 == 0){
			_width = 0;
			int temp = _height + height + 10;
			_height = temp;
		}
		
		position[i] = [[Point1 alloc] init];
		position[i].x = _width + left ;
		position[i].y = _height + top;
		
		_width += width + 5;
	}
	
	for(int i = 0; i < maxLevel ; i ++){
		Point1 * temp = [score get:i];
		
		UILabel *scoreLabel = [[UILabel alloc ] initWithFrame:CGRectMake(position[i].x - 5, position[i].y + height - 5 , 50, 20)];
		scoreLabel.textAlignment =  UITextAlignmentCenter;
		scoreLabel.font = [UIFont fontWithName:@"5" size:(5.0)];
		scoreLabel.backgroundColor = [UIColor clearColor];
		scoreLabel.textColor = [UIColor whiteColor];
		scoreLabel.text = [NSString stringWithFormat: @"%d", temp.y];
		
		[self.view addSubview:scoreLabel];
		
		btnLevel[i] = [UIButton buttonWithType:UIButtonTypeCustom];
		[(btnLevel[i]) addTarget:self action:@selector(buttonPessed:) forControlEvents:UIControlEventTouchUpInside];
	
		NSString *name = [[NSString alloc]initWithFormat:@"level%d.png",(i + 1)] ;
		UIImage *img= [UIImage imageNamed:name];
		btnLevel[i].frame = CGRectMake(position[i].x, position[i].y, width, height);
		[(btnLevel[i]) setImage:img forState:UIControlStateNormal];
		[(btnLevel[i]) addTarget:self action:@selector(buttonPessed:) forControlEvents:UIControlEventTouchUpInside];
		[super.view addSubview:(btnLevel[i])];
	}
}

-(IBAction) buttonPessed:(id)sender{
	if([Setting1 canSoundEffect]){
		[[SoundCommon soundMenu] play];
	}
	
	if(sender == btnHome){
		MenuController *_view1 = [[MenuController alloc] initWithNibName:@"menu"  bundle:nil];
		[self.navigationController pushViewController:_view1 animated:YES];
		//[self.navigationController popViewControllerAnimated:YES];
		return;
	}else if(sender == btnReset){
		return;
	}
	
	for(int i =0 ; i < 25; i ++){
		if((UIButton*)sender == btnLevel[i]){
			if([Setting1 canSoundEffect]) [[SoundCommon soundMenu]play];
			PlayerController *_view1 = [[PlayerController alloc] initWithNibName:@"playgame" level:(i+1) bundle:nil];
			[self.navigationController pushViewController:_view1 animated:YES];
			return;
		}
	}
}

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)viewDidUnload {
    [super viewDidUnload];
}


- (void)dealloc {
    [super dealloc];
}


@end
