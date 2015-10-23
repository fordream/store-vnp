//
//  ViewController.h
//  WonderBra
//
//  Created by cuong minh on 3/12/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class DataSource;
@class CustomViewCell;
@class DetailViewController;
//1. Add UISearch
@interface ViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, UISearchDisplayDelegate, UISearchBarDelegate>
{
    NSInteger currentIndex;
}
@property (strong, nonatomic) IBOutlet UITableView *mainTableView;
@property (strong, nonatomic) IBOutlet CustomViewCell *tmpCell;
@property (nonatomic, retain) UINib *cellNib;


@property (weak, nonatomic) DataSource *datasource;
@property (strong, nonatomic) DetailViewController *detailViewController;
@property (strong, nonatomic) IBOutlet UIButton *addButton;
@end
