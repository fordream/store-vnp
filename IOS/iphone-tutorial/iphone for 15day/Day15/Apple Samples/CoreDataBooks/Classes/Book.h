
#import <CoreData/CoreData.h>

@interface Book : NSManagedObject  
{
}

@property (nonatomic, retain) NSString *title;
@property (nonatomic, retain) NSString *author;
@property (nonatomic, retain) NSDate *copyright;

@end


