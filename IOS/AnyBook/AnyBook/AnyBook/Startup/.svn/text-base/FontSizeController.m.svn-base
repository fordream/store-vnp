//
//  PagingController.m
//  AnyBook
//
//  Created by vega on 4/18/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "FontSizeController.h"

@implementation FontSizeController
- (id)init
{
    self = [super initWithNibName:@"FontSizeController" bundle:Nil];
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
    
    NSString *paging = [dataStore getStringConfig:KEY_FONT_SIZE];
    
    if(paging == nil || paging == @""){
        paging = KEY_FONT_SIZE_VERY_SMALL;
    }
    
    [btn10Px setImageUnSelected];
    [btn20Px setImageUnSelected];
    [btn30Px setImageUnSelected];
    [btn40Px setImageUnSelected];
    [btn50Px setImageUnSelected];
    
    
    if(paging == KEY_FONT_SIZE_VERY_SMALL){
        [btn10Px setImageSelected];
    }else if(paging == KEY_FONT_SIZE_SMALL){
        [btn20Px setImageSelected];
    }else if(paging == KEY_FONT_SIZE_NORMAL){
        [btn30Px setImageSelected];
    }else if(paging == KEY_FONT_SIZE_BIG){
        [btn40Px setImageSelected];
    }else if(paging == KEY_FONT_SIZE_VERY_BIG){
        [btn50Px setImageSelected];
    }
}
-(IBAction)onClick:(id)sender{
    if(sender == btn10Px){
        [dataStore setStringConfig:KEY_FONT_SIZE_VERY_SMALL forKey:KEY_FONT_SIZE];
    }else if(sender ==btn20Px){
       [dataStore setStringConfig:KEY_FONT_SIZE_SMALL forKey:KEY_FONT_SIZE]; 
    }else if(sender == btn30Px){
        [dataStore setStringConfig:KEY_FONT_SIZE_NORMAL forKey:KEY_FONT_SIZE];
    }else if(sender == btn40Px){
        [dataStore setStringConfig:KEY_FONT_SIZE_BIG forKey:KEY_FONT_SIZE];
    }else if(sender == btn50Px){
        [dataStore setStringConfig:KEY_FONT_SIZE_VERY_BIG forKey:KEY_FONT_SIZE];
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
