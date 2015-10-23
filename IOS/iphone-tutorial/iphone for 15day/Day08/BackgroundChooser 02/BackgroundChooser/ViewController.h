//
//  ViewController.h
//  BackgroundChooser
//
//  Created by cuong minh on 3/10/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class AboutViewController;
@class DataSource;

@interface ViewController : UIViewController <UIScrollViewDelegate, UIGestureRecognizerDelegate>
{
    int pageCount;
    BOOL isPopupShow;
    BOOL isFirstTimeLoading;
}

//@property (nonatomic, strong) NSArray *pageImages;
@property (nonatomic, strong) NSMutableArray *pageViews;
@property (nonatomic, strong) DataSource *datasource;

@property (strong, nonatomic) IBOutlet UIScrollView *scrollView;
@property (strong, nonatomic) IBOutlet UIPageControl *pageControl;
@property (strong, nonatomic) IBOutlet UIButton *aboutButton;

@property (strong, nonatomic) AboutViewController *aboutViewController;
@property (strong, nonatomic) IBOutlet UILabel *labelPopup;

@end
