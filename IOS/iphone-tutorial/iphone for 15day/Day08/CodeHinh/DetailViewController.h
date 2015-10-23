#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>



@interface DetailViewController : UIViewController <UIScrollViewDelegate> {
	UITextView *desview;
	UISlider *slider;
}

- (IBAction)slidermoved;

@property (nonatomic,retain) IBOutlet UITextView *desview;
@property (nonatomic,retain) IBOutlet UISlider *slider;

@end




