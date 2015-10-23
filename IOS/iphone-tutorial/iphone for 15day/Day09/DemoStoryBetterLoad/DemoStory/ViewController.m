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
@synthesize pageCounter = _pageCounter;
@synthesize pages = _pages;

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
    
    _pages = [[NSMutableArray alloc] initWithCapacity: pageCount];
    for (NSInteger i = 0; i < pageCount; ++i)
    {
        [_pages addObject:[NSNull null]];
    }
    
    
    CGSize viewSize = self.scrollView.frame.size;
    self.scrollView.contentSize = CGSizeMake(viewSize.width*pageCount, viewSize.height);
    self.scrollView.showsHorizontalScrollIndicator = NO;    
    self.scrollView.pagingEnabled = YES;
    
    /*
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
     */
    [self loadVisiblePages: currentPage];
    [self updatePageCounterAndSlider: currentPage];
}
//Display only visible page, its previous and next page
- (void) loadVisiblePages: (int) pageIndex
{
    if ([_pages objectAtIndex:pageIndex] == [NSNull null])  //Does not exist then create !
    {
        CGRect pageViewRect = self.scrollView.frame;
        pageWidth = pageViewRect.size.width;
        pageViewRect.origin.x = pageWidth * (pageIndex-1);
        
        UIView *pageView = [[UIView alloc] initWithFrame:pageViewRect];
        
        UILabel *pageLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 200, 320, 40)];
       // pageLabel.text = [NSString stringWithFormat:@"Page %d", pageIndex];
        
        pageLabel.text = [NSString localizedStringWithFormat:@"Page %d", pageIndex];
        NSLog(@"%@", pageLabel.text);
        pageLabel.textAlignment = UITextAlignmentCenter;
        [pageView addSubview:pageLabel];
        [self.scrollView addSubview:pageView];        
        [_pages replaceObjectAtIndex:pageIndex withObject:pageView];    
    }
    
    self.scrollView.contentOffset =  CGPointMake(pageWidth* (pageIndex -1), self.scrollView.contentOffset.y);

}

//Update page counter label
- (void) updatePageCounterAndSlider: (int) pageIndex
{
    _pageCounter.text = [NSString localizedStringWithFormat:@"page %d", pageIndex];
    //Update slider  
    _pageSlider.value = (float)pageIndex/pageCount;
}
- (void)viewDidUnload
{
    [self setScrollView:nil];
    [self setPageSlider:nil];
    [self setPageCounter:nil];
    [self setPages:nil];
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
    if (currentPage > 1) {
        currentPage = currentPage -1 ;
        [self loadVisiblePages: currentPage];       
        
    }
}
-(IBAction)handleNext:(id)sender{
    if (currentPage < pageCount - 1) {
        currentPage = currentPage + 1;
        [self loadVisiblePages: currentPage];
    }         
}
- (IBAction)sliderChange:(id)sender {
   self.scrollView.contentOffset =  CGPointMake(_pageSlider.value*pageWidth*(float) pageCount, self.scrollView.contentOffset.y);
    
}

#pragma mark - UIScrollViewDelegate
- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{    
    currentPage = (NSInteger)round(scrollView.contentOffset.x/pageWidth)+1;
    if (currentPage > pageCount-1) return;
    [self loadVisiblePages:currentPage];
    [self updatePageCounterAndSlider:currentPage];    
}

@end
