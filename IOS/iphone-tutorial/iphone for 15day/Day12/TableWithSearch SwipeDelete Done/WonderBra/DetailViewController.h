//
//  DetailViewController.h
//  AdvancedTableView
//
//  Created by cuong minh on 3/12/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class DataSource;

@interface DetailViewController : UIViewController
@property (nonatomic, assign) NSInteger selectedIndex;
@property (strong, nonatomic) IBOutlet UIImageView *imageView;
@property (nonatomic, weak) DataSource *datasource;
@property (nonatomic, assign) BOOL isSearchResult;
@end
