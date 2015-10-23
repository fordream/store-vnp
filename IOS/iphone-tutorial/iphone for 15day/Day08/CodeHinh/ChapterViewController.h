#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import "DetailViewController.h"

@class RootViewController;

@interface ChapterViewController : UIViewController <UINavigationBarDelegate, UITableViewDelegate, UITableViewDataSource> {
	DetailViewController *detailviewcontroller;
	NSMutableArray *chapters;
	NSDictionary *outlineData;
	NSArray *listContent;
	UITableView *myTableView;
	NSString *kItemTitleKey;
	NSString *kChildrenKey;
	
	RootViewController *galleryViewController;
}


@property (nonatomic,retain) DetailViewController *detailviewcontroller;
@property (nonatomic,retain) RootViewController *galleryViewController;
@property (nonatomic,retain) NSMutableArray *chapters;
@property (nonatomic, retain) NSArray *listContent;
@property (nonatomic, retain) NSDictionary *outlineData;
@property (nonatomic, retain) IBOutlet UITableView *myTableView;

@end
