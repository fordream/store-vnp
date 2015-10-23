//
//  AsynController.m
//  AnyBook
//
//  Created by vega on 4/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "AsynController.h"

@implementation AsynController
- (id)init
{
    self = [super initWithNibName:@"AsynController" bundle:Nil];
    if (self) {
        // Custom initialization
        
    }
    return self;
}
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}


-(IBAction)onClick:(id)sender{
    if(sender == btnNormal){
        [dataStore setStringConfig: OFF forKey:KEY_ASYN_FULL];
    }else if(sender == btnFull){
        [dataStore setStringConfig: ON forKey:KEY_ASYN_FULL];
    }else{
        [self dismissModalViewControllerAnimated:YES];
    }
    
    if([dataStore getStringConfig:KEY_ASYN_FULL] == ON){
        [btnFull setImageSelected];
        [btnNormal setImageUnSelected];
    }else{
        [btnFull setImageUnSelected];
        [btnNormal setImageSelected];
    }
}
#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    dataStore = [CachedDataStore getInstance];
    
    if([dataStore getStringConfig:KEY_ASYN_FULL] == ON){
        [btnFull setImageSelected];
        [btnNormal setImageUnSelected];
    }else{
        [btnFull setImageUnSelected];
        [btnNormal setImageSelected];
    }
    
    
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
