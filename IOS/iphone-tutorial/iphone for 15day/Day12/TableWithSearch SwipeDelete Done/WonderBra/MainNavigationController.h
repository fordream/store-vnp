//
//  MainNavigationController.h
//  WonderBra
//
//  Created by cuong minh on 3/12/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class DataSource;
@interface MainNavigationController : UINavigationController
@property (strong, nonatomic) DataSource *datasource;
@end
