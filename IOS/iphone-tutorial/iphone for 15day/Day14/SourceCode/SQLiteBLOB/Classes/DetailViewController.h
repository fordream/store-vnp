//
//  DetailViewController.h
//  SQL
//
//  Created by iPhone SDK Articles on 10/26/08.
//  Copyright 2008 www.iPhoneSDKArticles.com.
//

#import <UIKit/UIKit.h>

@class Coffee, EditViewController;

@interface DetailViewController : UIViewController <UINavigationControllerDelegate, UIImagePickerControllerDelegate, UITableViewDataSource, UITableViewDelegate> {

	IBOutlet UITableView *tableView;
	Coffee *coffeeObj;
	NSIndexPath *selectedIndexPath;
	EditViewController *evController;
	
	UIImagePickerController *imagePickerView;
}

@property (nonatomic, retain) Coffee *coffeeObj;
- (IBAction)editCoffee:(id)sender;

@end
