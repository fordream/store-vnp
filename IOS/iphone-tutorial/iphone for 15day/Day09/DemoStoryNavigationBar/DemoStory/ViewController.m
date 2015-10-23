//
//  ViewController.m
//  DemoStory
//
//  Created by cuong minh on 7/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize scrollView = _scrollView;
@synthesize pageSlider = _pageSlider;
@synthesize pageCounter;
- (void) createPrevNextButtons

{
    // create an array for the buttons
    
    NSMutableArray* buttons = [[NSMutableArray alloc] initWithCapacity:3];
    // create a standard save button
    UIBarButtonItem *preButton = [[UIBarButtonItem alloc]
                                   initWithTitle:@"<" 
                                   style:UIBarButtonItemStyleBordered
                                   target:self
                                   action:@selector(handlePrev:)];
   // preButton.style = UIBarButtonItemStyleBordered;
    // create a spacer between the buttons
    UIBarButtonItem *spacer = [[UIBarButtonItem alloc]                               
                               initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace                      
                               target:nil                               
                               action:nil];
    [buttons addObject:spacer];  
    // create a standard delete button with the trash icon
    UIBarButtonItem *nextButton = [[UIBarButtonItem alloc]
                                     initWithTitle:@">"
                                     style:UIBarButtonItemStyleBordered
                                     target:self
                                     action:@selector(handleNext:)];
   // nextButton.style = UIBarButtonItemStyleBordered;
    [buttons addObject:nextButton];
    NSArray *buttonArray = [[NSArray alloc] initWithObjects:nextButton, spacer, preButton, nil];
    self.navigationItem.rightBarButtonItems = buttonArray;
}
- (void)viewDidLoad
{
    [self createPrevNextButtons];
    [super viewDidLoad];
	self->pageCount = 1000;
    self->currentPage = 1;
    self->pageCounter.text= [NSString stringWithFormat:@"Page 1"];
    _pageSlider.value = 0.0f;
    CGSize viewSize = self.scrollView.frame.size;
    self.scrollView.contentSize = CGSizeMake(viewSize.width*pageCount, viewSize.height);
    self.scrollView.showsHorizontalScrollIndicator = NO;
    CGRect pageViewRect = self.scrollView.frame;
    pageWidth = pageViewRect.size.width;
    self.scrollView.pagingEnabled = YES;
    for (int i=1; i<pageCount; i++) {
        //Create content page here
        UIView *pageView = [[UIView alloc] initWithFrame:pageViewRect];
        pageViewRect.origin.x += pageWidth;
        UILabel *pageLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 200, 320, 40)];
        pageLabel.text = [NSString stringWithFormat:@"Page %d", i];
        pageLabel.textAlignment = UITextAlignmentCenter;
        [pageView addSubview:pageLabel];
        [self.scrollView addSubview:pageView];
    }
}
- (void)viewDidUnload
{
    [self setScrollView:nil];
    [self setPageSlider:nil];
    [self setPageCounter:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}
-(void)viewWillAppear:(BOOL)animated{
}
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}
-(IBAction)handlePrev:(id)sender{
    if (currentPage >=2) {
        self.scrollView.contentOffset =  CGPointMake(self.scrollView.contentOffset.x  - pageWidth, self.scrollView.contentOffset.y);          
        
    }
}
-(IBAction)handleNext:(id)sender{
    if (currentPage < 99) {
        self.scrollView.contentOffset =  CGPointMake(self.scrollView.contentOffset.x + pageWidth, self.scrollView.contentOffset.y);
    }         
}
- (IBAction)sliderChange:(id)sender {
   self.scrollView.contentOffset =  CGPointMake(_pageSlider.value*pageWidth*(float) pageCount, self.scrollView.contentOffset.y);
    //NSLog(@"%f", _pageSlider.value*pageWidth);
    
}

#pragma mark - UIScrollViewDelegate
- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    
    currentPage = (NSInteger)round(scrollView.contentOffset.x/pageWidth)+1;
    _pageSlider.value = (float)currentPage/pageCount;
    pageCounter.text = [NSString stringWithFormat:@"Page %d",currentPage];
    
}

@end
