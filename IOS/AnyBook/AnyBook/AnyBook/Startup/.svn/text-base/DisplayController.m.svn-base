//
//  DisplayController.m
//  AnyBook
//
//  Created by vega on 4/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "DisplayController.h"

@implementation DisplayController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        dataStore = Nil;
    }
    return self;
}

- (id)init
{
    self = [super initWithNibName:@"DisplayController" bundle:Nil];
    if (self) {
        // Custom initialization
       
    }
    return self;
}
-(IBAction)onCLick:(id)sender{
    if(sender == btnGotoHome){
        [self dismissModalViewControllerAnimated:YES];
    }else if(sender == uiSwith){
        if([uiSwith isOn]){

            [dataStore setStringConfig:ON forKey:KEY_STATUS_ON];
            [uiSlider setEnabled:YES];
        }else {
             [dataStore setStringConfig:OFF forKey:KEY_STATUS_ON];
            [uiSlider setEnabled:NO];
        }
    }else if(sender == uiSlider){
        NSString * value = [NSString stringWithFormat:@"%f",[uiSlider value]];
        [dataStore setStringConfig:value forKey:KEY_DISPLAY_VALUE];
        
    }
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    dataStore = [CachedDataStore getInstance];
    
    NSString* isOn = [dataStore getStringConfig:KEY_STATUS_ON];

    if(isOn == ON ){
        [uiSwith setOn:YES];
        [uiSlider setEnabled:YES];
    }else{
        [uiSwith setOn:NO];
        [uiSlider setEnabled:NO];
    }
    
    NSString *value = [dataStore getStringConfig:KEY_DISPLAY_VALUE];
    
    float value_float = 0.0f;
    
    if(value != nil && value != @""){
        value_float = [value floatValue];
    }
     [uiSlider setValue:value_float];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
