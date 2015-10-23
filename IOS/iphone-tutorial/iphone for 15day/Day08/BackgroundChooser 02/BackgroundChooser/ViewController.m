//
//  ViewController.m
//  BackgroundChooser
//
//  Created by cuong minh on 3/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import "AboutViewController.h"
#import "DataSource.h"

@implementation ViewController
@synthesize datasource;
@synthesize scrollView;
@synthesize pageControl;
@synthesize aboutButton;
//@synthesize pageImages;
@synthesize pageViews;
@synthesize aboutViewController;
@synthesize labelPopup;
//@synthesize swipeGestureRecognizer;

#pragma mark - Load data to scroll view


- (void)loadPage:(NSInteger)page {
    if (page < 0 || page >= pageCount) {
        // If it's outside the range of what we have to display, then do nothing
        return;
    }
    
    // Load an individual page, first seeing if we've already loaded it
    UIView *pageView = [self.pageViews objectAtIndex:page];
    if ((NSNull*)pageView == [NSNull null]) {
       // CGRect frame = self.scrollView.frame	;
        CGRect frame = [UIScreen mainScreen].bounds;
        frame.origin.x = frame.size.width * page;
        frame.origin.y = 0.0f;
        
        UIImageView *newPageView = [[UIImageView alloc] initWithImage:[self.datasource imageAtIndex:page]];
        newPageView.contentMode = UIViewContentModeScaleAspectFit;
        
        newPageView.frame = frame;
        
        newPageView.userInteractionEnabled = YES;
        
        [self.scrollView addSubview:newPageView];
        [self.pageViews replaceObjectAtIndex:page withObject:newPageView];        
    }
}

- (void)purgePage:(NSInteger)page {
    if (page < 0 || page >= self.datasource.photos.count) {
        // If it's outside the range of what we have to display, then do nothing
        return;
    }
    
    // Remove a page from the scroll view and reset the container array
    UIView *pageView = [self.pageViews objectAtIndex:page];
    if ((NSNull*)pageView != [NSNull null]) {
        [pageView removeFromSuperview];
        [self.pageViews replaceObjectAtIndex:page withObject:[NSNull null]];
    }
}
//Very bad handling here !!!! Repeat too much, slow down app


- (void)loadVisiblePages {
    // First, determine which page is currently visible
    CGFloat pageWidth = self.scrollView.frame.size.width;
    NSInteger page = (NSInteger)floor(self.scrollView.contentOffset.x/pageWidth + 0.5f);
    
    if (self.pageControl.currentPage != page || isFirstTimeLoading) {
        isFirstTimeLoading = NO;
        // Update the page control
        self.pageControl.currentPage = page;
        
        //[self loadPage:page-1];
        [self loadPage:page];
        [self loadPage:page+1];
       
        self.labelPopup.text = [self.datasource descriptionAtIndex:page];
        /*   
        // Work out which pages we want to load
        NSInteger firstPage = page - 1;
        NSInteger lastPage = page + 1;
    
        // Purge anything before the first page
        for (NSInteger i=0; i<firstPage; i++) {
            [self purgePage:i];
        }
        for (NSInteger i=firstPage; i<=lastPage; i++) {
            [self loadPage:i];
        }
        for (NSInteger i=lastPage+1; i<pageCount; i++) {
            [self purgePage:i];
        }*/
        
        

    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.datasource = [[DataSource alloc] init];
    pageCount = self.datasource.photos.count;    
	
    // Set up the page control
    self.pageControl.currentPage = 0;
    self.pageControl.numberOfPages = pageCount;
    
    // Set up the array to hold the views for each page
    self.pageViews = [[NSMutableArray alloc] init];
    for (NSInteger i = 0; i < pageCount; ++i) {
        [self.pageViews addObject:[NSNull null]];
    }
    self.scrollView.showsHorizontalScrollIndicator = NO;
    
    self.aboutButton.frame = CGRectMake(300, 440, aboutButton.frame.size.width, aboutButton.frame.size.height);
    self.pageControl.center = CGPointMake(self.view.center.x, self.view.bounds.size.height - 20);
    //Make it stay outsize the visible view
    self.labelPopup.center = CGPointMake(self.view.center.x, self.view.bounds.size.height+20);
    isPopupShow = NO;
    
    
    UITapGestureRecognizer *tapRecognizer = [[UITapGestureRecognizer alloc] 
                                             initWithTarget:self 
                                             action:@selector(tapScreen:)];
    [self.scrollView addGestureRecognizer:tapRecognizer];

    
   /* UISwipeGestureRecognizer *upSwipeRecognizer = [[UISwipeGestureRecognizer alloc]
                                                   initWithTarget:self
                                                   action:@selector(swipedUp:)];
    upSwipeRecognizer.numberOfTouchesRequired = 1; 
    upSwipeRecognizer.direction = UISwipeGestureRecognizerDirectionUp;
    [self.scrollView addGestureRecognizer:upSwipeRecognizer];
    
    
    UISwipeGestureRecognizer *downSwipeRecognizer = [[UISwipeGestureRecognizer alloc]
                                                   initWithTarget:self
                                                   action:@selector(swipedDown:)];
    downSwipeRecognizer.numberOfTouchesRequired = 1; 
    downSwipeRecognizer.direction = UISwipeGestureRecognizerDirectionDown;
    [self.scrollView addGestureRecognizer:downSwipeRecognizer];
    */
    
}

- (void)viewDidUnload
{
    [self setScrollView:nil];
    [self setPageControl:nil];    
    [self setPageViews:nil];
    
    [self setAboutButton:nil];
    [self setAboutViewController:nil];
    [self setLabelPopup:nil];
    [self setDatasource:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    // Set up the content size of the scroll view
    CGSize pagesScrollViewSize = self.scrollView.frame.size;
    self.scrollView.contentSize = CGSizeMake(pagesScrollViewSize.width * pageCount, pagesScrollViewSize.height);
    isFirstTimeLoading = YES;
    [self loadVisiblePages];
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

#pragma mark - UIScrollViewDelegate
- (void)scrollViewDidScroll:(UIScrollView *)scrollView {   
    // Load the pages which are now on screen
    [self loadVisiblePages];
}

#pragma mark - Handle other events
- (IBAction)buttonAboutClick:(id)sender {
   if (!aboutViewController) {
        aboutViewController = [[AboutViewController alloc] initWithNibName:@"AboutViewController" bundle: nil];
        aboutViewController.modalTransitionStyle  = UIModalTransitionStyleFlipHorizontal;
   }
   [self.navigationController presentModalViewController: self.aboutViewController animated:YES];
}


- (void) swipedUp :(UISwipeGestureRecognizer *)swipeRecognizer
{
   [UIView animateWithDuration:1.0 animations:^ (void) {
       self.labelPopup.center = CGPointMake(self.labelPopup.center.x, 420); 
    }];
    isPopupShow = YES;
}

- (void) swipedDown :(UISwipeGestureRecognizer *)swipeRecognizer
{
   [UIView animateWithDuration:1.0 animations:^ (void) {
     self.labelPopup.center = CGPointMake(self.labelPopup.center.x, self.view.bounds.size.height+20); 
    }];
    isPopupShow = NO;
}
- (void) popUpLabel {
    if (isPopupShow) {
        [UIView animateWithDuration:1.0 animations:^ (void) {
            self.labelPopup.center = CGPointMake(self.labelPopup.center.x, self.view.bounds.size.height+20); 
        }];
        isPopupShow = NO;
        //[self.labelPopup removeFromSuperview];
    } else {
        self.labelPopup.text = [self.datasource descriptionAtIndex:self.pageControl.currentPage];
        // [self.view addSubview: self.labelPopup];
        [UIView animateWithDuration:1.0 animations:^ (void) {
            self.labelPopup.center = CGPointMake(self.labelPopup.center.x, 420); 
        }];
        isPopupShow = YES;
        
    }
}


- (void) tapScreen :(UITapGestureRecognizer *)tapRecognizer
{
    [self popUpLabel];
}
@end
