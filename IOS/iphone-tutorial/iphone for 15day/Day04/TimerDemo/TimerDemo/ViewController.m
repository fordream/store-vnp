//
//  ViewController.m
//  TimerDemo
//
//  Created by cuong minh on 3/11/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@implementation ViewController
@synthesize labelCountDown;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [self setLabelCountDown:nil];
    tmrElapsedTime = nil;
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}
-(void)updateElapsedTime
{
    dblElapsedSeconds += 1;
    //double seconds = [[NSDate date] timeIntervalSinceDate:self.startTime];
    int hours,minutes, lseconds;
    hours = dblElapsedSeconds / 3600;
    minutes = (dblElapsedSeconds - (hours*3600)) / 60;
    lseconds = fmod(dblElapsedSeconds, 60); 
    labelCountDown.text = [NSString stringWithFormat: @"%02d:%02d", minutes, lseconds];
}

- (IBAction)switchHandle:(id)sender {
    UISwitch * switchTimer = (UISwitch *) sender;
    if (switchTimer.isOn ) {
        dblElapsedSeconds=0.0; //Declare this in header
    
        tmrElapsedTime = [NSTimer scheduledTimerWithTimeInterval:0.1 target:self selector:@selector(updateElapsedTime) userInfo:nil repeats:YES]; //Declare timer variable in header
    } else {
        [tmrElapsedTime invalidate];
    }
}

@end
