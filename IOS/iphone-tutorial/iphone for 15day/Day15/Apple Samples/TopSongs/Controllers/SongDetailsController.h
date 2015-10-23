#import <UIKit/UIKit.h>

@class Song;

@interface SongDetailsController : UITableViewController {
@private
    NSDateFormatter *dateFormatter;
    Song *song;
}

@property (nonatomic, retain) Song *song;
@property (nonatomic, readonly, retain) NSDateFormatter *dateFormatter;

@end
