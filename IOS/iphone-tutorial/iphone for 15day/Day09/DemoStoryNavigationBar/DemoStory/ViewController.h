//
//  ViewController.h
//  DemoStory
//
//  Created by cuong minh on 7/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController <UIScrollViewDelegate>
{
    int pageCount;
    int currentPage;
    float pageWidth;
}
@property (strong, nonatomic) IBOutlet UIScrollView *scrollView;
@property (strong, nonatomic) IBOutlet UISlider *pageSlider;
@property (strong, nonatomic) IBOutlet UILabel *pageCounter;
@end
