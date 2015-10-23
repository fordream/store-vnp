//
//  NavigationControlleriPhone.h
//  DemoNavigationOneXib
//
//  Created by cuong minh on 2/25/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class AnotherViewController;
@class MoreViewController;

@interface NavControlleriPhone : UINavigationController

@property (strong, nonatomic) AnotherViewController *anotherViewController;
@property (strong, nonatomic) MoreViewController *moreViewController;

@property (strong, nonatomic) NSString *txtValue;
@property (readwrite, nonatomic) float numValue;
@end
