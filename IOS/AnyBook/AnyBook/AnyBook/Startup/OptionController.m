//
//  OptionController.m
//  AnyBook
//
//  Created by vega on 4/17/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "OptionController.h"


@implementation OptionController
- (id)init
{
    self = [super initWithNibName:@"OptionController" bundle:Nil];
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

-(IBAction)onClick:(id)sender{
    if(sender == btnGotoHome){
        [self dismissModalViewControllerAnimated:YES];
        return;
    }else if(sender == btnGotoSearh){
        [self dismissModalViewControllerAnimated:YES];
        //[mControler gotoSearch];
        return;
    }else if(sender == bntDisplay){
        DisplayController* vc = [[DisplayController alloc] init];
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
    }else if(sender == btnAsyn){
        AsynController* vc = [[AsynController alloc] init];
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
    }else if(sender == btnTextPagging){
        
        PagingController* vc = [[PagingController alloc] init];
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
    }else if(sender == btnDisplayDayOrNight){
        DayController* vc = [[DayController alloc] init];
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
    }else if(sender == btnTextFontSize){
        FontSizeController* vc = [[FontSizeController alloc] init];
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
    }else if(sender == btnDispalyOrient){
        OrientController* vc = [[OrientController alloc] init];
        self.modalPresentationStyle = UIModalPresentationPageSheet;
        [self presentModalViewController:vc animated:YES];
    }
    
}

//-(void)setController:(ViewTabLibrary*)controller{
   // mControler = controller;
//}


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
