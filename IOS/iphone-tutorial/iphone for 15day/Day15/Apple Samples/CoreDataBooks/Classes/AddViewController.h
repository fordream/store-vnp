#import "DetailViewController.h"


@protocol AddViewControllerDelegate;


@interface AddViewController : DetailViewController {
	id <AddViewControllerDelegate> delegate;
}

@property (nonatomic, assign) id <AddViewControllerDelegate> delegate;

- (IBAction)cancel:(id)sender;
- (IBAction)save:(id)sender;

@end


@protocol AddViewControllerDelegate
- (void)addViewController:(AddViewController *)controller didFinishWithSave:(BOOL)save;
@end

