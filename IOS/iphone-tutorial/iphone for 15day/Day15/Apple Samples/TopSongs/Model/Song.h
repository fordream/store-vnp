#import <UIKit/UIKit.h>

@class Category;

@interface Song : NSManagedObject

@property (nonatomic, retain) NSString *title;
@property (nonatomic, retain) Category *category;
@property (nonatomic, retain) NSNumber *rank;
@property (nonatomic, retain) NSString *album;
@property (nonatomic, retain) NSDate *releaseDate;
@property (nonatomic, retain) NSString *artist;

@end
