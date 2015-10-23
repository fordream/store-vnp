//
//  PagingController.m
//  AnyBook
//
//  Created by vega on 4/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "DayController.h"

@implementation DayController
- (id)init
{
    self = [super initWithNibName:@"DayController" bundle:Nil];
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
    
    NSString *paging = [dataStore getStringConfig:KEY_DAY_OR_NIGHT];
    
    if(paging == nil || paging == @""){
        paging = DAY_AUTO;
    }
    
    [btn10Px setImageUnSelected];
    [btn20Px setImageUnSelected];
    [btn30Px setImageUnSelected];

    
    
    if(paging == DAY_AUTO){
        [btn10Px setImageSelected];
    }else if(paging == DAY_DAY){
        [btn20Px setImageSelected];
    }else if(paging == DAY_NIGHT){
        [btn30Px setImageSelected];
    }
}
-(IBAction)onClick:(id)sender{
    if(sender == btn10Px){
        [dataStore setStringConfig:DAY_AUTO forKey:KEY_DAY_OR_NIGHT];
    }else if(sender ==btn20Px){
       [dataStore setStringConfig:DAY_DAY forKey:KEY_DAY_OR_NIGHT]; 
    }else if(sender == btn30Px){
        [dataStore setStringConfig:DAY_NIGHT forKey:KEY_DAY_OR_NIGHT];
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
