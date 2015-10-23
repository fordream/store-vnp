//
//  ViewController.m
//  DemoImage
//
//  Created by Techmaster on 6/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize photoView;
@synthesize alphaSlider;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [self setPhotoView:nil];
    [self setAlphaSlider:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}
- (IBAction)segmentValueChange:(id)sender {
    UISegmentedControl * mySegmented = (UISegmentedControl *) sender;
    if (mySegmented.selectedSegmentIndex == 0)
    {
        photoView.image = [UIImage imageNamed:@"Messi.jpg"];
    } else {
        photoView.image = [UIImage imageNamed:@"Ronaldo.jpg"];
        
    }
}
- (IBAction)alphaChangeValue:(id)sender {
    photoView.alpha = alphaSlider.value;
}


@end
