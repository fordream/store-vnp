//
//  PagingController.m
//  AnyBook
//
//  Created by vega on 4/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PagingController.h"

@implementation PagingController
- (id)init
{
    self = [super initWithNibName:@"PagingController" bundle:Nil];
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

-(void)config{
    
    NSString *paging = [dataStore getStringConfig:KEY_PAGING];
    
    if(paging == nil || paging == @""){
        paging = PAGING_10px;
    }
    
    [btn10Px setImageUnSelected];
    [btn20Px setImageUnSelected];
    [btn30Px setImageUnSelected];
    [btn40Px setImageUnSelected];
    [btn50Px setImageUnSelected];
    
    
    if(paging == PAGING_10px){
        [btn10Px setImageSelected];
    }else if(paging == PAGING_20px){
        [btn20Px setImageSelected];
    }else if(paging == PAGING_30px){
        [btn30Px setImageSelected];
    }else if(paging == PAGING_40px){
        [btn40Px setImageSelected];
    }else if(paging == PAGING_50px){
        [btn50Px setImageSelected];
    }
}
-(IBAction)onClick:(id)sender{
    if(sender == btn10Px){
        [dataStore setStringConfig:PAGING_10px forKey:KEY_PAGING];
    }else if(sender ==btn20Px){
       [dataStore setStringConfig:PAGING_20px forKey:KEY_PAGING]; 
    }else if(sender == btn30Px){
        [dataStore setStringConfig:PAGING_30px forKey:KEY_PAGING];
    }else if(sender == btn40Px){
        [dataStore setStringConfig:PAGING_40px forKey:KEY_PAGING];
    }else if(sender == btn50Px){
        [dataStore setStringConfig:PAGING_50px forKey:KEY_PAGING];
    }else{
        [self dismissModalViewControllerAnimated:YES];
        return;
    }
    
    [self config];
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
    
    [self config];
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
