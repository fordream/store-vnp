//
//  ViewController.h
//  NguoiMauVietNam
//
//  Created by Lion User on 26/05/2012.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ImageViewProtocol.h"
#import <UIKit/UIKit.h>

@interface ViewController : UIViewController <UIScrollViewDelegate ,ImageViewProtocol>
@property (nonatomic,strong) UIScrollView *myScrollView;
@property (nonatomic,strong) UIImageView *myImageView;
@end
